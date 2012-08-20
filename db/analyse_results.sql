select 
experiments.brain_type,
schedules.id,
schedules.name,
avg(boat_records.aggregate_gear_difference),
sum(crash_count)/count(distinct experiment_runs.id)
from experiment_runs
join experiments on experiments.id = experiment_runs.experiment_id
join schedules on schedules.id = experiments.schedule_id
join boat_records on boat_records.experiment_run_id = experiment_runs.id
and boat_records.land_tick IS NOT NULL
group by schedules.id, experiments.brain_type;