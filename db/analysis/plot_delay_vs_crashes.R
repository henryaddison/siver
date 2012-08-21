library(RMySQL)
con <- dbConnect("MySQL", group = "siver_development")

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
launched <- 10
xlab='Delay between launched'
ylab='Average number of crashes'


plot(list(), list(), ylim=c(0,3000), xlim=c(0,600), xlab=xlab, ylab=ylab)

brain_types <- c("BasicBrain", "ConservativeBrain", "OvertakingBrain", "RandomMovement")

brain_types_to_colour_map = c(BasicBrain="black", ConservativeBrain="red", OvertakingBrain="green", RandomMovement="blue")

for (brain_type in brain_types) {

	interpolated_query = sprintf(query, brain_type, launched)

	res <- dbSendQuery(con, interpolated_query)
	
	brain_data = fetch(res, -1)

	points(x=brain_data$xcol, y=brain_data$ycol, col=brain_types_to_colour_map[brain_type])
}

dbDisconnect(con)
