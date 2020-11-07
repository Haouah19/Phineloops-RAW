package fr.dauphine.javaavance.phineloops;

import org.junit.Assert;

import org.junit.Test;

import fr.dauphine.javaavance.phineloops.model.Converter;
import fr.dauphine.javaavance.phineloops.model.Grid;
import fr.dauphine.javaavance.phineloops.model.utils.L;
import fr.dauphine.javaavance.phineloops.model.utils.TwoConnections;

public class ConstructTest {
	@Test
    public void constructGrille() {
		Grid grid = Converter.constrcutGrid("instances/public/grid_8x8_dist.0_vflip.false_hflip.false_messedup.false_id.3.dat");
		System.out.println(grid);
		// vérification de la taille 
		Assert.assertEquals(8,grid.getWidth());
		Assert.assertEquals(8,grid.getheight());
		Assert.assertEquals(64, grid.getheight()*grid.getWidth());
		
		// Verification des élements
		// Ligne 3 : 5 1
		Assert.assertTrue(grid.getPiece(0) instanceof L );
		Assert.assertEquals(grid.getPiece(0).getOrientation(), 1);
		
		// Ligne 53 : 5 3
		Assert.assertTrue(grid.getPiece(50) instanceof L ); 
		Assert.assertEquals(grid.getPiece(50).getOrientation(), 3);
		

		Assert.assertTrue(grid.getPiece(31) instanceof TwoConnections ); 
		Assert.assertEquals(grid.getPiece(31).getOrientation(), 1);
		Assert.assertFalse(grid.getPiece(26) instanceof L ); 
		Assert.assertEquals(grid.getPiece(26).getOrientation(), 0);
		
    }
	
	@Test
    public void constructFile() {
		/**
		 * To check the consistency of the output file. We build a grid "gridConverted" with a file in the instances directory,
		 * then we convert this grid to a file, and we create a new grid with this file,
		 * Then we check if the grids are similar.
		 */
		Grid gridConverted = Converter.constrcutGrid("instances/public/grid_8x8_dist.0_vflip.false_hflip.false_messedup.false_id.3.dat");
		Converter.constrcutFile(gridConverted, "Grid_8*8.txt");
		
		Grid gridFromFile = Converter.constrcutGrid("Grid_8*8.txt");
		System.out.println(gridConverted);
		System.out.println(gridFromFile);
		Assert.assertEquals(gridConverted, gridFromFile);
		
    }

}
