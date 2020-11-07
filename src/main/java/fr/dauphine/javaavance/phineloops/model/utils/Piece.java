package fr.dauphine.javaavance.phineloops.model.utils;

/**
 * Description : Class representing a piece in a grid
 */
public abstract class Piece{
	/**
	 * Position represents the position of the piece in the grid, 
	 * this attribute is between 0 and the size (length * width) of the grid.
	 *  NB_CONNECTIONS_POSSIBLE : Constant fixed, Number of possible connections
	 *  TYPE : Constant fixed, represents the number of the Piece
	 * nb_connection : number of connections made, int between 0 and NB_CONNECTIONS_POSSIBLE
	 */
	private int position;
	private final int NB_CONNECTIONS_POSSIBLE;
	private final int TYPE;
	private int nb_connections;
	
	/**
	 * 
	 * @param position : this attribute is between 0 and the size (length * width) of the grid.
	 */
	public Piece(int position, int nbCoMax,int type) {
		this.position = position;
		this.NB_CONNECTIONS_POSSIBLE=nbCoMax;
		this.TYPE=type;
		this.nb_connections=0;
	}
	
	/**
	 * Copy constructor
	 * @param p the piece to be cloned
	 */
	public Piece(Piece p)
	{
		this.position=p.position;
		this.NB_CONNECTIONS_POSSIBLE=p.NB_CONNECTIONS_POSSIBLE;
		this.TYPE=p.TYPE;
		this.nb_connections=p.nb_connections;
	}
	

	/**
	 * Method which returns the position of the piece in the grid
	 * @return position 
	 */
	public int getPosition() {
		return this.position;
	}
	
	/**
	 * Method which updates the position of the part in the grid
	 * Normally it will never be used
	 * @param position : New position of the part in the grid
	 */
	public void setPosition(int position) {
		this.position = position;
	}
	
	/**
	 * method that returns the type of the piece
	 * @return the type of the piece
	 */
	public int getType()
	{
		return this.TYPE;
	}
	
	/**
	 * Returns an integer representing the actual number of connections
	 * @return the actual number of connections
	 */
	public int getNbConnections()
	{
		return this.nb_connections;
	}
	
	
	/**
	 * Sets the number of connections
	 * @param nbCo
	 */
	public void setNbConnections(int nbCo)
	{
		this.nb_connections=nbCo;
	}
	
	public int getNbConncetionsPossible()
	{
		return this.NB_CONNECTIONS_POSSIBLE;
	}
	
	/**
	 * Method which returns a boolean which represents the state of the Piece connections
	 * @return the state of the piece
	 */
	public boolean isConnected()
	{
		return this.nb_connections == this.NB_CONNECTIONS_POSSIBLE;
	}

	public abstract int getOrientation();
	public abstract void putOrientation(int orientation);
	public abstract boolean hasNorthConnection();
	public abstract boolean hasSouthConnection();
	public abstract boolean hasEastConnection();
	public abstract boolean hasWestConnection();
	public abstract Piece Clone();
}
