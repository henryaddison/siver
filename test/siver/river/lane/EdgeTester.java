package siver.river.lane;


import static org.easymock.EasyMock.*;

import org.junit.Before;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import siver.boat.Boat;
import siver.context.SiverContextCreator;
import siver.cox.Cox;

public class EdgeTester {
	protected Context<Object> mockContext;
	protected ContinuousSpace<Object> mockSpace;
	protected Cox cox1;
	protected Cox cox2;
	protected Cox cox3;
	
	@Before
	public void setUp() throws Exception {
		mockContext = createMock(Context.class);
		mockSpace = createMock(ContinuousSpace.class);
		
		SiverContextCreator.setContext(mockContext);
		SiverContextCreator.setSpace(mockSpace);
		
		expect(mockContext.add(anyObject())).andStubReturn(true);
		expect(mockSpace.moveTo(anyObject(), eq(10.0), eq(20.0))).andStubReturn(true);
		expect(mockContext.remove(anyObject())).andStubReturn(true);
		
		cox1 = createMock(Cox.class);
		cox2 = createMock(Cox.class);
		cox3 = createMock(Cox.class);
		
		Boat mockBoat = createMock(Boat.class);
		expect(mockBoat.getLocation()).andStubReturn(new NdPoint(10,20));
		expect(cox1.getBoat()).andStubReturn(mockBoat);
		cox1.incapcitate();
		expectLastCall().anyTimes();
		expect(cox2.getBoat()).andStubReturn(mockBoat);
		cox2.incapcitate();
		expectLastCall().anyTimes();
		expect(cox3.getBoat()).andStubReturn(mockBoat);
		cox3.incapcitate();
		expectLastCall().anyTimes();
		
		replay(cox1, cox2, cox3, mockBoat, mockContext, mockSpace);
	}
}
