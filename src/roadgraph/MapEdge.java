/**
 * @author Annah
 * 
 * A class which represents an road on a map, or more generally 
 * an edge/connection on a graph. 
 *
 */
package roadgraph;
import geography.GeographicPoint;
import java.util.List;

public class MapEdge{
	private GeographicPoint start;
	private GeographicPoint end;
	private String roadName;
	private String roadType;
	private double length;
	
	public MapEdge(GeographicPoint from, GeographicPoint to, String name,
					String type, double len) {
		start = from;
		end = to;
		roadName = name;
		roadType = type;
		length = len;
	}
	
	public double getLength() {
		double len = length;
		return len;
	}
	
	public String getRoadType() {
		String type = roadType;
		return type;
	}
	
	public String getRoadName() {
		String name = roadName;
		return name;
	}
	
	public GeographicPoint getStart() {
		GeographicPoint st = start;
		return st;
	}
	
	public GeographicPoint getEnd() {
		GeographicPoint en = end;
		return en;
	}
	
}