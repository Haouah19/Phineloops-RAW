package fr.dauphine.javaavance.phineloops.model.utils;
/**
 * Description : Class representing a Piece with a four connections, FourConnections seeing 4 neighbors (+)
 */
public class FourConnections extends Piece{
	/**

	 * 	Orientation : Enumerated variable, used to restrict the domain of the orientation variable.
	 * 	orientation : represents The orientation of the piece,
	 */
	private enum Orientation {
		NORTH_EAST_SOUTH_WEST(0);
			
		private int value;

		private Orientation(int value) {
		    this.value = value;
		}

	    public static Orientation valueOf(int orientation) {
	        return Orientation.NORTH_EAST_SOUTH_WEST;
	    }
		public int getValue() {
		    return value;
		}
		
		//The following four methods will return true if the piece has a connection on the 
		//specified side, false if not.
		
	};
	
	private Orientation orientation;
	
	/**
	 * Build a Piece that sees three neighbors
	 * @param position : initial position of the Piece, Does not change during execution
	 * @param orientation : initial Orientation of the piece, Orientation is an integer, which we convert to Type Orientation
	 * @param nb_connection : Number of actual connections fixed when creating the Piece
	 */
	public FourConnections(int position,int orientation) {
		super(position,4,4);
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
		return "\u253C";  // pièce 4 connections nord-sud-est-ouest
	}

	/**
	 * methods that let us know if a room has a north, east, south and west connection
	 */
	public boolean hasNorthConnection()
	{
		return true;
	}
			
	public boolean hasSouthConnection()
	{
		return true;
	}
			
	public boolean hasEastConnection()
	{
		return true;
	}
			
	public boolean hasWestConnection()
	{

		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FourConnections other = (FourConnections) obj;
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
		return new FourConnections(this.getPosition(), this.getOrientation());
	}
}
