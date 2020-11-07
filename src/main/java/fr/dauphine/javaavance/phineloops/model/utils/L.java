package fr.dauphine.javaavance.phineloops.model.utils;

import java.util.HashMap;
import java.util.Map;
/**
 * Description : Class representing a Piece with Two connections, FourConnections seeing 2 neighbors (L)
 */
public class L extends Piece{
	/**
	 *  NB_CONNECTIONS_POSSIBLE : Constant fixed, Number of possible connections
	 *  TYPE : Constant fixed, represents the number of the Piece
	 * 	Orientation : Enumerated variable, used to restrict the domain of the orientation variable.
	 * 	orientation : represents The orientation of the piece,
	 * 	nb_connection : number of connections made, int between 0 and NB_CONNECTIONS_POSSIBLE
	 */
	private enum Orientation {
		NORTH_EAST(0),
		EAST_SOUTH(1),
		SOUTH_WEST(2),
		WEST_NORTH(3);
			
		private int value;
	    private static Map<Integer,Orientation> map = new HashMap<>();
		private Orientation(int value) {
		    this.value = value;
		}
	    static {
	        for (Orientation orientation: Orientation.values()) {
	            map.put(orientation.value, orientation);
	        }
	    }
		public static Orientation valueOf(int orientation) {
	        return (Orientation) map.get(orientation);
	    }
		public int getValue() {
		    return value;
		}
		
		//The following four methods will return true if the piece has a connection on the 
		//specified side, false if not.
		
	};
	
	private Orientation orientation;; 
	
	/**
	 * Build a Piece that sees two neighbors L
	 * @param position : initial position of the Piece, Does not change during execution
	 * @param orientation : initial Orientation of the piece, Orientation is an integer, which we convert to Type Orientation
	 * @param nb_connection : Number of actual connections fixed when creating the Piece
	 */
	public L(int position, int orientation) {
		super(position,2,5);
		this.orientation = Orientation.valueOf(orientation);
	}
	
	/**
	 * Updates the orientation of the piece
	 * @param the new orientation of the Piece
	 */
	public int getOrientation() {
		if(this.orientation != null)
			return this.orientation.getValue();
		return -1;
	}
	
	/**
	 * Updates the orientation of the piece
	 * @param the new orientation of the Piece
	 */
	public void putOrientation(int orientation) {
		this.orientation = Orientation.valueOf(orientation);
	}


	public String toString() {
	    switch(this.orientation.getValue()) {
			case 1: 
		    	return "\u250C"; // pièce L est-sud
		    case 2: 
		    	return "\u2510"; // pièce L sud-ouest
		    case 3: 
		    	return "\u2518"; // pièce L ouest-nord
	    }
	    return "\u2514"; // pièce L nord-est
	}
	
	/**
	 * methods that let us know if a room has a north, east, south and west connection
	 */
	public boolean hasNorthConnection()
	{
		if (this.getOrientation() == 0 || this.getOrientation() == 3)
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}
	
	public boolean hasSouthConnection()
	{
		if (this.getOrientation() == 1 || this.getOrientation() == 2)
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}
	
	public boolean hasEastConnection()
	{
		if (this.getOrientation() == 0 || this.getOrientation() == 1)
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}
	
	public boolean hasWestConnection()
	{
		if (this.getOrientation() == 2 || this.getOrientation() == 3)
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		L other = (L) obj;
		if (getNbConncetionsPossible() != other.getNbConncetionsPossible())
			return false;
		if (getType() != other.getType())
			return false;
		if (getNbConnections() != other.getNbConnections())
			return false;
		if (orientation != other.orientation)
			return false;
		return true;
	}

	@Override
	public Piece Clone() 
	{	
		return new L(this.getPosition(), this.getOrientation());
	}
}
