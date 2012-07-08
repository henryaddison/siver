package siver.river.lane;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import repast.simphony.context.space.graph.NetworkBuilder;
import repast.simphony.space.graph.Network;
import siver.LaneContext;
import siver.river.OutlinedArea;
/**
 * Contains the make up of a lane.
 * 
 * A Lane can be considered a network of points that boat will travel between 
 * and an area around that network defined by a top and bottom border.
 * The nodes of the network are what the boat will travel between when considered as a point that travels
 * in straight lines.
 * The area bounded by the lane is where the boat must sit when the boat itself is 
 * considered as an object that takes up space.
 * 
 * @author henryaddison
 *
 */
public class Lane extends OutlinedArea {
	public class CompletedLaneException extends Exception {
		private static final long serialVersionUID = 1L;

		public CompletedLaneException(String msg) {
			super(msg);
		}
	}

	public class UnstartedLaneException extends Exception {

		private static final long serialVersionUID = 1L;

		public UnstartedLaneException(String msg) {
			super(msg);
		}
	}

	//the context that the network projection will belong to
	private LaneContext context;
	
	//the network of LaneNodes that shall be used to steer a boat
	private Network<LaneNode> net;
	//the last node added to the lane so we can determine where a new node should be joined on next.
	private LaneNode lastAddedNode;
	
	//used to determine whether this lane has been properly started as we should not allow 
	//a lane to be extended until it has been started
	private boolean started = false;
	
	 //The width the river should be roughly. 
	final private static double width = 10;
	
	// The distance between each node in the lane's network. 
	// Only need to determine the angle between each point.
	final private static double edge_length = 20;
	
	public Lane(LaneContext c, String projectionId) {
		top = new ArrayList<Point2D.Double>();
		bottom = new ArrayList<Point2D.Double>();
		
		this.context = c;
		
		NetworkBuilder<LaneNode> builder = new NetworkBuilder<LaneNode>(projectionId,
				this.context, true);
		builder.setEdgeCreator(new LaneEdgeCreator<LaneNode>());
		net = builder.buildNetwork();
	}
	
	/**
	 * Use to add the first node to the network that makes up this lane.
	 * Also adds the corresponding top and bottom boundaries for this first point/
	 * 
	 * @param start the location of the first node that will make up the 
	 */
	public void start(Point2D.Double start) {
		bottom.add(new Point2D.Double(start.getX(), start.getY() - width ));
		top.add(new Point2D.Double(start.getX(), start.getY() + width ));
		lastAddedNode = new LaneNode(start);
		
		started = true;
	}
	
	/**
	 * 
	 * @return true is the lane has been given a starting point.
	 */
	public boolean isStarted() {
		return started;
	}
	
	/**
	 * Extends a lane.
	 * 
	 *  Adds a new vertex and edge by defining a new LaneNode whose position 
	 *  is determined by following the angle provided for the distance defined by
	 *  #{link siver.river.lane.Lane.edge_length} 
	 * 
	 * @param heading the angle in radians at which the lane should head next
	 * @throws UnstartedLaneException
	 * @throws CompletedLaneException 
	 */
	public void extend(double heading) throws UnstartedLaneException, CompletedLaneException {
		if(!started) {
			throw new UnstartedLaneException("Cannot add a point when the Lane has not been started");
		}
		if(completed) {
			throw new CompletedLaneException("Cannot extend a lane that has already been completed");
		}
		
		AffineTransform at = new AffineTransform();
		at.translate(lastAddedNode.getLocation().getX(), lastAddedNode.getLocation().getY());
		at.rotate(heading);
		
		Point2D.Double next_top = new Point2D.Double();
		Point2D.Double next_mid = new Point2D.Double();
		Point2D.Double next_bottom = new Point2D.Double();
		
		at.transform(new Point2D.Double(edge_length, width), next_top);
		at.transform(new Point2D.Double(edge_length, 0), next_mid);
		at.transform(new Point2D.Double(edge_length, -width), next_bottom);
		
		top.add(next_top);
		
		bottom.add(next_bottom);
		LaneNode next = new LaneNode(next_mid);
		net.addEdge(lastAddedNode, next);
		lastAddedNode = next;
	}
	
	//GETTERS & SETTERS
	
	/**
	 * 
	 * @return the repast.simphony.space.graph.Network<LaneNode> that defines the route a boat in this lane
	 * will travel.
	 */
	public Network<LaneNode> getNet() {
		return net;
	}
	
	
}
