select
simulation_runs.control_policy as control_policy,
ROUND(avg(boat_records.land_tick),0) as avg_land_tick,
ROUND(avg(boat_records.desired_gear),0) as desired_gear,
avg(boat_records.distance_covered) as avg_distance_covered,
ROUND(avg(boat_records.aggregate_gear_difference),0) as avg_aggregate_gear_difference
from 
simulation_runs 
join simulation_parameters on simulation_parameters.id = simulation_runs.simulation_parameters_id
join schedules on schedules.id = simulation_parameters.schedule_id
join boat_records on simulation_runs.id = boat_records.simulation_run_id
where simulation_runs.flushed = 1
AND schedules.name = "One boat"
group by simulation_runs.control_policy