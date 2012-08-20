select 
experiment_runs.brain_type,
sum(experiment_runs.crash_count)/count(distinct experiment_runs.id) as avg_crashes_per_run,
count(boat_records.id)/count(distinct experiment_runs.id) as boats_launched,
max(boat_records.launch_tick)/(count(boat_records.id)/count(distinct experiment_runs.id) - 1) as delay,
sum(if(boat_records.land_tick IS NOT NULL, 1, 0))/count(distinct experiment_runs.id) as boats_landed,
schedules.name
from experiment_runs
join boat_records on boat_records.experiment_run_id = experiment_runs.id
join experiments on experiments.id = experiment_runs.experiment_id
join schedules on schedules.id = experiments.schedule_id
where
experiment_runs.brain_type != 'RandomChoice'
group by schedules.name, experiment_runs.brain_type
#order by experiment_runs.brain_type, schedules.name
#having boats_launched = 10 and delay = 600