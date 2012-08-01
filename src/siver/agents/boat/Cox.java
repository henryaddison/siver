package siver.agents.boat;

import repast.simphony.engine.schedule.ScheduledMethod;
import siver.river.lane.Lane;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;
import siver.agents.boat.actions.*;

public class Cox {
	//The boat the cox is controlling.
	protected Boat boat;
	
	//how fast the boat would like to be travelling
	private int desired_gear;
	
	private boolean incapcitated;
	
	protected Action action;
	protected BoatNavigation navigator;
	
	protected CoxObservations observations;
	
	public Cox() {
		incapcitated = false;
	}
	
	public void launch(Boat boat, Lane launchLane, int desGear) {
		//save reference to boat launched in
		this.boat = boat;
		
		this.desired_gear = desGear;
		
		//place the boat at the location of the first node of the lane
		LaneNode launchNode = launchLane.getStartNode();
		boat.launch(this, launchNode.getLocation());
		
		//and point the boat in the correct direction
		LaneEdge launchEdge = launchLane.getNextEdge(launchNode, false);
		navigator = new BoatNavigation(this, boat, launchEdge, false);
		observations = new CoxObservations(this, boat, navigator);
	}
	
	//BEHAVIOUR
	@ScheduledMethod(start = 1, interval = 1, priority=10)
	public void step() {
		if(!incapcitated) {
			if(backAtBoatHouse()) {
				new Land(this).execute();
				return;
			}
			
			if(action == null) {
				chooseAction();
			}
			action.execute();
		}
	}
	
	public void chooseAction() {
		if(observations.atRiversEnd()) {
			action = new Spin(this);
		}
		else if(observations.belowDesiredSpeed()) {
			action = new SpeedUp(this);
		}
		else if(true) {
			action = new LetBoatRun(this);
		}
	}
	
	/*
	 * PREDICATES
	 */	
	
	
	
	protected boolean backAtBoatHouse() {
		return navigator.getDestinationNode().equals(navigator.getLane().getStartNode());
	}
	

	
	/*
	 * HELPERS
	 */
	public Action getAction() {
		return action;
	}
	
	public void clearAction() {
		action = null;
	}
	
	public Boat getBoat() {
		return boat;
	}
	
	public BoatNavigation getNavigator() {
		return navigator;
	}
	
	public void incapcitate() {
		boat.deadStop();
		incapcitated = true;
	}
	
	public void recapcitate() {
		incapcitated = false;
	}
	
	public boolean isIncapcitated() {
		return incapcitated;
	}
	
	public int desired_gear() {
		return desired_gear;
	}
	
}
