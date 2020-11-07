package fr.dauphine.javaavance.phineloops;


import org.junit.Assert;
import org.junit.Test;

import fr.dauphine.javaavance.phineloops.controller.Controller;
import fr.dauphine.javaavance.phineloops.model.Grid;
import fr.dauphine.javaavance.phineloops.model.utils.L;
import fr.dauphine.javaavance.phineloops.model.utils.OneConnections;
import fr.dauphine.javaavance.phineloops.model.utils.Piece;


public class ObserverTest {
   
	@Test(expected = NullPointerException.class)
    public void OrientationTest() {
		Grid g=new Grid(3,3);
		Controller controller = new Controller(g);
		g.putPiece(0, null);
		g.putPiece(1, null);
		g.putPiece(2, null);
		
		g.putPiece(3, new OneConnections(3,2));
		g.putPiece(4, new OneConnections(4,1));
		g.putPiece(5, new OneConnections(5,1)); 
		
		
		g.putPiece(6, new L(6,3));
		g.putPiece(7, new OneConnections(7,3));
		g.putPiece(8, null); 
		
		controller.putModification(0);
		controller.putModification(4);
		controller.putModification(7);
		
		Assert.assertEquals(g.getPiece(4).getOrientation(), 2);
		Assert.assertEquals(g.getPiece(7).getOrientation(), 0);
		Assert.assertEquals(g.getPiece(0).getOrientation(), 1);
    }
	
	
	@Test(expected = NullPointerException.class)
    public void NbConnectionTest() {
		Grid g=new Grid(3,3);
		Controller controller = new Controller(g);
		g.putPiece(0, null);
		g.putPiece(1, null);
		g.putPiece(2, null);
		
		g.putPiece(3, new OneConnections(3,2));
		g.putPiece(4, new OneConnections(4,1));
		g.putPiece(5, new OneConnections(5,1)); 
		
		
		g.putPiece(6, new L(6,3));
		g.putPiece(7, new OneConnections(7,3));
		g.putPiece(8, null); 
		
		for (Piece p : g.getGrid()) {
			g.calculateConnections(p);
		}
		
		Assert.assertEquals(g.getPiece(4).getNbConnections(), 0);
		Assert.assertEquals(g.getPiece(7).getNbConnections(), 0);
		Assert.assertEquals(g.getPiece(6).getNbConnections(), 1);
		Assert.assertEquals(g.getPiece(0).getNbConnections(), 1);

		controller.putModification(0); // affecte l'orientation et re-calcul les NbConnections
		controller.putModification(4);
		controller.putModification(7);
		
		// On test à la fois les orientation et les connections
		Assert.assertEquals(g.getPiece(4).getOrientation(), 2);
		Assert.assertEquals(g.getPiece(7).getOrientation(), 0);
		Assert.assertEquals(g.getPiece(0).getOrientation(), 1);
		
		
		Assert.assertEquals(g.getPiece(4).getNbConnections(), 1);
		Assert.assertEquals(g.getPiece(7).getNbConnections(), 1);
		Assert.assertEquals(g.getPiece(6).getNbConnections(), 1);
		Assert.assertEquals(g.getPiece(0).getNbConnections(), 1); // Pour le nullPointerException
		

    }
	
	@Test
    public void isSolvedTest() {
		Grid g=new Grid(3,3);
		Controller controller = new Controller(g);
		g.putPiece(0, null);
		g.putPiece(1, null);
		g.putPiece(2, null);
		
		g.putPiece(3, new OneConnections(3,2));
		g.putPiece(4, new OneConnections(4,1));
		g.putPiece(5, new OneConnections(5,1)); 
		
		
		g.putPiece(6, new L(6,3));
		g.putPiece(7, new OneConnections(7,3));
		g.putPiece(8, null); 
		
		for (Piece p : g.getGrid()) {
			g.calculateConnections(p);
		}
		
		Assert.assertFalse(controller.isGridSolved());
		Assert.assertEquals(controller.isGridSolved(), g.isSolved());
		
		// Résolution de la grille
		controller.putModification(5); // 1 2 sud
		controller.putModification(5); // 1 3 ouest
		
		controller.putModification(6); // ouest-nord => nord-est
	
		
		Assert.assertTrue(controller.isGridSolved());
		Assert.assertEquals(controller.isGridSolved(), g.isSolved());

    }

}
