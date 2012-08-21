library(RMySQL)

source('functions/plot_graph_from_query.R')

query = "
SELECT
  schedules.name,
  experiment_runs.brain_type,
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
) AS aggregate_boat_records ON aggregate_boat_records.experiment_run_id = experiment_runs.id
JOIN experiments ON experiments.id = experiment_runs.experiment_id
JOIN schedules ON schedules.id = experiments.schedule_id
WHERE experiment_runs.brain_type = '%s'
AND boats_launched = %d
GROUP BY boats_launched, delay, experiment_runs.brain_type
ORDER BY xcol"

plot_graph_from_query(query,
xlab='Delay between launched', 
ylab='Average number of crashes',
xlim=c(0,600),
ylim=c(0,2000),
main_title="Average number of crashes per brain type for experiments with %i boats launched")