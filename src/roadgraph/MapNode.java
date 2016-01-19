/**
 * 
 */
package roadgraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import geography.GeographicPoint;

/**
 * @author elrouss
 *
 */
class MapNode implements Comparable {
	/** geographic coordinates(latitude + longitude) of this node*/
	private GeographicPoint location;
	
	/** list containing all the edges(roads) of this node */
	private List<MapEdge> edges;
	/** the predicted distance of this node (used in Week 3 algorithms) */
	private double distance;
	/** the actual distance of this node (used in Week 3 algorithms) */
	private double actualDistance;
	

	/**Create a new MapNode object*/
	MapNode(GeographicPoint location) {
		this.location = location;	
		edges = new ArrayList<MapEdge>(); 
	}
	
	//return location of this node
	GeographicPoint getLocation(){
		return location;
	}
	
	// this method add an edge to the list of edge.
	void addEdge(MapEdge edge){
		edges.add(edge);
	}
	
	
	//return edge for this node 
	List<MapEdge> getEdges()
	{
		return edges;
	}
	
	/**return if two nodes are equal
	 * node are considered equal if their location is equal
	 * even if their street name or street type is different.	
	 */
	public boolean equals(Object o){
		if (!(o instanceof MapNode)||(o == null)){
			return false;
		}
		else {
			MapNode node = (MapNode)o;
			return node.location.equals(this.location);
		}
	}
	
	/** Display information about this node 
	 * 
	 */
	
	public String toString(){
		String toReturn = "[NODE at location (" + location + ")";
		toReturn += " intersects streets: ";
		for (MapEdge e: edges) {
			toReturn += e.getStreetName() + ", ";
		}
		toReturn += "]";
		return toReturn;
	}
	
	
	/** Return the neighbors of this MapNode */
	Set<MapNode> getNeighbors()
	{
		Set<MapNode> neighbors = new HashSet<MapNode>();
		for (MapEdge edge : edges) {
			neighbors.add(edge.getOtherNode(this));
		}
		return neighbors;
	}
	
	
	// get node distance (predicted)
		public double getDistance() {
			return this.distance;
		}
		
		// set node distance (predicted)
		public void setDistance(double distance) {
		    this.distance = distance;
		}

		// get node distance (actual)
		public double getActualDistance() {
			return this.actualDistance;
		}
		
		// set node distance (actual)	
		public void setActualDistance(double actualDistance) {
		    this.actualDistance = actualDistance;
		}
	
	
	//Implementation of the compareTo() method to satisfy
	//compliances with Comparable interface
	
	public int compareTo(Object o){
		MapNode node = (MapNode)o;
		return ((Double)this.getDistance()).compareTo((Double)node.getDistance());
	}
	
	
	/**return an edge between this node and a given node.
	*or null if there is no connection between the two nodes.
	*/
	public MapEdge toEdge(MapNode node){
		for(MapEdge e:this.edges){
			if(node.equals(e.getOtherNode(this))){
				return e;
			}
		}
		return null;
	}
	
	
	
}
