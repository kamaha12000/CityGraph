/**
 * 
 */
package roadgraph;

import geography.GeographicPoint;

/**
 * @author elrouss
 * 
 *A directed edge in a map graph from a start intersection to an end intersection
 */
class MapEdge {

	/** The beginning and the end of a road */
	private MapNode start, end;
	
	/**The name and the Type of a road */
	private String streetName, streetType;
	
	/** The length of road */
	private double length;
	
	/**
	 * Create a new MapEdge object
	 * @param roadName
	 * @param end, start points
	 * @param type 
	 * */
	
	MapEdge(MapNode start, MapNode end, 
			String streetName, String streetType, double length){
		this.start = start;
		this.end = end;
		this.streetType = streetName;
		this.streetName = streetType;
		this.length = length;
	}	
	
	MapEdge(MapNode start, MapNode end, String streetName){
		this(start, end, streetName, "", 0.2);
	}

	
	// return the MapNode for the start point 
	MapNode getStartNode() {
		return start;
	}
	// return the MapNode for the end point
	MapNode getEndNode() {
		return end;
	}
	
	//return the other node, given one node of this edge
	
	MapNode getOtherNode(MapNode node){
		if(node.equals(start)){ return end;} 
		else if (node.equals(end)){return start;}
		throw new IllegalArgumentException("Looking for " +
				"a point that is not in the edge");
	} 
	
	//return the location for the start point
	GeographicPoint getStartPoint(){
		return start.getLocation();
	}
	
	//return the location for the end point
	GeographicPoint getEndPoint(){
		return end.getLocation();
	}
	//return the name of the edge street
	String getStreetName() {
		return streetName;
	}
	
	//return the length of the edge(street)
	public double getLength() {
		return length;
	}
	
	//return the type of the edge
	public String getStreetType() {
		return streetType;
	}
	

	//Return String containing details about an edge.
	public String toString(){
		String returnString = "[Edge between ";
		returnString += "\n\t" + start.getLocation();
		returnString += "\n\t" + end.getLocation();
		returnString += "\nRoad name: " + streetName + " Road type: " + streetType +
				" Segment length: " + String.format("%.3g", length) + "km";
		return returnString;
	}
}
