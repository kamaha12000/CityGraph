/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
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
	//TODO: Add your member variables here in WEEK 2
	HashMap<GeographicPoint, MapNode> vertices;
	
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 2
		vertices = new HashMap<GeographicPoint, MapNode>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 2
		return vertices.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 2
		
		return vertices.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 2
		int count = 0;
		for(GeographicPoint loc : vertices.keySet()){
			count += vertices.get(loc).getEdges().size();
		}
		return count;
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
		// TODO: Implement this method in WEEK 2
		if(location == null || vertices.keySet().contains(location)){
			return false;
		}
		else {
			vertices.put(location, new MapNode(location));
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

		//TODO: Implement this method in WEEK 2
		if(!vertices.containsKey(from)){
			throw new IllegalArgumentException("Point" + from + " not found on the graph");
		}
		if(!vertices.containsKey(to)){
			throw new IllegalArgumentException("Point" + to + " not found on the graph");
		}
		MapNode fromNode = vertices.get(from);
		MapNode toNode = vertices.get(to);
		if(from == null){
			throw new IllegalArgumentException("Point" + from + " not found on the graph");
		}
		if(toNode == null){
			throw new IllegalArgumentException("Point" + to + " not found on the graph");
		}
		MapEdge eFromTo = new MapEdge(fromNode, toNode, roadName, roadType, length);
		fromNode.addEdge(eFromTo);
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
		// TODO: Implement this method in WEEK 2
		
		MapNode startNode = vertices.get(start);
		MapNode goalNode = vertices.get(goal);
		
		HashMap<MapNode,MapNode> parentMap = new HashMap<MapNode,MapNode>();
		Queue<MapNode> toExplore = new LinkedList<MapNode>();
		HashSet<MapNode> visited = new HashSet<MapNode>();
		
		MapNode curr = null;
		
		toExplore.add(startNode);
		
		while(!toExplore.isEmpty()){
			curr = toExplore.remove();
			// Hook for visualization.  See writeup.
			nodeSearched.accept(curr.getLocation());
			if (curr.equals(goalNode)){break;}
			
			for (MapNode neighbor:curr.getNeighbors()){
				if (!visited.contains(neighbor)){
					visited.add(neighbor);
					parentMap.put(neighbor, curr);
					toExplore.add(neighbor);
				}
			}
			
		}
		
		if (!curr.equals(goalNode)) {
			System.out.println("No path found from " +start+ " to " + goal);
			return null;
		}
		
		
		
		// Reconstruct the parent path
		List<GeographicPoint> path =
				reconstructPath(parentMap, startNode, goalNode);

		return path;
	}
	
	
	/** Reconstruct a path from start to goal using the parentMap
	 *
	 * @param parentMap the HashNode map of children and their parents
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from
	 *   start to goal (including both start and goal).
	 */

	private List<GeographicPoint> reconstructPath(HashMap<MapNode,MapNode> parentMap,
			MapNode start, MapNode goal)
	{
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		MapNode current = goal;

		while (!current.equals(start)) {
			path.addFirst(current.getLocation());
			current = parentMap.get(current);
		}

		// add start
		path.addFirst(start.getLocation());
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
		// TODO: Implement this method in WEEK 3
		
		int count = 0;
		MapNode startNode = vertices.get(start);
		MapNode goalNode = vertices.get(goal);
		
		HashMap<MapNode,MapNode> parentMap = new HashMap<MapNode,MapNode>();
		PriorityQueue<MapNode> toExplore = new PriorityQueue<MapNode>();
		HashSet<MapNode> visited = new HashSet<MapNode>();
		
		for(GeographicPoint key:vertices.keySet()){
			vertices.get(key).setDistance(100000000);
		}
		
		MapNode curr = null;
		startNode.setDistance(0);
		toExplore.add(startNode);
		
		
		while(!toExplore.isEmpty()){
			curr = toExplore.remove();
			// Hook for visualization.  See writeup.
			nodeSearched.accept(curr.getLocation());
			count++;
			if (!visited.contains(curr)){
				visited.add(curr);
				if (curr.equals(goalNode)){break;}
				Set<MapNode> setNeighbor = curr.getNeighbors();
				for (MapNode neighbor:setNeighbor){
					if (!visited.contains(neighbor)){
						// visited.add(neighbor);
						neighbor.setDistance(curr.getDistance() + (curr.toEdge(neighbor)).getLength());
						toExplore.add(neighbor);
						if (curr.getDistance() > neighbor.getDistance()){
						parentMap.put(neighbor, curr);
						}
					}
				}
				
				
			}
			
			
		}
		
		if (!curr.equals(goalNode)) {
			System.out.println("No path found from " +start+ " to " + goal);
			return null;
		}
		
		
		
		
		// Reconstruct the parent path
		List<GeographicPoint> path =
				reconstructPath1(parentMap, startNode, goalNode);
		System.out.println(count);
		return path;
		
		
	}
	
	
	/** Reconstruct a path from start to goal using the parentMap
	 *
	 * @param parentMap the HashNode map of children and their parents
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from
	 *   start to goal (including both start and goal).
	 */
	private List<GeographicPoint> reconstructPath1(HashMap<MapNode,MapNode> parentMap,
			MapNode start, MapNode goal)
	{
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		MapNode current = goal;
	
		HashSet<MapNode> visited = new HashSet<MapNode>();
		
		Set<MapNode> setParentMap = parentMap.keySet();
		

		while (!current.equals(start)) {
			visited.add(current);
			
			
			path.addFirst(current.getLocation());
			current = parentMap.get(current);
		}

		// add start
		path.addFirst(start.getLocation());
		return path;
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
		// TODO: Implement this method in WEEK 3
		
		// TODO: Implement this method in WEEK 3
		
				int count =0;
				MapNode startNode = vertices.get(start);
				MapNode goalNode = vertices.get(goal);
				
				HashMap<MapNode,MapNode> parentMap = new HashMap<MapNode,MapNode>();
				PriorityQueue<MapNode> toExplore = new PriorityQueue<MapNode>();
				HashSet<MapNode> visited = new HashSet<MapNode>();
				
				for(GeographicPoint key:vertices.keySet()){
					vertices.get(key).setDistance(100000000);
				}
				
				MapNode curr = null;
				startNode.setDistance(0);
				toExplore.add(startNode);
				
				
				while(!toExplore.isEmpty()){
					curr = toExplore.remove();
					// Hook for visualization.  See writeup.
					nodeSearched.accept(curr.getLocation());
					count++;
					if (!visited.contains(curr)){
						visited.add(curr);
						if (curr.equals(goalNode)){break;}
						Set<MapNode> setNeighbor = curr.getNeighbors();
						for (MapNode neighbor:setNeighbor){
							if (!visited.contains(neighbor)){
								// visited.add(neighbor);
								neighbor.setDistance(curr.getDistance() + 
										(curr.toEdge(neighbor)).getLength() + curr.getLocation().distance(goal));
								toExplore.add(neighbor);
								parentMap.put(neighbor, curr);
							}
						}
						
						
					}
					
					
				}
				
				if (!curr.equals(goalNode)) {
					System.out.println("No path found from " +start+ " to " + goal);
					return null;
				}
				
				
				
				
				// Reconstruct the parent path
				List<GeographicPoint> path =
						reconstructPath1(parentMap, startNode, goalNode);
				System.out.println(count);
				return path;
				
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
	}

	
	
	public static void main(String[] args)
	{/*
		System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
		System.out.println("DONE.");
		*/
		
		// You can use this method for testing.  
		
		// Use this code in Week 3 End of Week Quiz
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		
		
	}
	
}
