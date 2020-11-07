package fr.dauphine.javaavance.phineloops.model;

import java.util.ArrayList;


import fr.dauphine.javaavance.phineloops.model.utils.Piece;
import fr.dauphine.javaavance.phineloops.view.Observer;
/**
 * Class representing a grid of a size length * width
 * It will allow us to browse the grid, insert/extract elements into it, and check if it is resolved
 */
public class Grid implements Observable{
	/**
	 * width : represents the width of the grid
	 * height : represents the length of the grid
	 * nbC : number of related components
	 * grid : List of Pieces in the grid, the list size is length * width
	 */
	private int width;
	private int height;
	private int nbC; // initialized to zero
	private ArrayList<Piece> grid;
	private ArrayList<Observer> observers;

	/**
	 * Constructor which allows us to install a grid, of a length and width recovered in parameters. 
	 * We also add the number of related components
	 * The ArrayList<Piece> list is empty
	 * @param width represents the width of the grid
	 * @param height represents the length of the grid
	 * @param nbC number of related components
	 */
	public Grid(int width, int height, int nbC) {
		this.width = width;
		this.height = height;
		this.nbC = nbC;
		this.grid = new ArrayList<Piece>();
		this.observers = new ArrayList<Observer>();
		
	}
	
	/**
	 * Constructor which allows us to install a grid, of a length and width recovered in parameters
	 * The ArrayList<Piece> list is empty
	 * @param width : represents the width of the grid
	 * @param height : represents the length of the grid
	 */
	public Grid(int width, int height) {
		this.width = width;
		this.height = height;
		this.grid = new ArrayList<Piece>();
		this.observers = new ArrayList<Observer>();
	}
	
	/**
	 * @return the width of the grid
	 */
	public int getWidth() {
		return this.width;
	}
	
	//retruns the arraylist of pieces
	public ArrayList<Piece> getGrid(){
		return this.grid;
	}
	
	/**
	 * @return the height of the grid
	 */
	public int getheight() {
		return this.height;
	}
	
	// Il faut créer une exception pour dire qu'il existe dèjà une piece à l'index
	// et si l'index est plus que la taille
	/**
	 * method that allows us to add a Piece to the grid
	 * @param index : the position of the new piece, an int between 0 and width * height
	 * @param piece : the new Piece added to the grid
	 */
	public void putPiece(int index, Piece piece) {
		grid.add(index, piece);
	}
	
	// Il faut gérer l'exception IndexOutOfBoundsException
	// elle ne risque pas d'arriver
	/**
	 * Method for retrieving a Piece at a certain position
	 * @param index : The position of the piece
	 * @return the piece at the index position
	 * @exception : IndexOutOfBoundsException
	 */
	public Piece getPiece(int index) {
		return grid.get(index);
	}
	
	/**
	 * This method is useful for notifying the observer when an event occurs in the view,
	 * we change the orientation of the piece at the index position, and we notify the observer
	 * @param index The position of the piece in the Grid
	 * @param orientation the new orientation of the piece
	 */
	public void putOrientation(int index, int orientation) {
		if(grid.get(index) != null) {
			this.getPiece(index).putOrientation(orientation);
			this.reCalculateConnections(getPiece(index));
			notifyObserver();
		}
	}
	
	/**
	 * browse the list and verify that each Piece is connected to its neighbors
	 * @return a boolean according to the state of the grid
	 */
	public boolean isSolved() {
		for(Piece piece : grid) {
			if(piece != null)
				if(!piece.isConnected())
					return false;
		}
		return true;
	}
	
	/**
	 * the method returns a character string used to describe the Grid concerned.
	 * We use StringBuilder to concatenate strings in an optimized way. 
	 * We allocate a large block of memory from the start and we gradually add characters to this block. 
	 * Additions are made via "append" methods
	 */
	public String toString() {
        StringBuilder builder = new StringBuilder();
        for( int i=0; i<width*height; i++ ) {
        	if(i%width == 0)
        		builder.append( "\n" );
        	if(grid.get(i) != null) {
        		builder.append( grid.get(i));
        	}else {
        		builder.append(" "); // if the Piece is null, we add a space. It’s better to view the grid.
        	}
        }
        return builder.toString();
	}
	
	/**
	 * 
	 * @param index indicates the position of the piece in our list of pieces
	 * @return True if the is at the corner of the grid, false if not
	 */
	public boolean isCorner(int index)
	{
		return (index == 0 ||
				index == width-1 ||
				index == (height-1)*width ||
				index == ((height-1)*width)+width-1
				);
	}
	
	/**
	 * @param index indicates the position of the piece in our list of pieces
	 * @return True if the index is at the north east corner of the grid, false if not
	 */
	public boolean isNorthWestCorner(int index)
	{
		return (index == 0);
	}
	
	/**
	 * @param index indicates the position of the piece in our list of pieces
	 * @return True if the index is at the north west corner of the grid, false if not
	 */
	public boolean isNorthEastCorner(int index)
	{
		return (index == width-1);
	}
	
	
	/**
	 * @param index indicates the position of the piece in our list of pieces
	 * @return True if the index is at the south east corner of the grid, false if not
	 */
	public boolean isSouthWestCorner(int index)
	{
		return (index == (height-1)*width);
	}
	
	/**
	 * @param index indicates the position of the piece in our list of pieces
	 * @return True if the index is at the south west corner of the grid, false if not
	 */
	public boolean isSouthEastCorner(int index)
	{
		return (index == ((height-1)*width)+width-1);
	}
	
	
	/**
	 * 
	 * @param index indicates the position of the piece in our list of pieces
	 * @return True if the index is at the border of the grid, false if not
	 */
	public boolean isBorder(int index)
	{
		return ( (0 <index && index < width) ||
				((height-1)*width<index && index<width-1+((height-1)*width)) ||
				(!isCorner(index) && ((index%width== 0)||(index%width == width-1) ))
				);
	}
	
	/**
	 * @param index indicates the position of the piece
	 * @return True if index piece belongs to the upper border line, false if not 
	 */
	public boolean isNorthBorderLine(int index)
	{
		return (0 <index && index < width-1) ;
	}
	
	/**
	 * @param index indicates the position of the piece
	 * @return True if index piece belongs to the lowwer border line, false if not 
	 */
	public boolean isSouthBorderLine(int index)
	{
		return ((height-1)*width<index && index<width-1+((height-1)*width));
	}
	
	/**
	 * @param index indicates the position of the piece
	 * @return True if index piece belongs to the left border line, false if not 
	 */
	public boolean isWestBorderLine(int index)
	{
		return !isCorner(index) && (index%width== 0);
	}
	
	/**
	 * @param index indicates the position of the piece
	 * @return True if index piece belongs to the right border line, false if not 
	 */
	public boolean isEastBorderLine(int index)
	{
		return !isCorner(index) && (index%width == width-1);
	}
	
	/**
	 * Equals is redefined to meet our needs
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grid other = (Grid) obj;
		if (grid == null) {
			if (other.grid != null)
				return false;
		} else if (!grid.equals(other.grid))
			return false;
		if (height != other.height)
			return false;
		if (nbC != other.nbC)
			return false;
		if (width != other.width)
			return false;
		return true;
	}
	
	/**
	 * Add an observer to the list of observers
	 * Given our implementation of the graphical interface, there will be only one observer
	 * which is the Main class of our interface
	 */
	@Override
	public void addObserver(Observer obs) {
		this.observers.add(obs);
	}

	/**
	 * Method which allows to delete the observers present in the list of observers, this method will never be called
	 */
	@Override
	public void removeObserver() {
		this.observers = new ArrayList<Observer>();
		
	}
	
	/**
	 * This method allows to notify observers, 
	 * it is useful to modify the display of our graphical interface. 
	 * This method is called when a Piece changes orientation, either by a user click or when the solver resolves the grid.
	 */
	@Override
	public void notifyObserver() {
		for(Observer observer : this.observers)
			observer.update();
	}
	
	/**
	 * the following nine private methods will calculate the number of connections a piece has depending on 
	 * it's position on the grid, they will be called by the calculatePieceConnections method.
	 * @param p the piece we want to calculate it's connections
	 */
	private void calculateMiddlePieceConnections(Piece p)
	{
		if(p != null)
		{
			int i=0;
			if (p.hasNorthConnection()) 
			{
				if (this.grid.get(p.getPosition()-width) != null && this.grid.get(p.getPosition()-width).hasSouthConnection()) 
				{
					i++;
				}
			}
		
			if (p.hasSouthConnection()) 
			{
				if (this.grid.get(p.getPosition()+width) != null && this.grid.get(p.getPosition()+width).hasNorthConnection()) 
				{
					i++;
				}
			}
		
			if (p.hasEastConnection()) 
			{
				if (this.grid.get(p.getPosition()+1) != null && this.grid.get(p.getPosition()+1).hasWestConnection()) 
				{
					i++;
				}
			}
		
			if (p.hasWestConnection()) 
			{
				if (this.grid.get(p.getPosition()-1) != null && this.grid.get(p.getPosition()-1).hasEastConnection()) 
				{
					i++;
				}
			}
			
			p.setNbConnections(i);
			
		}
	}
	
	private void calculateNorthWestPieceConnections(Piece p)
	{
		if(p != null)
		{
			int i=0;
			
			if (p.hasSouthConnection()) 
			{
				if (this.grid.get(p.getPosition()+width) != null && this.grid.get(p.getPosition()+width).hasNorthConnection()) 
				{
					i++;
				}
			}
		
			if (p.hasEastConnection()) 
			{
				if (this.grid.get(p.getPosition()+1) != null && this.grid.get(p.getPosition()+1).hasWestConnection()) 
				{
					i++;
				}
			}
			
			p.setNbConnections(i);
			
		}
	}
	
	private void calculateNorthLinePieceConnections(Piece p)
	{
		if(p != null)
		{
			int i=0;
			
			if (p.hasSouthConnection()) 
			{
				if (this.grid.get(p.getPosition()+width) != null && this.grid.get(p.getPosition()+width).hasNorthConnection()) 
				{
					i++;
				}
			}
		
			if (p.hasEastConnection()) 
			{
				if (this.grid.get(p.getPosition()+1) != null && this.grid.get(p.getPosition()+1).hasWestConnection()) 
				{
					i++;
				}
			}
		
			if (p.hasWestConnection()) 
			{
				if (this.grid.get(p.getPosition()-1) != null && this.grid.get(p.getPosition()-1).hasEastConnection()) 
				{
					i++;
				}
			}
			
			p.setNbConnections(i);
			
		}
	}
	
	private void calculateNorthEastPieceConnections(Piece p)
	{
		if(p != null)
		{
			int i=0;
			if (p.hasSouthConnection()) 
			{
				if (this.grid.get(p.getPosition()+width) != null && this.grid.get(p.getPosition()+width).hasNorthConnection()) 
				{
					i++;
				}
			}
		
			if (p.hasWestConnection()) 
			{
				if (this.grid.get(p.getPosition()-1) != null && this.grid.get(p.getPosition()-1).hasEastConnection()) 
				{
					i++;
				}
			}
			
			p.setNbConnections(i);
			
		}
	}
	
	private void calculateEastPieceConnections(Piece p)
	{
		if(p != null)
		{
			int i=0;
			if (p.hasNorthConnection()) 
			{
				if (this.grid.get(p.getPosition()-width) != null && this.grid.get(p.getPosition()-width).hasSouthConnection()) 
				{
					i++;
				}
			}
		
			if (p.hasSouthConnection()) 
			{
				if (this.grid.get(p.getPosition()+width) != null && this.grid.get(p.getPosition()+width).hasNorthConnection()) 
				{
					i++;
				}
			}
		
			if (p.hasWestConnection()) 
			{
				if (this.grid.get(p.getPosition()-1) != null && this.grid.get(p.getPosition()-1).hasEastConnection()) 
				{
					i++;
				}
			}
			
			p.setNbConnections(i);
			
		}
	}
	
	private void calculateSouthPieceConnections(Piece p)
	{
		if(p != null)
		{
			int i=0;
			if (p.hasNorthConnection()) 
			{
				if (this.grid.get(p.getPosition()-width) != null && this.grid.get(p.getPosition()-width).hasSouthConnection()) 
				{
					i++;
				}
			}

			if (p.hasEastConnection()) 
			{
				if (this.grid.get(p.getPosition()+1) != null && this.grid.get(p.getPosition()+1).hasWestConnection()) 
				{
					i++;
				}
			}
		
			if (p.hasWestConnection()) 
			{
				if (this.grid.get(p.getPosition()-1) != null && this.grid.get(p.getPosition()-1).hasEastConnection()) 
				{
					i++;
				}
			}
			
			p.setNbConnections(i);
			
		}
	}
	
	private void calculateWestPieceConnections(Piece p)
	{
		if(p != null)
		{
			int i=0;
			if (p.hasNorthConnection()) 
			{
				if (this.grid.get(p.getPosition()-width) != null && this.grid.get(p.getPosition()-width).hasSouthConnection()) 
				{
					i++;
				}
			}
		
			if (p.hasSouthConnection()) 
			{
				if (this.grid.get(p.getPosition()+width) != null && this.grid.get(p.getPosition()+width).hasNorthConnection()) 
				{
					i++;
				}
			}
		
			if (p.hasEastConnection()) 
			{
				if (this.grid.get(p.getPosition()+1) != null && this.grid.get(p.getPosition()+1).hasWestConnection()) 
				{
					i++;
				}
			}
			
			p.setNbConnections(i);
			
		}
	}
	
	private void calculateSouthWestPieceConnections(Piece p)
	{
		if(p != null)
		{
			int i=0;
			if (p.hasNorthConnection()) 
			{
				if (this.grid.get(p.getPosition()-width) != null && this.grid.get(p.getPosition()-width).hasSouthConnection()) 
				{
					i++;
				}
			}
		
			if (p.hasEastConnection()) 
			{
				if (this.grid.get(p.getPosition()+1) != null && this.grid.get(p.getPosition()+1).hasWestConnection()) 
				{
					i++;
				}
			}
			
			p.setNbConnections(i);
			
		}
	}
	
	private void calculateSouthEastPieceConnections(Piece p)
	{
		if(p != null)
		{
			int i=0;
			if (p.hasNorthConnection()) 
			{
				if (this.grid.get(p.getPosition()-width) != null && this.grid.get(p.getPosition()-width).hasSouthConnection()) 
				{
					i++;
				}
			}

			if (p.hasWestConnection()) 
			{
				if (this.grid.get(p.getPosition()-1) != null && this.grid.get(p.getPosition()-1).hasEastConnection()) 
				{
					i++;
				}
			}
			
			p.setNbConnections(i);
			
		}
	}
	
	/**
	 * calculates the number of connections a piece has, will be called by the generator to initialize
	 * the connections the generated grid has
	 * will also be called by the reCalculatePieceConnections method.
	 * @param p the piece that will be calculated
	 */
	public void calculateConnections(Piece p)
	{
		if(p != null) 
		{
			if (this.isNorthWestCorner(p.getPosition())) 
				this.calculateNorthWestPieceConnections(p);
			else
				if(this.isNorthBorderLine(p.getPosition()))
					this.calculateNorthLinePieceConnections(p);
				else
					if(this.isNorthEastCorner(p.getPosition()))
						this.calculateNorthEastPieceConnections(p);
					else
						if(this.isEastBorderLine(p.getPosition()))
							this.calculateEastPieceConnections(p);
						else
							if(this.isSouthBorderLine(p.getPosition()))
								this.calculateSouthPieceConnections(p);
							else
								if(this.isWestBorderLine(p.getPosition()))
									this.calculateWestPieceConnections(p);
								else
									if(this.isSouthWestCorner(p.getPosition()))
										this.calculateSouthWestPieceConnections(p);
									else
										if(this.isSouthEastCorner(p.getPosition()))
											this.calculateSouthEastPieceConnections(p);
										else
											this.calculateMiddlePieceConnections(p);
		}						
	}
	
	
	/**
	 * clone method
	 *@return a clone of the grid. 
	 */
	public Grid clone() {
		Grid grid = new Grid(this.getWidth(), this.getheight());
		
		for(int i =0 ; i<this.getheight()*this.getWidth(); i++) {
			if(this.getPiece(i) != null){
				grid.putPiece(i, this.getPiece(i).Clone());
			}else {
				grid.putPiece(i, null);
			}
		}
		
		return grid;
	}
	
	/**
	 * Calculates the number of connections a piece and all it's neighbours have depending of the piece's
	 * position
	 * will use the calculateConnections method for each one of them
	 * will be called by the controller method whenever a piece's orientation changes
	 * @param p the piece that changed orientation
	 */
	public void reCalculateConnections(Piece p)
	{
		if (this.isNorthWestCorner(p.getPosition()))
		{
			this.calculateNorthWestPieceConnections(p);
			this.calculateConnections(this.grid.get(p.getPosition()+1));//East
			this.calculateConnections(this.grid.get(p.getPosition()+width));//South
		}
		else
		{
			if(this.isNorthBorderLine(p.getPosition()))
			{
				this.calculateNorthLinePieceConnections(p);
				this.calculateConnections(this.grid.get(p.getPosition()-1));//West
				this.calculateConnections(this.grid.get(p.getPosition()+1));//East
				this.calculateConnections(this.grid.get(p.getPosition()+width));//South
			}
			else
			{
				if(this.isNorthEastCorner(p.getPosition()))
				{
					this.calculateNorthEastPieceConnections(p);
					this.calculateConnections(this.grid.get(p.getPosition()-1));//West
					this.calculateConnections(this.grid.get(p.getPosition()+width));//South
				}
				else
				{
					if(this.isEastBorderLine(p.getPosition()))
					{
						this.calculateEastPieceConnections(p);
						this.calculateConnections(this.grid.get(p.getPosition()-1));//West
						this.calculateConnections(this.grid.get(p.getPosition()-width));//North
						this.calculateConnections(this.grid.get(p.getPosition()+width));//South
					}
					else
					{
						if(this.isSouthBorderLine(p.getPosition()))
						{
							this.calculateSouthPieceConnections(p);
							this.calculateConnections(this.grid.get(p.getPosition()-1));//West
							this.calculateConnections(this.grid.get(p.getPosition()+1));//East
							this.calculateConnections(this.grid.get(p.getPosition()-width));//North
						}
						else
						{
							if(this.isWestBorderLine(p.getPosition()))
							{
								this.calculateWestPieceConnections(p);
								this.calculateConnections(this.grid.get(p.getPosition()+1));//East
								this.calculateConnections(this.grid.get(p.getPosition()-width));//North
								this.calculateConnections(this.grid.get(p.getPosition()+width));//South
							}
							else
							{
								if(this.isSouthWestCorner(p.getPosition()))
								{
									this.calculateSouthWestPieceConnections(p);
									this.calculateConnections(this.grid.get(p.getPosition()+1));//East
									this.calculateConnections(this.grid.get(p.getPosition()-width));//North
								}
								else
								{
									if(this.isSouthEastCorner(p.getPosition()))
									{
										this.calculateSouthEastPieceConnections(p);
										this.calculateConnections(this.grid.get(p.getPosition()-1));//West
										this.calculateConnections(this.grid.get(p.getPosition()-width));//North
									}
									else
									{
										this.calculateMiddlePieceConnections(p);
										this.calculateConnections(this.grid.get(p.getPosition()-1));//West
										this.calculateConnections(this.grid.get(p.getPosition()+1));//East
										this.calculateConnections(this.grid.get(p.getPosition()-width));//North
										this.calculateConnections(this.grid.get(p.getPosition()+width));//South
									}
								
								}
							}
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * calculates the number of possible connections a piece has, will be called by the solver to ensure
	 * a grid is solvable 
	 * @param p the piece that will be calculated
	 */
	public int calculatePossibleConnections(Piece p)
	{ 
			if (this.isNorthWestCorner(p.getPosition())) 
				return this.calculateNorthWestPiecePossibleConnections(p);
			else
				if(this.isNorthBorderLine(p.getPosition()))
					return this.calculateNorthPiecePossibleConnections(p);
				else
					if(this.isNorthEastCorner(p.getPosition()))
						return this.calculateNorthEastPiecePossibleConnections(p);
					else
						if(this.isEastBorderLine(p.getPosition()))
							return this.calculateEastPiecePossibleConnections(p);
						else
							if(this.isSouthBorderLine(p.getPosition()))
								return this.calculateSouthPiecePossibleConnections(p);
							else
								if(this.isWestBorderLine(p.getPosition()))
									return this.calculateWestPiecePossibleConnections(p);
								else
									if(this.isSouthWestCorner(p.getPosition()))
										return this.calculateSouthWestPiecePossibleConnections(p);
									else
										if(this.isSouthEastCorner(p.getPosition()))
											return this.calculateSouthEastPiecePossibleConnections(p);
										else
											return this.calculateMiddlePiecePossibleConnections(p);
							
	}
	
	/**
	 * following nine methods are used by calculatePossibleConnections() to calculate a piece possible
	 * connections depending on it's position on the grid
	 * @param p the piece we calculate it's possible connections on
	 * @return an int representing the number of possible connections a piece has
	 */
	private int calculateNorthEastPiecePossibleConnections(Piece p)
	{
		int i=0;
	
		if (this.grid.get(p.getPosition()+width) != null) 
			i++;

		if (this.grid.get(p.getPosition()-1) != null ) 
			i++;
			
		return i;	
	}
	
	private int calculateNorthWestPiecePossibleConnections(Piece p)
	{
		int i=0;
	
		if (this.grid.get(p.getPosition()+width) != null) 
			i++;

		if (this.grid.get(p.getPosition()+1) != null ) 
			i++;
			
		return i;	
	}
	
	private int calculateNorthPiecePossibleConnections(Piece p)
	{
		int i=0;

		if (this.grid.get(p.getPosition()+1) != null ) 				
			i++;
		
		if (this.grid.get(p.getPosition()+width) != null ) 
			i++;
		
		
		if (this.grid.get(p.getPosition()-1) != null ) 			
			i++;
				
		return i;		
	}
	
	private int calculateEastPiecePossibleConnections(Piece p)
	{
		int i=0;

		if (this.grid.get(p.getPosition()-width) != null ) 				
			i++;
		
		if (this.grid.get(p.getPosition()+width) != null ) 
			i++;
		
		
		if (this.grid.get(p.getPosition()-1) != null ) 			
			i++;
				
		return i;		
	}
	
	private int calculateSouthPiecePossibleConnections(Piece p)
	{
		int i=0;

		if (this.grid.get(p.getPosition()-width) != null ) 
			i++;

		if (this.grid.get(p.getPosition()+1) != null ) 		
			i++;
		
		if (this.grid.get(p.getPosition()-1) != null )		
			i++;
			
		return i;
	}
	
	private int calculateWestPiecePossibleConnections(Piece p)
	{
		int i=0;
			
		if (this.grid.get(p.getPosition()-width) != null ) 
			i++;

		if (this.grid.get(p.getPosition()+width) != null ) 
			i++;

		if (this.grid.get(p.getPosition()+1) != null ) 
			i++;
	
		return i;
	}
	
	private int calculateSouthWestPiecePossibleConnections(Piece p)
	{
		int i=0;
			
		if (this.grid.get(p.getPosition()-width) != null) 
			i++;

		if (this.grid.get(p.getPosition()+1) != null )
			i++;
			
			
		return i;
	}
	
	private int calculateSouthEastPiecePossibleConnections(Piece p)
	{
		int i=0;

		if (this.grid.get(p.getPosition()-width) != null ) 
			i++;

		if (this.grid.get(p.getPosition()-1) != null ) 
			i++;
			
		return i;
	}
	
	private int calculateMiddlePiecePossibleConnections(Piece p)
	{
		int i=0;
			
		if (this.grid.get(p.getPosition()-width) != null ) 				
			i++;
		
		if (this.grid.get(p.getPosition()+width) != null ) 			
			i++;
		
		if (this.grid.get(p.getPosition()+1) != null ) 			
			i++;
		
		if (this.grid.get(p.getPosition()-1) != null ) 
			i++;
			
		return i;
	}
}
