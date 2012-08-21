plot_graph_from_query <- function(query, xlab, ylab, xlim, ylim, db_group, main_title) {
	con <- dbConnect("MySQL", group = db_group)
	brain_types <- c("BasicBrain", "ConservativeBrain", "OvertakingBrain", "RandomMovement")

	brain_types_to_colour_map = c(BasicBrain="black", OvertakingBrain="green", RandomMovement="blue", ConservativeBrain="red")
	
	launched_options <- c(10, 20, 30)
	
	
	for(launched in launched_options) {
		#dev.new()
		interpolated_main_title = sprintf(main_title, launched)
		pdf(paste("images/", interpolated_main_title, ".pdf", sep=""))
		plot(list(), list(), xlim=xlim, ylim=ylim, xlab=xlab, ylab=ylab)
		
		title(main=interpolated_main_title)
		legend("topright", inset=.05, title="Brain Type", brain_types, col=brain_types_to_colour_map[brain_types], pch=1)
		
		for (brain_type in brain_types) {
			interpolated_query = sprintf(query, brain_type, launched)
	
			res <- dbSendQuery(con, interpolated_query)
		
			brain_data = fetch(res, -1)
	
			points(x=brain_data$xcol, y=brain_data$ycol, col=brain_types_to_colour_map[brain_type])
		}
		
		dev.off()
	}

	dbDisconnect(con)
}