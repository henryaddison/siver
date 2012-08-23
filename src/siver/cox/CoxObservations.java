package siver.cox;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.cox.observations.*;
import siver.cox.observations.LaneObservation.Blockage;

public class CoxObservations {
	private Cox cox;
	private Boat boat;
	private BoatNavigation navigator;
	
	OutingComplete outingComplete;
	
	private CoxVision vision;
	
	private static HashMap<Class<AbstractObservation>, AbstractObservation> observations;

	
	public CoxObservations(Cox cox, Boat boat, BoatNavigation navigator) {
		this.cox = cox;
		this.boat = boat;
		this.navigator = navigator;
		observations = new HashMap<Class<AbstractObservation>, AbstractObservation>();
	}
	
	public static CoxObservations make(Cox cox, Boat boat, BoatNavigation navigator) {
		CoxObservations obs = new CoxObservations(cox, boat, navigator);
		
		return obs;
	}
	
	public CoxVision getVision() {
		if(vision == null) {
			this.setVision(new CoxVision(cox, boat, navigator));
		}
		return vision;
	}

	private void setVision(CoxVision vision) {
		this.vision = vision;
	}
	
	private Object retrieveFromObservations(Class<? extends AbstractObservation> klass) throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		if(observations.get(klass) == null) {
			Constructor<? extends AbstractObservation> cons = klass.getConstructor(CoxObservations.class, Cox.class, Boat.class, BoatNavigation.class);
			AbstractObservation instance = cons.newInstance(this, cox, boat, navigator);
			observations.put((Class<AbstractObservation>) klass, instance);
		}
		return observations.get(klass).getValue();
		
	}
	
	public Integer boatGear() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		return (Integer) retrieveFromObservations(BoatGear.class);
	}
	
	public Boolean outingComplete() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		return (Boolean) retrieveFromObservations(OutingComplete.class);
	}
	
	public boolean atRiversEnd() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		return (Boolean) retrieveFromObservations(AtRiverEnd.class);
	}
	
	public boolean belowDesiredSpeed() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		return (Boolean) retrieveFromObservations(BelowDesiredSpeed.class);
	}
	
	public boolean aboveDesiredSpeed() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		return (Boolean) retrieveFromObservations(AboveDesiredSpeed.class);
	}
	
	public boolean changingLane() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		return (Boolean) retrieveFromObservations(ChangingLane.class);
	}
	
	//these are more complicated
	
	public Blockage aheadCurrentLaneLook() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		return (Blockage) retrieveFromObservations(AheadCurrentLane.class);
	}
	
	public boolean nearbyBoatInfront() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		return (Boolean) retrieveFromObservations(NearbyBoatInfront.class);
	}
	
	public boolean slowBoatInfront() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		return (Boolean) retrieveFromObservations(SlowBoatInfront.class);
	}
	
	public boolean laneToLeftIsClear() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		return (Boolean) retrieveFromObservations(LaneToLeftIsClear.class);
	}
	
	public boolean laneToRightIsClear() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		return (Boolean) retrieveFromObservations(LaneToRightIsClear.class);
	}
	
		
}
