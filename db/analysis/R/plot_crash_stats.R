library(RMySQL)

source('functions/plot_graph_from_query.R')

query = "SELECT 
AVG(avg_crash_speed) as ycol,
AVG(avg_boats_per_crash) as avg_boats_per_crash,
AVG(crash_count) as avg_crashes_per_run,
AVG(crashes_in_middle_lane_count) as avg_num_crashes_in_middle_lane,
simulation_runs.control_policy,
delay as xcol
FROM simulation_runs
JOIN (SELECT 
  simulation_run_id,
  COUNT(boat_records.id) as boats_launched,
  SUM(IF(boat_records.land_tick IS NOT NULL, 1, 0)) as boats_landed,
  MAX(boat_records.launch_tick)/(COUNT(boat_records.id)-1) as delay
  FROM boat_records
  GROUP BY simulation_run_id
) AS aggregate_boat_records ON aggregate_boat_records.simulation_run_id = simulation_runs.id
LEFT JOIN (
SELECT
COUNT(*) as crash_count,
AVG(relative_velocity) as avg_crash_speed,
AVG(boats_count) as avg_boats_per_crash,
SUM(middle_lane) as crashes_in_middle_lane_count,
simulation_run_id
FROM crash_records
GROUP BY simulation_run_id) as aggregate_crash_records ON aggregate_crash_records.simulation_run_id = simulation_runs.id
JOIN simulation_parameters ON simulation_parameters.id = simulation_runs.simulation_parameters_id
JOIN schedules ON schedules.id = simulation_parameters.schedule_id
WHERE simulation_runs.flushed = 1
AND simulation_runs.control_policy != 'RandomChoice'
AND simulation_runs.control_policy = '%s'
AND boats_launched = %d
GROUP BY boats_launched, delay, simulation_runs.control_policy
ORDER BY xcol"

plot_graph_from_query(query,
xlab='Delay between launches in seconds', 
ylab='Average relative speed of crashes',
xlim=c(0,630),
ylim=c(0,5),
main_title="Average relative speed of crashes per control policy for simulation runs with %i boats launched")