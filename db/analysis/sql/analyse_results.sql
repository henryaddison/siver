SELECT
  schedules.name,
  experiment_runs.control_policy,
  boats_launched,
  AVG(boats_landed),
  AVG(experiment_runs.crash_count) as ycol,
  delay as xcol
FROM experiment_runs
JOIN (SELECT 
  experiment_run_id,
  COUNT(boat_records.id) as boats_launched,
  SUM(IF(boat_records.land_tick IS NOT NULL, 1, 0)) as boats_landed,
  MAX(boat_records.launch_tick)/(COUNT(boat_records.id)-1) as delay
  FROM boat_records
  GROUP BY experiment_run_id
) AS boat_schedule ON boat_schedule.experiment_run_id = experiment_runs.id
JOIN experiments ON experiments.id = experiment_runs.experiment_id
JOIN schedules ON schedules.id = experiments.schedule_id
WHERE boats_launched = 10
AND experiment_runs.control_policy = "BasicBrain"
GROUP BY boats_launched, delay, experiment_runs.control_policy
ORDER BY experiment_runs.control_policy