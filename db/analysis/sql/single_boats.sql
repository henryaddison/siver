SELECT
simulation_runs.control_policy as control_policy,
ROUND(AVG(boat_records.land_tick),0) as avg_land_tick,
ROUND(AVG(boat_records.desired_gear),0) as desired_gear,
AVG(boat_records.distance_covered) as avg_distance_covered,
ROUND(AVG(boat_records.aggregate_gear_difference),0) as avg_aggregate_gear_difference
FROM 
simulation_runs 
JOIN simulation_parameters ON simulation_parameters.id = simulation_runs.simulation_parameters_id
JOIN schedules ON schedules.id = simulation_parameters.schedule_id
JOIN boat_records ON simulation_runs.id = boat_records.simulation_run_id
WHERE simulation_runs.flushed = 1
AND schedules.name = "One boat"
GROUP BY simulation_runs.CONTROL_POLICY