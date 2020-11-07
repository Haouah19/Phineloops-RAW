package fr.dauphine.javaavance.phineloops;

import fr.dauphine.javaavance.phineloops.model.utils.*;


//import java.io.ObjectOutputStream.PutField;

import org.junit.*;
import fr.dauphine.javaavance.phineloops.model.*;

public class GridTest  {

	@Test(expected = IndexOutOfBoundsException.class)
    public void creation() {
    	Grid grid = new Grid(5, 5);
    	Piece piece1 = new L(0,0);
    	grid.putPiece(0, piece1);
        Assert.assertEquals(piece1,grid.getPiece(0));
        
		Piece piece = grid.getPiece(1);
        Assert.assertEquals(piece, piece1);
    }
	
	@Test
	public void testCorner()
	{
    	Grid grid = new Grid(5, 5, 0);
    	for(int i=0; i<8; i++)
    	{
    		Piece piece1 = new L(i,0);
        	grid.putPiece(i, piece1);
    	}
        Assert.assertTrue(grid.isCorner(0));
        Assert.assertFalse(grid.isCorner(1));
        Assert.assertFalse(grid.isCorner(7));
       
    }
	
	@Test
	public void testBorder()
	{
    	Grid grid = new Grid(5, 5, 0);
    	for(int i=0; i<8; i++)
    	{
    		Piece piece1 = new L(i,0);
        	grid.putPiece(i, piece1);
    	}
    	
    	Assert.assertFalse(grid.isBorder(0));
        Assert.assertTrue(grid.isBorder(1));
        Assert.assertFalse(grid.isBorder(7));
        
        
    }
	
	/*@Test
	public void testGenerateFirstPiece()
	{
    	Grid g = new Grid(5, 5);
    	Generator gn=new Generator();
        Generator.putFirstPiece(g,0);
        System.out.println(g.getPiece(0));
        System.out.println(g.getPiece(0).getType());
        System.out.println(g.getPiece(0).getOrientation());
        System.out.println("-----");
        for (Piece piece : Generator.NorthWestCornerDomain) {
			System.out.println(piece.getType()+""+piece.getOrientation());
		}
        
    }*/
	
	/*@Test//Test pour voir s'il mets la bonne pièce si la pièce d'avant n'est pas orientéée Est
	public void testGenerateFirstBorderPieceElse()
	{
    	Grid g = new Grid(5, 5);
    	Generator gn=new Generator();
        g.putPiece(0, new OneConnections(0, 2));
        Generator.putFirstLinePiece(g, 1);
        System.out.println(g.getPiece(1));
        System.out.println(g.getPiece(1).getType());
        System.out.println(g.getPiece(1).getOrientation());
        
    }
	
	@Test//Test pour voir s'il mets la bonne pièce si la pièce d'avant est orientéée Est
	public void testGenerateFirstBorderPieceIf()
	{
    	Grid g = new Grid(5, 5);
    	Generator gn=new Generator();
        g.putPiece(0, new OneConnections(0, 1));
        Generator.putFirstLinePiece(g, 1);
        System.out.println("------");
        System.out.println(g.getPiece(1));
        System.out.println(g.getPiece(1).getType());
        System.out.println(g.getPiece(1).getOrientation());
        
    }*/
	
	/*@Test
	public void testGenerateSolvedGrille()
	{
    	Grid g = new Grid(5,5);
    	Generator gn=new Generator();
        g=gn.generateSolvedGrid(g.getWidth(), g.getheight());
        System.out.println(g);
        int i =0;
        for (Piece p : g.getGrid()) {
        	if(p != null)
			System.out.println("piece "+i+" : "+p.getType()+" "+p.getOrientation());
        	else
        		System.out.println("Pas de pièce");
			i++;
		}
    }*/
	
	/*@Test
	public void testGenerateShuffledGrille()
	{
    	Grid g = new Grid(5,5);
    	Generator gn=new Generator();
        g=gn.generateSolvedGrid(g.getWidth(), g.getheight());
        System.out.println("Grille résolue :");
        System.out.println(g);
        int i =0;
        for (Piece p : g.getGrid()) {
        	if(p != null)
			System.out.println("piece "+i+" : "+p.getType()+" "+p.getOrientation());
        	else
        		System.out.println("Pas de pièce");
			i++;
		}
        i=0;
        gn.shuffle(g);
        System.out.println("Grille mélangée : ");
        System.out.println(g);
        for (Piece p : g.getGrid()) {
        	if(p != null)
			System.out.println("piece "+i+" : "+p.getType()+" "+p.getOrientation());
        	else
        		System.out.println("Pas de pièce");
			i++;
		}
        //gn.generateGridFile(g);
    }
	
	@Test
	public void testGenerateGridFile()
	{
		Generator gn=new Generator();
		Grid grid = gn.generateSolvedGrid(16,16);
		System.out.println(grid);
		System.out.println("==============================================");
		Grid grid2 = gn.shuffle(grid);
		System.out.println(grid2);
    }
	
	/*@Test
	public void testNorthEastPiece()
	{
		Generator gn=new Generator();
		Grid g=new Grid(5,5);
		g.putPiece(0, new OneConnections(0, 2));
		g.putPiece(1, new OneConnections(1,1));
		g.putPiece(1, new OneConnections(2,3));
		g.putPiece(1, new OneConnections(3,1));
		System.out.println(g.isNorthEastCorner(4));
		Generator.putNorthEastPiece(g,4);
		System.out.println("piece prise avec: "+g.getPiece(4).getType()+" "+g.getPiece(4).getOrientation());
		
		Grid g2=new Grid(5,5);
		g2.putPiece(0, new OneConnections(0, 1));
		g2.putPiece(1, new OneConnections(1,3));
		g2.putPiece(0, new OneConnections(2, 1));
		g2.putPiece(0, new OneConnections(3, 3));
		System.out.println(g2.isNorthEastCorner(4));
		Generator.putNorthEastPiece(g2,4);
		System.out.println("piece prise sans: "+g2.getPiece(4).getType()+" "+g2.getPiece(4).getOrientation());
		
		
	}*/
	/*
	@Test
	public void testOrientations()
	{
		System.out.println("TEST ORIENTATION");
		Generator gn = new Generator();
		Grid g=gn.generateGrid(5, 5);
		System.out.println(g);
		int i = 0;
		for (Piece p : g.getGrid()) {
			if(p != null)
				System.out.println("piece numero "+i+" position fausse : "+ p.getPosition()+" Le type  et l'oreintation :" +p.getType()+" "+p.getOrientation());
			else
				System.out.println("piece "+i+" : Pas de piece");
		i++;
		}
		
		int j=0;
		System.out.println("CONNEXIONS");
		for (Piece p : g.getGrid()) 
		{
			if(p != null)
				System.out.println("piece "+j+" : "+p.getNbConnections());
			else
				System.out.println("piece "+j+" : null");
			j++;
		}
		

	}
	*/
	/*@Test
	public void testPositions()
	{
		System.out.println("TEST Positions");
		Generator gn = new Generator();
		Grid g=gn.generateGrid(3, 3);
		System.out.println(g);
		for (Piece p : g.getGrid()) {
			System.out.println(p.getPosition());
		}
	}*/
	
	@Test
	public void testSolvedChecker()
	{
		Generator gn=new Generator();
		Grid g=gn.generateSolvedGrid(3,3);
		
		int i=0;
		for (Piece p : g.getGrid())
		{
			if(p != null)
				p.setPosition(i);
			i++;
		}
		
		for (Piece p : g.getGrid())
		{
			if(p != null)
				g.calculateConnections(p);
		}
		System.out.println(g);
		g.putOrientation(4, 1);
		
		System.out.println(g);
		Assert.assertFalse(g.isSolved());
	}
	
	/*
	@Test
	public void testMiniSolution()
	{
		Grid g=new Grid(2, 2);
		g.putPiece(0, new L(0,1));
		g.putPiece(1, new L(1,2));
		g.putPiece(2, new OneConnections(2,0));
		g.putPiece(3, new OneConnections(3,0));
		/*g.getGrid().get(0).setNbConnections(2);
		g.getGrid().get(1).setNbConnections(2);
		g.getGrid().get(2).setNbConnections(1);
		g.getGrid().get(3).setNbConnections(1);
		for (Piece p : g.getGrid()) 
		{
			g.calculateConnections(p);
		}
		
		Assert.assertTrue(g.isSolved());
	}
    */
        
 }

