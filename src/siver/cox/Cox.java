package siver.cox;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import repast.simphony.engine.schedule.ScheduledMethod;
import siver.river.lane.Lane;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;
import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.boat.actions.*;

public class Cox {
	//The boat the cox is controlling.
	protected Boat boat;
	
	//how fast the boat would like to be travelling
	private int desired_gear;
	
	private boolean incapcitated;
	
	protected Action action;
	protected BoatNavigation navigator;
	
	protected CoxObservations observations;
	
	private CoxBrain brain;
	
	public Cox() {
		
	}
	
	public void launch(Boat boat, Lane launchLane, int desGear) throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		launch(BasicBrain.class, boat, launchLane, desGear);
	}
	
	public void launch(Class brainType, Boat boat, Lane launchLane, int desGear) throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		incapcitated = false;
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
		Constructor<CoxBrain> cons = brainType.getConstructor(CoxObservations.class);
		brain = cons.newInstance(observations);
	}
	
	//BEHAVIOUR
	@ScheduledMethod(start = 1, interval = 1, priority=10)
	public void step() throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		if(!incapcitated) {
			if(backAtBoatHouse()) {
				new Land(this).execute();
				return;
			}
			
			if(action == null) {
				Constructor<Action> cons = brain.chooseAction().getConstructor(Cox.class);
				action = cons.newInstance(this);
			}
			action.execute();
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
