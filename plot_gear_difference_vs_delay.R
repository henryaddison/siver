library(RMySQL)

plot_graph_from_query <- function(query, xlab, ylab, xlim, ylim, db_group, launched) {
	con <- dbConnect("MySQL", group = db_group)
	brain_types <- c("BasicBrain", "ConservativeBrain", "OvertakingBrain", "RandomMovement")

	brain_types_to_colour_map = c(BasicBrain="black", ConservativeBrain="red", OvertakingBrain="green", RandomMovement="blue")
	
	plot(list(), list(), xlim=xlim, ylim=ylim, xlab=xlab, ylab=ylab)
	
	for (brain_type in brain_types) {
		interpolated_query = sprintf(query, brain_type, launched)

		res <- dbSendQuery(con, interpolated_query)
	
		brain_data = fetch(res, -1)

		points(x=brain_data$xcol, y=brain_data$ycol, col=brain_types_to_colour_map[brain_type])
	}

	dbDisconnect(con)
}

query = "select 
count(boat_records.id)/count(distinct experiment_runs.id) as boats_launched,
max(boat_records.launch_tick)/(count(boat_records.id)/count(distinct experiment_runs.id) - 1) as xcol,
sum(if(boat_records.land_tick IS NOT NULL, boat_records.aggregate_gear_difference, 0))/sum(if(boat_records.land_tick IS NOT NULL, 1, 0)) as ycol
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
ylab='Average aggregate gear difference',
xlim=c(0,600),
ylim=c(0,30000),
launched=10,
db_group="siver_development")

