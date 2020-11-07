package fr.dauphine.javaavance.phineloops.model;

import java.util.ArrayList;
import java.util.Random;

import fr.dauphine.javaavance.phineloops.model.utils.FourConnections;
import fr.dauphine.javaavance.phineloops.model.utils.L;
import fr.dauphine.javaavance.phineloops.model.utils.OneConnections;
import fr.dauphine.javaavance.phineloops.model.utils.Piece;
import fr.dauphine.javaavance.phineloops.model.utils.ThreeConnections;
import fr.dauphine.javaavance.phineloops.model.utils.TwoConnections;

public class Generator {
	
	//arrayLists of all the possible pieces with their different possible orientations following the
	//case type
	//The values are the same for all generators, so we declare it static
	private static ArrayList<Piece> allPieces;
	private static ArrayList<Piece> NorthWestCornerDomain;
	private static ArrayList<Piece> NorthEastCornerDomain;
	private static ArrayList<Piece> NorthBorderLineDomain;
	private static ArrayList<Piece> SouthWestCornerDomain;
	private static ArrayList<Piece> WestBorderLineDomain;
	private static ArrayList<Piece> SouthEastCornerDomain;
	private static ArrayList<Piece> EastBorderLineDomain;
	private static ArrayList<Piece> SouthBorderLineDomain;
	
public Generator() 
{
	allPieces=generateAllPieces();
	NorthBorderLineDomain=generateNorthBorderLineDomain();
	NorthWestCornerDomain=generateNorthWestCornerDomain();
	NorthEastCornerDomain=generateNorthEastCornerDomain();
	WestBorderLineDomain=generateWestBorderLineDomain();
	SouthWestCornerDomain=generateSouthWestCornerDomain();
	SouthBorderLineDomain=generateSouthBorderLineDomain();
	EastBorderLineDomain=generateEastBorderLineDomain();
	SouthEastCornerDomain=generateSouthEastCornerDomain();
}
/**
 * generates a solved grid without the number connexe components constraint
 * @param width represents the width of the output grid 
 * @param height represents the height of the output grid
 * @return a solved grid of size width*height
 */
public Grid generateSolvedGrid(int width, int height)
{
	Grid g=new Grid(width,height);
	//Browses all the Grid
	for (int i = 0; i < width*height; i++) 
	{
		if(g.isNorthWestCorner(i))
			putFirstPiece(g,i);
		else
			if (g.isNorthBorderLine(i)) 
				putFirstLinePiece(g,i);
			else
				if(g.isNorthEastCorner(i))
					putNorthEastPiece(g,i);
				else
					if(g.isWestBorderLine(i))
						putWestLinePiece(g,i);
					else
						if(g.isSouthWestCorner(i))
							putSouthWestPiece(g, i);
						else
							if(g.isEastBorderLine(i))
								putEastLinePiece(g,i);
							else
								if (g.isSouthBorderLine(i)) 
									putSouthLinePiece(g,i);
								else
									if (g.isSouthEastCorner(i)) 
										putSouthEastPiece(g, i);
									else 
										putMiddlePiece(g, i);
	}
	
	return g;
	
}

/**
 * takes the generated solved grid, suffles it and then returns it.
 * @param g a solved grid
 * @return the g grid shuffled
 */
private Grid shuffle(Grid g)
{
	for (Piece piece : g.getGrid()) 
	{
		if (piece != null) 
			if (piece.getType() != 4) 
				if (piece.getType() == 2) 
					piece.putOrientation(generateRandomNumber(2));
				else
					piece.putOrientation(generateRandomNumber(4));
	}
	return g;
}

/**
 * Generates the shuffled a grid, this is the method that will be called by the main
 * @param a width x height shuffled Grid
 */
public Grid generateGrid(int width,int height)
{
	Grid g=this.shuffle(this.generateSolvedGrid(width,height));

	int i=0;
	for (Piece piece : g.getGrid()) {
		if(piece != null)
		{
		piece.setPosition(i);
		g.calculateConnections(piece);
		}
	i++;
	}
	return g;
}

/**
 * The following nine generate methods will be used to initialize the attributes of the class.
 * @return an array with all the possible pieces and their possible rotations for a domain
 */
private static ArrayList<Piece> generateAllPieces()
{
	ArrayList<Piece> all=new ArrayList<Piece>();
	for (int i = 0; i < 4; i++) 
	{
		Piece p=new OneConnections(-1, i);
		all.add(p);
	}
	
	for (int i = 4; i < 6; i++) 
	{
		Piece p=new TwoConnections(-1, i%4);
		all.add(p);
	}
	
	for (int i = 6; i < 10; i++) 
	{
		Piece p=new ThreeConnections(-1, i%6);
		all.add(p);
	}
	
	Piece p = new FourConnections(-1, 0);
	all.add(p);
	
	for (int i = 11; i < 15; i++) 
	{
		Piece pp=new L(-1, i%11);
		all.add(pp);
	}
	
	return all;
}


private static ArrayList<Piece> generateNorthBorderLineDomain()
{
	ArrayList<Piece> p =new ArrayList<Piece>();
	for (Piece piece : allPieces)
	{
		if(!piece.hasNorthConnection() )
		{
			p.add(piece);
		}
	}
	
	return p;
}


private static ArrayList<Piece> generateNorthWestCornerDomain()
{
	ArrayList<Piece> p =new ArrayList<Piece>();
	for (Piece piece : NorthBorderLineDomain)
	{
		if(!piece.hasWestConnection())
		{
			p.add(piece);
		}
	}
	
	return p;
}

private static ArrayList<Piece> generateNorthEastCornerDomain()
{
	ArrayList<Piece> p =new ArrayList<Piece>();
	for (Piece piece : NorthBorderLineDomain)
	{
		if(!piece.hasEastConnection())
		{
			p.add(piece);
		}
	}
	
	return p;
}


private static ArrayList<Piece> generateWestBorderLineDomain()
{
	ArrayList<Piece> p =new ArrayList<Piece>();
	for (Piece piece : allPieces)
	{
		if(!piece.hasWestConnection() )
		{
			p.add(piece);
		}
	}
	
	return p;
}

private static ArrayList<Piece> generateSouthWestCornerDomain()
{
	ArrayList<Piece> p =new ArrayList<Piece>();
	for (Piece piece : WestBorderLineDomain)
	{
		if(!piece.hasSouthConnection())
		{
			p.add(piece);
		}
	}
	
	return p;
}

private static ArrayList<Piece> generateSouthBorderLineDomain()
{
	ArrayList<Piece> p =new ArrayList<Piece>();
	for (Piece piece : allPieces)
	{
		if(!piece.hasSouthConnection() )
		{
			p.add(piece);
		}
	}
	
	return p;
}

private static ArrayList<Piece> generateEastBorderLineDomain()
{
	ArrayList<Piece> p =new ArrayList<Piece>();
	for (Piece piece : allPieces)
	{
		if(!piece.hasEastConnection() )
		{
			p.add(piece);
		}
	}
	
	return p;
}

private static ArrayList<Piece> generateSouthEastCornerDomain()
{
	ArrayList<Piece> p =new ArrayList<Piece>();
	for (Piece piece : SouthBorderLineDomain)
	{
		if(!piece.hasEastConnection())
		{
			p.add(piece);
		}
	}
	
	return p;
}

/**
 * Puts (or not) a piece on the first case of the grid, can be called on a first line border case if 
 * the previous one doesn't have an east connection
 * @param g the grid where we add a piece
 * @param index position where we add a piece
 */
private static void putFirstPiece(Grid g,int index)
{
	if (putAPiece()) 
	{
		g.putPiece(index, NorthWestCornerDomain.get(generateRandomNumber(NorthWestCornerDomain.size())).Clone());
		
	} 
	else 
	{
		g.putPiece(index, null);
	}	
}


/**
 * puts (or not) a piece in the a first line border case, if the previous case has an east connection 
 * we need to have a west connection but if not we can simply call the putFirstPiece method since the domain 
 * become the same
 * @param g the grid we add a piece to
 * @param index position of the piece to add
 */
private static void putFirstLinePiece(Grid g,int index)
{
	
	if(g.getPiece(index-1) != null && g.getPiece(index-1).hasEastConnection()  )
	{
		ArrayList<Piece> domain = new ArrayList<Piece>();
		for (Piece piece : NorthBorderLineDomain) 
		{
			if( piece.hasWestConnection())
			{
				domain.add(piece);
			}
		}
		
		g.putPiece(index, domain.get(generateRandomNumber(domain.size())).Clone());
		
	}
	else
	{
		putFirstPiece(g, index);
	}
}

/**
 * puts (or not) a piece on the north east corner case, following case and neighbours constraints 
 * @param g
 * @param index
 */
public static void putNorthEastPiece(Grid g, int index)
{
	ArrayList<Piece> domain = new ArrayList<Piece>();
	if(g.getPiece(index-1) != null && g.getPiece(index-1).hasEastConnection())
	{
		for (Piece piece : NorthEastCornerDomain) 
		{
			if(piece.hasWestConnection() )
			{
				domain.add(piece);
			}
		}
		g.putPiece(index, domain.get(generateRandomNumber(domain.size())).Clone());
		
	}
	else
	{
		if (putAPiece()) 
		{
			//Since the domain in this case is composed of one piece only, we can just put it without using 
			//the domain array
			g.putPiece(index,new OneConnections(index,2));
		} 
		else 
		{
			g.putPiece(index, null);
		}
	}
}

/**
 * puts (or not) a piece on the west border case, following case and neighbours constraints 
 * @param g
 * @param index
 */
private static void putWestLinePiece(Grid g,int index)
{
	
	if(g.getPiece(index-g.getWidth()) != null  && g.getPiece(index-g.getWidth()).hasSouthConnection() )
	{
		ArrayList<Piece> domain = new ArrayList<Piece>();
		for (Piece piece : WestBorderLineDomain) 
		{
			if(piece.hasNorthConnection())
			{
				domain.add(piece);
			}
		}
		
		g.putPiece(index, domain.get(generateRandomNumber(domain.size())).Clone());
		
	}
	else
	{
		putFirstPiece(g, index);
	}
}

/**
 * puts (or not) a piece on the South west corner
 * @param g
 * @param index
 */
private static void putSouthWestPiece(Grid g,int index)
{
	ArrayList<Piece> domain = new ArrayList<Piece>();
	if(g.getPiece(index-g.getWidth()) != null && g.getPiece(index-g.getWidth()).hasSouthConnection() )
	{
		for (Piece piece : SouthWestCornerDomain) 
		{
			if(piece.hasNorthConnection())
			{
				domain.add(piece);
			}
		}
		
		g.putPiece(index, domain.get(generateRandomNumber(domain.size())).Clone());
		
	}
	else
	{
		if (putAPiece()) 
		{
			for (Piece piece : SouthWestCornerDomain) 
			{
				if(!piece.hasNorthConnection())
				{
					domain.add(piece);
				}
				
			}
			g.putPiece(index, domain.get(generateRandomNumber(domain.size())).Clone());
			
		} 
		else 
		{
			g.putPiece(index, null);
		}
	}
}

/**
 * puts (or not) a piece on the east line cases
 * @param g
 * @param index
 */
private static void putEastLinePiece(Grid g,int index)
{
	ArrayList<Piece> domain =new ArrayList<Piece>();
	if (g.getPiece(index-g.getWidth()) != null && g.getPiece(index-g.getWidth()).hasSouthConnection()
		&& g.getPiece(index-1) != null && g.getPiece(index-1).hasEastConnection() ) 
	{
		for (Piece piece : EastBorderLineDomain) 
		{
			if (piece.hasNorthConnection() && piece.hasWestConnection()) 
			{
				domain.add(piece);
			}
		}
		g.putPiece(index, domain.get(generateRandomNumber(domain.size())).Clone());
	} 
	else 
	{
		if (g.getPiece(index-g.getWidth()) != null && g.getPiece(index-g.getWidth()).hasSouthConnection()) 
		{
			for (Piece piece : EastBorderLineDomain) 
			{
				if (piece.hasNorthConnection() && !piece.hasWestConnection())
				{
					domain.add(piece);
				}
			}
			g.putPiece(index, domain.get(generateRandomNumber(domain.size())).Clone());
		}
		else
		{
			if (g.getPiece(index-1) != null && g.getPiece(index-1).hasEastConnection()) 
			{
				for (Piece piece : EastBorderLineDomain) 
				{
					if (piece.hasWestConnection() && !piece.hasNorthConnection())
					{
						domain.add(piece);
					}
				}
				g.putPiece(index, domain.get(generateRandomNumber(domain.size())).Clone());
			}
			else
			{
				for (Piece piece : EastBorderLineDomain) 
				{
					if (!piece.hasNorthConnection() && !piece.hasWestConnection()) 
					{
						domain.add(piece);
					}
				}
				g.putPiece(index, domain.get(generateRandomNumber(domain.size())).Clone());
			}
		}
	}
}

/**
 * puts a piece on the south line cases
 * @param g
 * @param index
 */
private static void putSouthLinePiece(Grid g,int index)
{
	ArrayList<Piece> domain =new ArrayList<Piece>();
	if (g.getPiece(index-g.getWidth()) != null && g.getPiece(index-g.getWidth()).hasSouthConnection()
		&& g.getPiece(index-1) != null && g.getPiece(index-1).hasEastConnection() ) 
	{
		for (Piece piece : SouthBorderLineDomain) 
		{
			if (piece.hasNorthConnection() && piece.hasWestConnection())
			{
				domain.add(piece);
			}
		}
		g.putPiece(index, domain.get(generateRandomNumber(domain.size())).Clone());
	} 
	else 
	{
		if (g.getPiece(index-g.getWidth()) != null && g.getPiece(index-g.getWidth()).hasSouthConnection()) 
		{
			for (Piece piece : SouthBorderLineDomain) 
			{
				if (piece.hasNorthConnection() && !piece.hasWestConnection()) 
				{
					domain.add(piece);
				}
			}
			g.putPiece(index, domain.get(generateRandomNumber(domain.size())).Clone());
		}
		else
		{
			if (g.getPiece(index-1) != null && g.getPiece(index-1).hasEastConnection()) 
			{
				for (Piece piece : SouthBorderLineDomain) 
				{
					if (piece.hasWestConnection() && !piece.hasNorthConnection())
					{
						domain.add(piece);
					}
				}
				g.putPiece(index, domain.get(generateRandomNumber(domain.size())).Clone());
			}
			else
			{
				for (Piece piece : SouthBorderLineDomain) 
				{
					if (!piece.hasNorthConnection() && !piece.hasWestConnection())
					{
						domain.add(piece);
					}
				}
				g.putPiece(index, domain.get(generateRandomNumber(domain.size())).Clone());
			}
		}
	}
}

/**
 * puts (or not) a piece on the south east corner case
 * @param g
 * @param index
 */
public static void putSouthEastPiece(Grid g, int index)
{
	ArrayList<Piece> domain =new ArrayList<Piece>();
	if (g.getPiece(index-g.getWidth()) != null && g.getPiece(index-g.getWidth()).hasSouthConnection()
		&& g.getPiece(index-1) != null && g.getPiece(index-1).hasEastConnection() ) 
	{
		for (Piece piece : SouthEastCornerDomain) 
		{
			if (piece.hasNorthConnection() && piece.hasWestConnection()) {
				domain.add(piece);
			}
		}
		g.putPiece(index, domain.get(generateRandomNumber(domain.size())).Clone());
	} 
	else 
	{
		if (g.getPiece(index-g.getWidth()) != null && g.getPiece(index-g.getWidth()).hasSouthConnection()) 
		{
			for (Piece piece : SouthEastCornerDomain) 
			{
				if (piece.hasNorthConnection() && !piece.hasWestConnection()) {
					domain.add(piece);
				}
			}
			g.putPiece(index, domain.get(generateRandomNumber(domain.size())).Clone());
		}
		else
		{
			if (g.getPiece(index-1) != null && g.getPiece(index-1).hasEastConnection()) 
			{
				for (Piece piece : SouthEastCornerDomain) 
				{
					if (piece.hasWestConnection() && !piece.hasNorthConnection()) {
						domain.add(piece);
					}
				}
				g.putPiece(index, domain.get(generateRandomNumber(domain.size())).Clone());
			}
			else
			{
				g.putPiece(index, null);
			}
		}
	}
}
/**
 * puts (or not) a piece on the middle cases
 * @param g
 * @param index
 */
private static void putMiddlePiece(Grid g, int index)
{
	ArrayList<Piece> domain =new ArrayList<Piece>();
	if (g.getPiece(index-g.getWidth()) != null && g.getPiece(index-g.getWidth()).hasSouthConnection()
		&& g.getPiece(index-1) != null && g.getPiece(index-1).hasEastConnection() ) 
	{
		for (Piece piece : allPieces) 
		{
			if (piece.hasNorthConnection() && piece.hasWestConnection())
			{
				domain.add(piece);
			}
		}
		g.putPiece(index, domain.get(generateRandomNumber(domain.size())).Clone());
	} 
	else 
	{
		if (g.getPiece(index-g.getWidth()) != null && g.getPiece(index-g.getWidth()).hasSouthConnection()) 
		{
			for (Piece piece : allPieces) 
			{
				if (piece.hasNorthConnection() && !piece.hasWestConnection()) 
				{
					domain.add(piece);
				}
			}
			g.putPiece(index, domain.get(generateRandomNumber(domain.size())).Clone());
		}
		else
		{
			if (g.getPiece(index-1) != null && g.getPiece(index-1).hasEastConnection()) 
			{
				for (Piece piece : allPieces) 
				{
					if (piece.hasWestConnection() && !piece.hasNorthConnection())
					{
						domain.add(piece);
					}
				}
				g.putPiece(index, domain.get(generateRandomNumber(domain.size())).Clone());
			}
			else
			{
				for (Piece piece : allPieces) 
				{
					if (!piece.hasNorthConnection() && !piece.hasWestConnection())
					{
						domain.add(piece);
					}
				}
				g.putPiece(index, domain.get(generateRandomNumber(domain.size())).Clone());
			}
		}
	}
}
/**
 * generates random number, used to choose a piece to add on a grid basic of an arrayList of possible pieces
 * @param max  : upper bound for the random generator (random < max) (lowwer bound is 0)
 * @return a random int r : 0<r<max
 */
private static int generateRandomNumber(int max)
{
	Random r=new Random();
	return r.nextInt(max);
}

/**
 * called if it's possible not to put a piece on a case
 * @return a boolean that tells if we should put a piece or not,
 * 		   with a probability of 67% true 33% false
 */
private static boolean putAPiece()
{
	Random r=new Random();
	int p=r.nextInt(3);
	if (p == 1 || p == 2) 
		return true;
	else 
		return false;
}



}

