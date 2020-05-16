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
import java.util.LinkedList;
import java.util.List;

public class MapNode{
	private GeographicPoint node;
	private List<MapEdge> adjEdges;
	
	public MapNode(GeographicPoint location) {
		node = location;
		adjEdges = new ArrayList<MapEdge>();	
	}
	
	/** Return a list of out-neighbors from calling vertex/node
	 * 
	 * @return The list of nodes where the geographicalpoint is the end of an 
	 * edge in the adjEdges list, and where the point does not equal
	 * the calling node.
	 */
	public List<MapNode> getNeighbors() {
		return implementGetNeighbors("out");
	}
	
	/** Return a list of in-neighbors from calling vertex/node
	 * 
	 * @return The list of nodes where the geographicalpoint is the start of an 
	 * edge in the adjEdges list, and where the point does not equal
	 * the calling node.
	 */
	public List<MapNode> getInNeighbors() {
		return implementGetNeighbors("in");
	}
	
	/** Supports functionality to get in or out neighbors.
	 * 
	 * @param function a string that dictates whether the method will get
	 * in or out neighbors
	 * @return The list of mapnodes corresponding to the input
	 */
	private List<MapNode> implementGetNeighbors(String function){
		List<MapEdge> allEdges = this.getAllEdges();
		List<MapNode> neighbors = new ArrayList<MapNode>();
		GeographicPoint currGP = this.getCoord();
		for(MapEdge currEdge : allEdges) {
			if(function.equals("in")) {currGP = currEdge.getStart();}
			else{currGP = currEdge.getEnd();}
			if(!currGP.equals(node)) {
				neighbors.add(new MapNode(currGP));
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