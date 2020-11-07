package fr.dauphine.javaavance.phineloops.model.utils;

import java.util.HashMap;
import java.util.Map;
/**
 * Description : Class representing a Piece with a three connection, ThreeConnections seeing 3 neighbors (T)
 */
public class ThreeConnections extends Piece{
	/**
	 * 	Orientation : Enumerated variable, used to restrict the domain of the orientation variable.
	 * 	orientation : represents The orientation of the piece,
	 */
	private enum Orientation {
		NORTH_EAST_WEST(0), 
		NORTH_SOUTH_EAST(1), 
		EAST_SOUTH_WEST(2),
		NORTH_SOUTH_WEST(3);
		
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
		
		
	};
	
	private Orientation orientation;
	
	/**
	 * Build a Piece that sees three neighbors
	 * @param position : initial position of the Piece, Does not change during execution
	 * @param orientation : initial Orientation of the piece, Orientation is an integer, which we convert to Type Orientation
	 * @param nb_connection : Number of actual connections fixed when creating the Piece
	 */
	public ThreeConnections(int position, int orientation) {
		super(position,3,3);
		this.orientation = Orientation.valueOf(orientation);
	}
	
	/**
	 * Returns an integer representing the orientation of the Piece
	 * @return the orientation of the piece
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
		    	return "\u251C"; // pièce 3 connections (T) nord-sud-est
		    case 2: 
		    	return "\u252C"; // pièce 3 connections (T) est-sud-ouest
		    case 3: 
		    	return "\u2524"; // pièce 3 connections (T) nord-sud-ouest
	    }
	    return "\u2534"; // pièce 3 connections (T) nord-est-ouest
	}
	
	//The following four methods will return true if the piece has a connection on the 
			//specified side, false if not.
			public boolean hasNorthConnection()
			{
				if (this.getOrientation() == 0 || this.getOrientation() == 1 || this.getOrientation() == 3)
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
				if (this.getOrientation() == 1 || this.getOrientation() == 2 || this.getOrientation() == 3)
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
				if (this.getOrientation() == 0 || this.getOrientation() == 1 || this.getOrientation() == 2)
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
				if (this.getOrientation() == 0 || this.getOrientation() == 2 || this.getOrientation() == 3)
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
		ThreeConnections other = (ThreeConnections) obj;
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
		return new ThreeConnections(this.getPosition(), this.getOrientation());
	}

}
