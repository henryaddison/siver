select
experiment_runs.brain_type,
avg(boat_records.launch_tick),
avg(boat_records.land_tick),
avg(boat_records.desired_gear),
avg(boat_records.distance_covered),
avg(boat_records.aggregate_gear_difference)
from 
experiment_runs 
join experiments on experiments.id = experiment_runs.experiment_id
join schedules on schedules.id = experiments.schedule_id
join boat_records on experiment_runs.id = boat_records.experiment_run_id
where schedules.name = "One boat"
group by experiment_runs.brain_type