/**
 * @author Annah
 * 
 * A class which represents an intersection on a map, or more generally 
 * a node/vertex on a graph. 
 * The edges that are stored in adjEdges describe edges of a directed graph
 * that go from the calling node to the node being stored in that list.
 *
 */
package roadgraph;
import geography.GeographicPoint;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MapNode implements Comparator<MapNode>{
	private GeographicPoint node;
	private List<MapEdge> adjEdges;
	private double distFromStart;
	private Map<GeographicPoint, Double> nodeToNode;
	
	public MapNode(GeographicPoint location) {
		node = location;
		adjEdges = new ArrayList<MapEdge>();	
		nodeToNode = new HashMap<GeographicPoint, Double>();
		//distFromStart = 0;
	}
	public MapNode() {
		node = null;
		adjEdges = new ArrayList<MapEdge>();	
		nodeToNode = new HashMap<GeographicPoint, Double>();
		//distFromStart = 0;
	}
	
	@Override
	public int compare(MapNode mp1, MapNode mp2) {
		if(mp1.getDistFromStart() < mp2.getDistFromStart()) {return -1;}
		if(mp1.getDistFromStart() > mp2.getDistFromStart()) {return 1;}
		return 0;
	}
	
	/** Return a double, adds the current node's distance from start to 
	 * the length of the path between current node to some node mn to
	 * the distance between mn's node to the goal. Used in Astar algorithm
	 * 
	 * @param mn: a MapNode that represents the next mapNode to look at
	 * @param gp: a GeographicPoint that represents the goal we are trying to get to
	 * @return double
	 */
	public double calcDistFromStart(MapNode mn, GeographicPoint gp) {
		//double prevNodeDist = mn.getDistFromStart();
		double pathLength = nodeToNode.get(mn.getCoord());
		double distFromGoal = mn.getCoord().distance(gp);

		return this.getDistFromStart()+pathLength+distFromGoal;
	}
	
	/** Return a double, adds the current node's distance from start to 
	 * the length of the path between current node. Used in Dijkstra's algorithm
	 * 
	 * @param mn: a MapNode that represents the next mapNode to look at
	 * @return double
	 */
	public double calcDistFromStart(MapNode mn) {
		
		double pathLength = nodeToNode.get(mn.getCoord());
		
		
		return this.getDistFromStart()+pathLength;
	}
	
	public boolean setDistFromStart(double d) {
		distFromStart = d;
		return true;
	}
	
	public double getDistFromStart() {
		double holder = distFromStart;
		return holder;
	}
	
	/** Return a list of out-neighbors from calling vertex/node
	 * 
	 * @return The list of GeographicPoints where the geographical point is the end of an 
	 * edge in the adjEdges list, and where the point does not equal
	 * the calling node.
	 */
	public List<GeographicPoint> getNeighbors() {
		return implementGetNeighbors("out");
	}
	
	/** Return a list of in-neighbors from calling vertex/node
	 * 
	 * @return The list of GeographicPoints where the geographicalpoint is the start of an 
	 * edge in the adjEdges list, and where the point does not equal
	 * the calling node.
	 */
	public List<GeographicPoint> getInNeighbors() {
		return implementGetNeighbors("in");
	}
	
	/** Supports functionality to get in or out neighbors.
	 * 
	 * @param function a string that dictates whether the method will get
	 * in or out neighbors
	 * @return The list of GeographicPoints corresponding to the input
	 */
	private List<GeographicPoint> implementGetNeighbors(String function){
		List<MapEdge> allEdges = this.getAllEdges();
		List<GeographicPoint> neighbors = new ArrayList<GeographicPoint>();
		GeographicPoint currGP = this.getCoord();
		for(MapEdge currEdge : allEdges) {
			if(function.equals("in")) {currGP = currEdge.getStart();}
			else{currGP = currEdge.getEnd();}
			if(!currGP.equals(node)) {
				neighbors.add(currGP);
				nodeToNode.put(currGP, currEdge.getLength());
			}
		}
		return neighbors;
	}
	
	public GeographicPoint getCoord() {
		GeographicPoint gp = node;
		return gp;
	}
	
	public void addAdjEdge(MapEdge currEdge) {
		adjEdges.add(currEdge);
	}
	
	public MapEdge getAnEdge(int index) {
		MapEdge currEdge = adjEdges.get(index);
		return currEdge;
	}
	
	public List<MapEdge> getAllEdges(){
		List<MapEdge> holder = adjEdges;
		return holder;		
	}

	
}