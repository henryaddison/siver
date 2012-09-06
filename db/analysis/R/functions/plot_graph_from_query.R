plot_graph_from_query <- function(query, xlab, ylab, xlim, ylim, main_title) {
  dbname="siver_production"
	con <- dbConnect("MySQL", user="siver", host="localhost", dbname=dbname)
	control_policies <- c("GearFocussed", "SafetyFocussed", "Overtaking", "RandomMovement")

  # control_policies_to_colour_map = c(GearFocussed="black", Overtaking="green", RandomMovement="blue", SafetyFocussed="red")
	
	control_policies_to_colour_map = c(GearFocussed="black", Overtaking="black", RandomMovement="black", SafetyFocussed="black")
	
	cp_to_lty = c(GearFocussed=1, Overtaking=2, RandomMovement=3, SafetyFocussed=4)

  cp_to_pch = c(GearFocussed=0, Overtaking=4, RandomMovement=2, SafetyFocussed=1)
	
	launched_options <- c(10, 20, 30)
	
	
	for(launched in launched_options) {
		#dev.new()
		
		interpolated_main_title = sprintf(main_title, launched)
		
		pdf(paste(paste("images",dbname,"",sep="/"), interpolated_main_title, ".pdf", sep=""))
		plot(list(), list(), xlim=xlim, ylim=ylim, xlab=xlab, ylab=ylab, xaxs = "i", yaxs = "i")
		
		title(main=paste(strwrap(interpolated_main_title, width=60), collapse="\n"))
    legend("topright", inset=.05, title="Control Policy", control_policies, col=control_policies_to_colour_map[control_policies], pch=cp_to_pch[control_policies], lty=cp_to_lty[control_policies])
		
		for (control_policy in control_policies) {
			interpolated_query = sprintf(query, control_policy, launched)
	
			res <- dbSendQuery(con, interpolated_query)
		
			brain_data = fetch(res, -1)
	
			points(x=brain_data$xcol, y=brain_data$ycol, col=control_policies_to_colour_map[control_policy], type='o', pch=cp_to_pch[control_policy], lty=cp_to_lty[control_policy])
		}
		
		dev.off()
	}

	dbDisconnect(con)
}