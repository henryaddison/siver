SELECT
simulation_runs.brain_type,
AVG(boats_landed/boats_launched*100) as percent_boats_returned
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
WHERE simulation_runs.brain_type != 'RandomChoice'
GROUP BY simulation_runs.brain_type
ORDER BY simulation_runs.brain_type, boats_launched, delay, schedules.name, schedules.version