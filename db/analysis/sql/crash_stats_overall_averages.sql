SELECT 
simulation_runs.control_policy,
ROUND(AVG(crash_count),0) as crashes_per_run,
ROUND(AVG(avg_crash_speed),1) as speed_per_crash,
ROUND(AVG(max_crash_speed),1) as max_crash_speed,
ROUND(AVG(avg_boats_per_crash), 1) as boats_per_crash,
ROUND((AVG(crashes_in_middle_lane_count)/AVG(crash_count))*100,1) as percent_middle_lane,
ROUND(AVG(avg_tick),0) as avg_crash_tick,
ROUND(AVG(sim_end_est),0) as simulation_length_estimate,
ROUND(AVG(crashes_during_launch_est)*100/AVG(crash_count),0) as launch_crash_percent_est
FROM simulation_runs
LEFT JOIN (
SELECT
COUNT(*) as crash_count,
AVG(relative_velocity) as avg_crash_speed,
AVG(boats_count) as avg_boats_per_crash,
SUM(middle_lane) as crashes_in_middle_lane_count,
MAX(relative_velocity) as max_crash_speed,
AVG(tick) as avg_tick,
SUM(IF((tick % 60) = 0, 1, 0)) as crashes_during_launch_est,
simulation_run_id
FROM crash_records
GROUP BY simulation_run_id
) as aggregate_crash_records on aggregate_crash_records.simulation_run_id = simulation_runs.id
JOIN (SELECT 
  simulation_run_id,
  COUNT(boat_records.id) as boats_launched,
  SUM(IF(boat_records.land_tick IS NOT NULL, 1, 0)) as boats_landed,
  MAX(boat_records.launch_tick)/(COUNT(boat_records.id)-1) as delay,
  MAX(boat_records.land_tick) as sim_end_est
  FROM boat_records
  GROUP BY simulation_run_id
) AS aggregate_boat_records ON aggregate_boat_records.simulation_run_id = simulation_runs.id
WHERE simulation_runs.flushed = 1
AND simulation_runs.control_policy != 'RandomChoice'
GROUP BY simulation_runs.control_policy