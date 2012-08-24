package siver.cox.control_policies;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import repast.simphony.random.RandomHelper;
import siver.cox.Cox;
import siver.cox.CoxObservations;
import siver.cox.actions.Action;

public class RandomDelegate extends ControlPolicy {
	private static final Class[] possible_policies = {GearFocussed.class, RandomMovement.class, RandomChoice.class};
	
	private ControlPolicy delegate;
	public RandomDelegate(CoxObservations obs) {
		int index = RandomHelper.nextIntFromTo(0, possible_policies.length-1);
		Constructor<? extends ControlPolicy> cons;
		try {
			cons = possible_policies[index].getConstructor();
			delegate = cons.newInstance();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected Class<? extends Action> typeSpecificActionChoice() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		return delegate.chooseAction();
	}
	
	
	
}
