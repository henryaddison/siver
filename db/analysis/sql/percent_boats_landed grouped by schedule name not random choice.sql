SELECT
schedules.name,
AVG(boats_landed/boats_launched*100) as percent_boats_returned
FROM (
SELECT 
  experiment_run_id,
  COUNT(boat_records.id) as boats_launched,
  SUM(IF(boat_records.land_tick IS NOT NULL, 1, 0)) as boats_landed,
  MAX(boat_records.launch_tick)/(COUNT(boat_records.id)-1) as delay
  FROM boat_records
  GROUP BY experiment_run_id
) AS aggregate_boat_records
JOIN experiment_runs ON experiment_runs.id = aggregate_boat_records.experiment_run_id
JOIN experiments ON experiments.id = experiment_runs.experiment_id
JOIN schedules ON schedules.id = experiments.schedule_id
WHERE experiment_runs.brain_type != 'RandomChoice'
GROUP BY schedules.name
ORDER BY experiment_runs.brain_type, boats_launched, delay, schedules.name, schedules.version