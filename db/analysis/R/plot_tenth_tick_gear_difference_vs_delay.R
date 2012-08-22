library(RMySQL)

source('functions/plot_graph_from_query.R')

query = "
SELECT
  schedules.name,
  simulation_runs.brain_type,
  boats_launched,
  delay as xcol,
  AVG(avg_landed_tenth_tick_gear_difference_in_run) as ycol
FROM simulation_runs
JOIN (SELECT 
  simulation_run_id,
  COUNT(boat_records.id) as boats_launched,
  SUM(IF(boat_records.land_tick IS NOT NULL, 1, 0)) as boats_landed,
  MAX(boat_records.launch_tick)/(COUNT(boat_records.id)-1) as delay,
  SUM(IF(boat_records.land_tick IS NOT NULL, boat_records.aggregate_tenth_tick_gear_difference,0))/SUM(IF(boat_records.land_tick IS NOT NULL, 1, 0)) as avg_landed_tenth_tick_gear_difference_in_run
  FROM boat_records
  GROUP BY simulation_run_id
) AS aggregate_boat_records ON aggregate_boat_records.simulation_run_id = simulation_runs.id
JOIN simulation_parameters ON simulation_parameters.id = simulation_runs.simulation_parameters_id
JOIN schedules ON schedules.id = simulation_parameters.schedule_id
WHERE simulation_runs.brain_type = '%s'
AND boats_launched = %d
GROUP BY schedules.name
ORDER BY xcol"

plot_graph_from_query(query,
xlab='Delay between launched in seconds', 
ylab='Average aggregate 10th tick gear difference',
xlim=c(0,600),
ylim=c(0,4500),
main_title="Average aggregate gear difference recorded every 10th tick per brain type for simulation runss with %i boats launched")

