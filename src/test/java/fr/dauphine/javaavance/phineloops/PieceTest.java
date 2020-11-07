package fr.dauphine.javaavance.phineloops;

import static org.junit.Assert.assertFalse;
import org.junit.*;
import fr.dauphine.javaavance.phineloops.model.utils.FourConnections;
import fr.dauphine.javaavance.phineloops.model.utils.L;
import fr.dauphine.javaavance.phineloops.model.utils.OneConnections;
import fr.dauphine.javaavance.phineloops.model.utils.Piece;
import fr.dauphine.javaavance.phineloops.model.utils.ThreeConnections;
import fr.dauphine.javaavance.phineloops.model.utils.TwoConnections;

public class PieceTest  {

   @Test
    public void Piece1() {
    	Piece piece = new OneConnections(1, 1);
        Assert.assertEquals(1,piece.getOrientation());
        
        piece.putOrientation(2);
        Assert.assertEquals(2,piece.getOrientation());
        
        Assert.assertEquals(0,piece.getNbConnections());
        assertFalse(piece.isConnected());

        
    }
    
    @Test
    public void testOrientation() {
    	Piece piece = new OneConnections(1, 1);
        Assert.assertTrue(piece.hasEastConnection());
        Assert.assertFalse(piece.hasNorthConnection());
        
        
    }

    @Test
    public void Piece2() {
    	Piece piece = new TwoConnections(1, 1);
        Assert.assertEquals(1,piece.getOrientation());
        
        piece.putOrientation(2);
        Assert.assertEquals(-1,piece.getOrientation());
        
        Assert.assertEquals(0,piece.getNbConnections());
        assertFalse(piece.isConnected());
    }
    @Test
    public void Piece3() {
    	Piece piece = new ThreeConnections(1, 1);
        Assert.assertEquals(1,piece.getOrientation());
        
        piece.putOrientation(2);
        Assert.assertEquals(2,piece.getOrientation());
        
        Assert.assertEquals(0,piece.getNbConnections());
        assertFalse(piece.isConnected());
    }
    @Test
    public void PieceL() {
    	Piece piece = new L (1, 1);
        Assert.assertEquals(1,piece.getOrientation());
        
        piece.putOrientation(2);
        Assert.assertEquals(2,piece.getOrientation());
        
        
        
        Assert.assertEquals(0,piece.getNbConnections());
        assertFalse(piece.isConnected());
        }
    
    @Test
    public void testOutOfBoundOrientation() {
    	Piece piece = new OneConnections(1, 520);
    	System.out.println(piece.getType());
        
        
    }
	
	@Test
	public void testOverride() {
    	FourConnections f=new FourConnections(1, 0);
    	Piece f2=f.Clone();
    	
    	System.out.println(f2.getPosition()+" "+f2.getOrientation()+" "+f2.getNbConncetionsPossible());
        
        
    }

}
