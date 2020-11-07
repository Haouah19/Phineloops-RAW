package fr.dauphine.javaavance.phineloops.model.utils;

import java.util.HashMap;
import java.util.Map;
/**
 * Description : Class representing a Piece with a two connection, TwoConnections seeing 2 neighbors
 */
public class TwoConnections extends Piece{
	/**
	 * 	Orientation : Enumerated variable, used to restrict the domain of the orientation variable.
	 * 	orientation : represents The orientation of the piece,
	 */
	private enum Orientation {
		NORTH_SOUTH(0),
		EAST_WEST(1);
			
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
	 * Build a Piece that sees two neighbors
	 * @param position : initial position of the Piece, Does not change during execution
	 * @param orientation : initial Orientation of the piece, Orientation is an integer, which we convert to Type Orientation
	 * @param nb_connection : Number of actual connections fixed when creating the Piece
	 */
	public TwoConnections(int position, int orientation) {
		super(position,2,2);
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
	    if(this.orientation.getValue() == 0)
		    	return "\u2502"; // pièce 2 connection nord-sud
		return "\u2500"; // pièce 2 connection est-ouest 
	}
	
	//The following four methods will return true if the piece has a connection on the 
			//specified side, false if not.
			public boolean hasNorthConnection()
			{
				if (this.getOrientation() == 0)
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
				if (this.getOrientation() == 0)
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
				if (this.getOrientation() == 1)
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
				if (this.getOrientation() == 1)
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
		TwoConnections other = (TwoConnections) obj;
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
		return new TwoConnections(this.getPosition(), this.getOrientation());
	}

}
