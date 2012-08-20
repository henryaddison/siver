library(RMySQL)
con <- dbConnect("MySQL", group = "siver_development")


query = "select experiment_runs.brain_type,
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
and experiment_runs.brain_type = '%s'
group by schedules.name, experiment_runs.brain_type
having boats_launched = %d"
launched <- 10
xlab='Delay between launched'
ylab='Average number of crashes'
axes <- T;



brain_types <- c("BasicBrain", "ConservativeBrain", "OvertakingBrain", "RandomMovement")

brain_types_to_colour_map = c(BasicBrain="black", ConservativeBrain="red", OvertakingBrain="green", RandomMovement="blue")

for (brain_type in brain_types) {

	interpolated_query = sprintf(query, brain_type, launched)

	res <- dbSendQuery(con, interpolated_query)
	
	brain_data = fetch(res, -1)

	plot(x=brain_data$delay, y=brain_data$avg_crashes_per_run, xlab=xlab, ylab=ylab, axes=axes, ylim=c(0,3000), col=brain_types_to_colour_map[brain_type])
	axes <- F
	xlab <- ''
	ylab <- ''
	par(new=T)
}



par(new=F)
dbDisconnect(con)
