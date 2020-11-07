package fr.dauphine.javaavance.phineloops;


import org.junit.Assert;

import fr.dauphine.javaavance.phineloops.model.Generator;
import fr.dauphine.javaavance.phineloops.model.Grid;
import fr.dauphine.javaavance.phineloops.model.solvers.Solver;
import fr.dauphine.javaavance.phineloops.model.solvers.SolverMaitre;
import fr.dauphine.javaavance.phineloops.model.utils.L;
import fr.dauphine.javaavance.phineloops.model.utils.OneConnections;
import fr.dauphine.javaavance.phineloops.model.utils.Piece;
import fr.dauphine.javaavance.phineloops.model.utils.TwoConnections;




public class SolverTest {
	public static void main( String [] args) {
    	
		
    	Generator gn = new Generator();
	 	/*Grid grid = gn.generateGrid(4,4);
		
	 	/*Grid grid = new Grid(3,3);
		grid.putPiece(0, new OneConnections(0, 1));
		grid.putPiece(1,new L(1,2));
		grid.putPiece(2, new OneConnections(2, 2));
		
		grid.putPiece(3, new OneConnections(3,2));
		grid.putPiece(4, new OneConnections(4,0));
		grid.putPiece(5, new TwoConnections(5,0));
		
		
		grid.putPiece(6,new OneConnections(6,0));
		grid.putPiece(7,new OneConnections(7, 1));
		grid.putPiece(8,new L(8,3));
		
		
		for(Piece piece : grid.getGrid()) {
			if(piece != null) {
				grid.calculateConnections(piece);
				System.out.println("Piece "+ piece.getPosition() + " Type : "+piece.getType() );
			}

		}
		
		grid.putOrientation(0, 3);
		grid.putOrientation(1, 3);
		grid.putOrientation(2, 3);
		grid.putOrientation(3, 1);
		grid.putOrientation(5, 1);
		grid.putOrientation(7, 2);
		*
	 	
    	Solver solver = new Solver(grid);
    	System.out.println(grid);
		System.out.println("Avant le solve : "+ grid.isSolved());
		
    	System.out.println(solver.getOrientations());
    	
    	solver.solve();
		System.out.println("Après le solve : "+grid.isSolved());
    	System.out.println(solver.getOrientations());
		System.out.println(grid);
    	
    	
    	SolverThread solverThread = new SolverThread(null,orientations, grid);
    	solverThread.start();
    	
    	try {
			solverThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Après le solve : "+grid.isSolved());
		System.out.println(grid);
		
    	*
    	int i =0;
    	while(true) {
    	 	Grid grid = gn.generateGrid(7,7);
    		Solver solver = new Solver(grid);
    		System.out.println(i++ + " :  "+ solver.solve() + " "+ grid.isSolved());
    		if(!grid.isSolved()) {
    			System.out.println(grid);
    			break;
    		}
    			
    	}
    	

    	/*
		Grid grid = gn.generateGrid(5,5);
		
    	*/
    	
    	Grid grid = new Grid(3,3);
		grid.putPiece(0, new OneConnections(0, 1));
		grid.putPiece(1,new L(1,2));
		grid.putPiece(2, new OneConnections(2, 2));
		
		grid.putPiece(3, new OneConnections(3,2));
		grid.putPiece(4, new OneConnections(4,0));
		grid.putPiece(5, new TwoConnections(5,0));
		
		
		grid.putPiece(6,new OneConnections(6,0));
		grid.putPiece(7,new OneConnections(7, 1));
		grid.putPiece(8,new L(8,3));
		
		
		for(Piece piece : grid.getGrid()) {
			if(piece != null) {
				grid.calculateConnections(piece);
				//System.out.println("Piece "+ piece.getPosition() + " Type : "+piece.getType() );
			}

		}
		grid.putOrientation(0, 3);
		grid.putOrientation(1, 3);
		grid.putOrientation(2, 3);
		grid.putOrientation(3, 1);
		grid.putOrientation(5, 1);
		grid.putOrientation(7, 2);
		
		Grid grid2 = gn.generateGrid(5,5);
    	SolverMaitre solver = new SolverMaitre(10,grid2);

    	//Solver solver = new Solver(grid2);
    	
    	System.out.println(solver.getGrid());
    	System.out.println(grid.isSolved());
    	
    	System.out.println("===============================================");	
    	System.out.println("Résolution ...");	
		solver.solve();
    	System.out.println("===============================================");	
    	
    	System.out.println(solver.getGrid().isSolved());
		System.out.println("Solver test :" + solver.getGrid());
		System.out.println("fini");
    	
		//Grid grid2 = gn.generateGrid(4, 4);
		//solver.setGrid(grid2);
		//System.out.println(solver.getGrid());
    	//System.out.println(grid2.isSolved());
		
		/*
		grid.putOrientation(0, 3);
		grid.putOrientation(1, 3);
		grid.putOrientation(2, 3);
		grid.putOrientation(3, 1);
		grid.putOrientation(5, 1);
		grid.putOrientation(7, 2);
		

		Grid grid = gn.generateGrid(4,4);

    	Solver solver = new Solver(grid);
    	System.out.println(grid);
    	System.out.println(grid.isSolved());

		solver.solveThreading(10);
		
    	System.out.println(grid.isSolved());
		System.out.println("Solver test :" +grid);
		*/

    	
    	/*Grid g=Converter.constrcutGrid("instances/public/grid_8x8_dist.0_vflip.false_hflip.false_messedup.false_id.3.dat");
    	Solver s2=new Solver(g);
    	System.out.println(g);
    	System.out.println("grille du prof : "+s2.isSolvable());*/
    	
    	


    }
}
