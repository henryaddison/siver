package siver.river.lane;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import org.junit.Before;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import siver.agents.boat.BoatAgent;
import siver.agents.boat.CoxAgent;
import siver.context.SiverContextCreator;

public class EdgeTester {
	protected Context<Object> mockContext;
	protected ContinuousSpace<Object> mockSpace;
	protected CoxAgent cox1;
	protected CoxAgent cox2;
	protected CoxAgent cox3;
	
	@Before
	public void setUp() throws Exception {
		mockContext = createMock(Context.class);
		mockSpace = createMock(ContinuousSpace.class);
		
		SiverContextCreator.setContext(mockContext);
		SiverContextCreator.setSpace(mockSpace);
		
		expect(mockContext.add(anyObject())).andStubReturn(true);
		expect(mockSpace.moveTo(anyObject(), eq(10.0), eq(20.0))).andStubReturn(true);
		expect(mockContext.remove(anyObject())).andStubReturn(true);
		
		cox1 = createMock(CoxAgent.class);
		cox2 = createMock(CoxAgent.class);
		cox3 = createMock(CoxAgent.class);
		
		BoatAgent mockBoat = createMock(BoatAgent.class);
		expect(mockBoat.getLocation()).andStubReturn(new NdPoint(10,20));
		expect(cox1.getBoat()).andStubReturn(mockBoat);
		expect(cox2.getBoat()).andStubReturn(mockBoat);
		expect(cox3.getBoat()).andStubReturn(mockBoat);
		
		replay(cox1, cox2, cox3, mockBoat, mockContext, mockSpace);
	}
}
