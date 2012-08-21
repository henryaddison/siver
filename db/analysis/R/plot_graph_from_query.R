plot_graph_from_query <- function(query, xlab, ylab, xlim, ylim, db_group, launched) {
	con <- dbConnect("MySQL", group = db_group)
	brain_types <- c("BasicBrain", "ConservativeBrain", "OvertakingBrain", "RandomMovement")

	brain_types_to_colour_map = c(BasicBrain="black", ConservativeBrain="red", OvertakingBrain="green", RandomMovement="blue")
	
	launched_options <- c(10, 20, 30)
	
	
	for(launched in launched_options) {
		dev.new()
		plot(list(), list(), xlim=xlim, ylim=ylim, xlab=xlab, ylab=ylab)
		for (brain_type in brain_types) {
			interpolated_query = sprintf(query, brain_type, launched)
	
			res <- dbSendQuery(con, interpolated_query)
		
			brain_data = fetch(res, -1)
	
			points(x=brain_data$xcol, y=brain_data$ycol, col=brain_types_to_colour_map[brain_type])
		}
	}

	dbDisconnect(con)
}