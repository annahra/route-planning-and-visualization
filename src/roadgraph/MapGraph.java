/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	private HashMap<GeographicPoint, MapNode> mainMap;
	private int numNodes;
	private int numEdges;
	private boolean isFound;
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		mainMap = new HashMap<GeographicPoint, MapNode>();
		numNodes = 0;
		numEdges = 0;
		isFound = false;
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		int holder = numNodes;
		return holder;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		Set<GeographicPoint> vertices = new HashSet<GeographicPoint>();
		for(GeographicPoint gp : mainMap.keySet()) {
			vertices.add(gp);
		}
		return vertices;
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		int holder = numEdges;
		return holder;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		if(location == null || mainMap.containsKey(location)) {
			return false;
		}
		else {
			MapNode currNode = new MapNode(location);
			mainMap.put(location, currNode);
			numNodes += 1;
			return true;
		}
		
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		if(!mainMap.containsKey(from) || !mainMap.containsKey(to)
				|| length < 0 || from == null || to == null || roadName == null
				|| roadType == null) {
			throw new IllegalArgumentException();
		}
		
		implementAddEdge(from,to,roadName,roadType,length);
		
	}
	
	/**
	 * Actual implementation of adding an edge of the graph
	 * 
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 *
	 */
	private void implementAddEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) {
		MapEdge currEdge = new MapEdge(from,to,roadName,roadType,length);
		MapNode startNode = mainMap.get(from);
		startNode.addAdjEdge(currEdge);
		numEdges += 1;
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		if (start == null || goal == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return null;
		}
		
		//initialization
		List<MapNode> queue = new LinkedList<MapNode>();
		queue.add(mainMap.get(start));
		Set<MapNode> visited = new HashSet<MapNode>();
		visited.add(mainMap.get(start));
		Map<GeographicPoint, GeographicPoint> parentMap = new HashMap<GeographicPoint,GeographicPoint>();
		boolean found = false;
		MapNode currNode = mainMap.get(start);
		MapNode nextNode = mainMap.get(start);

		
		//search graph
		while(queue.size()!=0) {
			currNode = queue.remove(0);
			nodeSearched.accept(currNode.getCoord());
			if(currNode.getCoord().equals(goal)) {
				found = true;
				break;
			}
			List<GeographicPoint> neighbors = currNode.getNeighbors();
			for(GeographicPoint nextGP : neighbors) {
				nextNode = mainMap.get(nextGP);
				if(!visited.contains(nextNode)) {
					visited.add(nextNode);
					parentMap.put(nextNode.getCoord(),currNode.getCoord());
					queue.add(nextNode);
					//nodeSearched.accept(nextNode.getCoord());
				}
			}
		}
		
		//for case not found
		if(!found) {
			System.out.println("No path exists");
			return null;
		}
		
		return reconstructPath(parentMap,goal,start);
	}
	
	/** Reconstructs the path given a parentMap. Starts from the goal GeographicPoint and
	 * ends at the start geographic point
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param parentMap A map of the path it took to get from start to goal
	 * @return The list of GeographicPoints that constitute as the path from start to goal 
	 */
	private List<GeographicPoint> reconstructPath(Map<GeographicPoint,GeographicPoint> parentMap, GeographicPoint goal,
									GeographicPoint start){
		List<GeographicPoint> path = new LinkedList<GeographicPoint>();
		GeographicPoint currPathCoord = goal;
		while(!currPathCoord.equals(start)) {
			((LinkedList<GeographicPoint>) path).addFirst(currPathCoord);
			currPathCoord = parentMap.get(currPathCoord);
		}
		((LinkedList<GeographicPoint>) path).addFirst(start);
		return path;
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the parentMap using Dijkstra's or AStar's algorithm
	 * 
	 * @param start: The starting location
	 * @param goal: The goal location
	 * @param nodeSearched: A hook for visualization.  See assignment instructions for how to use it.
	 * @param type: indicates whether to use dijkstra's algorithm or astar
	 * @return The map that keeps the path of geographic points to get from start to goal
	 */
	private Map<GeographicPoint,GeographicPoint> getMap(GeographicPoint start, 
			  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched, int type){
		isFound = false;
		Comparator<MapNode> comparator = new MapNode();
		PriorityQueue<MapNode> queue = new PriorityQueue<MapNode>(comparator);
		Set<MapNode> visited = new HashSet<MapNode>();
		Map<GeographicPoint, GeographicPoint> parentMap = new HashMap<GeographicPoint, GeographicPoint>();
		for(MapNode mn : mainMap.values()) {
			mn.setDistFromStart(Double.POSITIVE_INFINITY);
		}
		MapNode currNode = mainMap.get(start);
		currNode.setDistFromStart(0);
		queue.add(currNode);
		int count = 0;
		//search!
		while(queue.size() != 0) {
			currNode = queue.remove();
			count +=1;
			nodeSearched.accept(currNode.getCoord());
			if(!visited.contains(currNode)) {
				visited.add(currNode);
				if(currNode.getCoord().equals(goal)) {
					isFound = true;
					System.out.println(count);
					return parentMap;
				}
				List<GeographicPoint> neighbors = currNode.getNeighbors();
				
				for(GeographicPoint currNeighbor : neighbors) {
					
					MapNode nextNode = mainMap.get(currNeighbor);
					if(!visited.contains(nextNode)) {
						//set dist from start
						double currDist = nextNode.getDistFromStart();
						if(type == 1) {
							currDist = currNode.calcDistFromStart(nextNode);
							//System.out.println("Dijkstra: "+currNode.getCoord()+" to "+nextNode.getCoord()+" : "+currDist);
						}
						else {
							currDist = currNode.calcDistFromStart(nextNode,goal);
							//System.out.println("Astar: "+currNode.getCoord()+" to "+nextNode.getCoord()+" : "+currDist);
						}
						if(currDist < nextNode.getDistFromStart()) {
							nextNode.setDistFromStart(currDist);
							parentMap.put(nextNode.getCoord(), currNode.getCoord());
							
							queue.add(nextNode);
						}
						
					}
				}
			}
		}
		
		return parentMap;
	}
	
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		
		if (start == null || goal == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return null;
		}
		Map<GeographicPoint,GeographicPoint> parentMap = getMap(start,goal,nodeSearched,1);
		//for case not found
		if(!isFound) {
			System.out.println("No path exists");
			return null;
		}
				
		return reconstructPath(parentMap,goal,start);		
		
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		if (start == null || goal == null) {
			System.out.println("Start or goal node is null!  No path exists.");
			return null;
		}
		Map<GeographicPoint,GeographicPoint> parentMap = getMap(start,goal,nodeSearched,5);
		//for case not found
		if(!isFound) {
			System.out.println("No path exists");
			return null;
		}
		
		return reconstructPath(parentMap,goal,start);		
		
	}

	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		
		
		// You can use this method for testing.  
		
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		
//		MapGraph simpleTestMap = new MapGraph();
//		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
//		
//		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
//		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
//		
//		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
//		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
//		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		
//		

		
//		MapGraph testMap = new MapGraph();
//		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
////		
//		// A very simple test using real data
//		testStart = new GeographicPoint(32.869423, -117.220917);
//		testEnd = new GeographicPoint(32.869255, -117.216927);
//		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
//		testroute = testMap.dijkstra(testStart,testEnd);
//		testroute2 = testMap.aStarSearch(testStart,testEnd);
//		
////		
//		// A slightly more complex test using real data
//		testStart = new GeographicPoint(32.8674388, -117.2190213);
//		testEnd = new GeographicPoint(32.8697828, -117.2244506);
//		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
//		testroute = testMap.dijkstra(testStart,testEnd);
//		testroute2 = testMap.aStarSearch(testStart,testEnd);
//		
//		
//		for(GeographicPoint gp: testroute) {
//			System.out.println(gp);
//		}
//		System.out.println("-----------");
//		for(GeographicPoint gp: testroute2) {
//			System.out.println(gp);
//		}		
		
		
		
		/* Use this code in Week 3 End of Week Quiz */
//		MapGraph theMap = new MapGraph();
//		System.out.print("DONE. \nLoading the map...");
//		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
//		System.out.println("DONE.");
//
//		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
//		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
//		
//		
//		List<GeographicPoint> route = theMap.dijkstra(start,end);
//		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
		
		
		
	}
	
}
