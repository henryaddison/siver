library(RMySQL)

source('functions/plot_graph_from_query.R')

query = "select 
sum(experiment_runs.crash_count)/count(distinct experiment_runs.id) as ycol,
count(boat_records.id)/count(distinct experiment_runs.id) as boats_launched,
max(boat_records.launch_tick)/(count(boat_records.id)/count(distinct experiment_runs.id) - 1) as xcol
from experiment_runs
join boat_records on boat_records.experiment_run_id = experiment_runs.id
join experiments on experiments.id = experiment_runs.experiment_id
join schedules on schedules.id = experiments.schedule_id
where
experiment_runs.brain_type != 'RandomChoice'
and experiment_runs.brain_type = '%s'
group by schedules.name, experiment_runs.brain_type
having boats_launched = %d
ORDER BY xcol"

plot_graph_from_query(query,
xlab='Delay between launched', 
ylab='Average number of crashes',
xlim=c(0,600),
ylim=c(0,51000),
main_title="Average aggregate gear difference recorded ever tick\n per brain type for experiments with %i boats launched")