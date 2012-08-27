SELECT
simulation_runs.control_policy,
ROUND(AVG(boats_landed/boats_launched*100),0) as percent_boats_returned
FROM (
SELECT 
  simulation_run_id,
  COUNT(boat_records.id) as boats_launched,
  SUM(IF(boat_records.land_tick IS NOT NULL, 1, 0)) as boats_landed,
  MAX(boat_records.launch_tick)/(COUNT(boat_records.id)-1) as delay
  FROM boat_records
  GROUP BY simulation_run_id
) AS aggregate_boat_records
JOIN simulation_runs ON simulation_runs.id = aggregate_boat_records.simulation_run_id
JOIN simulation_parameters ON simulation_parameters.id = simulation_runs.simulation_parameters_id
JOIN schedules ON schedules.id = simulation_parameters.schedule_id
WHERE simulation_runs.flushed = 1
AND simulation_runs.control_policy != 'RandomChoice'
GROUP BY simulation_runs.control_policy
ORDER BY simulation_runs.control_policy, boats_launched, delay, schedules.name, schedules.version