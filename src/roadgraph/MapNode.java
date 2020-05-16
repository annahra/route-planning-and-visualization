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
import java.util.List;

public class MapNode{
	private GeographicPoint node;
	private List<MapEdge> adjEdges;
	
	public MapNode(GeographicPoint location) {
		node = location;
		adjEdges = new ArrayList<MapEdge>();
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