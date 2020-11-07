package fr.dauphine.javaavance.phineloops.model.solvers;

import java.util.ArrayList;


import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

import fr.dauphine.javaavance.phineloops.model.Grid;
import fr.dauphine.javaavance.phineloops.model.utils.Piece;
/**
 * Class modeling a solver based on CSP problem solving.
 * We use the ChocoSolver library to model the CSP problem
 */
public class SolverCSP {
	/**
	 * Given our confrontations with different problems to create the necessary constraints in order to solve this problem of satisfaction.
	 * We opted for two approaches to define the variables.
	 * 
	 * one defining 9 types of variables:
	 * 		northWestCorner : the domain of North West corner, put according to the type of the Piece,
	 * 		southEastCorner : the domain of South East corner, put according to the type of the Piece,
	 *		southWestCorner : the domain of South West corner, put according to the type of the Piece,
	 * 		northEastCorner : the domain of North East corner, put according to the type of the Piece,
	 * 
	 * 		For the moment the area of ​​these parts is defined in a non-optimized way :
	 * 		westLineBorder [] : the domain of West Line Border,
	 * 		northLineBorder [] : the domain of North Line Border, 
	 *		southLineBorder [] : the domain of South Line corner, 
	 *		eastLineBorder [] : the domain of  East Line Border,
	 * 		middlePieces [] : the domain of all middle pieces.
	 * 
	 * 
	 * The other approach is :
	 *  We define a Variables grid as long as the number of parts in the grid, and we restrict the domain according to the position of the part and its type.
	 *  This restriction step was aboded because we could not improve our constraints
	 */
	private Grid grid;
	private Model model;
	private IntVar northWestCorner;
	private IntVar southEastCorner;
	private IntVar southWestCorner;
	private IntVar northEastCorner;
	private IntVar westLineBorder [];
	private IntVar northLineBorder [];
	private IntVar southLineBorder [];
	private IntVar eastLineBorder [];
	private IntVar middlePieces [];
	
	// Grille complète des cases sans optimisation
	private IntVar gridVariables[];
	private Constraint contraintes[];
	
	
	public SolverCSP(Grid grid) {
		this.grid = grid;
		this.model = new Model();
		
		// first approach
		this.initialiseVariables(); /** If we choose the first approach, we are oblige to add some constraints */

		// second approach
		this.gridVariables = new IntVar[grid.getWidth()*grid.getheight()];
		this.contraintes = new Constraint[grid.getWidth()*grid.getheight()];
	}
	
	/**
	 * Mother method of this class, it launches the resolution of the Problem.
	 * For the moment it only implements the second approach because, 
	 * it was difficult to define relevant constraints for the first approach
	 */
	public void solve() {
		List<Integer> orientation = new ArrayList<>();
		
		/** If we choose the second approache */
		for(int i=0; i<grid.getGrid().size(); i++) {
			this.gridVariables[i] = model.intVar("Piece " +i, new int[]{0,1,2,3}); // Pour l'instant, puis on va restreindre le domaine des orientations
		}
		
		for(int i=0; i<grid.getGrid().size(); i++) {
			contraintes[i] = new Constraint( "Piece" + i, new ConstraintsOrientations(grid, i, gridVariables[i]));
			contraintes[i].post();
		}
		
		int index =0;
		org.chocosolver.solver.Solver solver = model.getSolver();
	    if(solver.solve()) {
	    		for(int i=0; i<gridVariables.length ; i++) {
	    			orientation.add(gridVariables[i].getValue());
	    		}
	    		grid.putOrientation(index, orientation.get(index));
	    		index++;
	    }
	    if(grid.isSolved())
	    	System.out.println("Grid Solved");
		
	}
	
	
	// First approach //
	/**
	 * Method which allows to initialize the variables, according to the position of the different pieces in the grid
	 */
	public void initialiseVariables() {
		// corners
		this.createNorthWestCornerVariables(grid.getPiece(0));
		this.createNorthEastCornerVariables(grid.getPiece(grid.getWidth()-1));
		this.createSouthWestCornerVariables(grid.getPiece((grid.getheight()*grid.getWidth())-grid.getWidth()));
		this.createSouthEastCornerVariables(grid.getPiece((grid.getheight()*grid.getWidth())-1));
		
		// Tabs
		this.createWestLineBorderVariables(grid);
		this.createNorthLineBorderVariables(grid);
		this.createSouthLineBorderVariables(grid);
		this.createEastLineBorderVariables(grid);
		
		// Middle
		this.createMiddlePieceVariables(grid);
	}
	public void putConstraints() {

		
	}
	
	/**
	 * Set of methods that define the variables :
	 */
	
	private void createNorthWestCornerVariables(Piece piece) {
		if(piece.getType() == 1)
			northWestCorner = model.intVar("northWestCorner", new int[]{1, 2});
		else 
			if(piece.getType() == 5)
				northWestCorner = model.intVar("northWestCorner", 1);
	}
	
	private void createSouthEastCornerVariables(Piece piece) {
		if(piece.getType() == 1)
			southEastCorner = model.intVar("southEastCorner", new int[]{0, 3});
		else 
			if(piece.getType() == 5)
				southEastCorner = model.intVar("southEastCorner", 3);
	}
	
	private void createSouthWestCornerVariables(Piece piece) {
		if(piece.getType() == 1)
			southWestCorner = model.intVar("southWestCorner", new int[]{0, 1});
		else 
			if(piece.getType() == 5)
				southWestCorner = model.intVar("southWestCorner", 0);
	}
	
	private void createNorthEastCornerVariables(Piece piece) {
		if(piece.getType() == 1)
			northEastCorner = model.intVar("northEastCorner", new int[]{2, 3});
		else 
			if(piece.getType() == 5)
				northEastCorner = model.intVar("northEastCorner", 2);
	}

	private void createWestLineBorderVariables(Grid grid) {
		
		/**
		 * Je ne sais pas ou il faut restreindre le domaine, ici ou dans les contraintes
		 * si on ne fixe pas le domaine ici, il faut plus de contrainte
		 * Sujet à débat 
		 
		List<Integer> listOrientation = new ArrayList<Integer>();
		for(int i= grid.getheight(); i <= (grid.getheight()*grid.getWidth())-grid.getWidth() ; i+=grid.getheight()) {
			switch(grid.getPiece(i).getType()) {
				case 1:
					listOrientation.add(0);
					listOrientation.add(1);
					listOrientation.add(2);
					break;
				case 2: 
					listOrientation.add(0);
				case 3: 
					listOrientation.add(1);
				case 5: 
					listOrientation.add(0);
					listOrientation.add(1);
					break;
			}
		}
		*/
		
		int westLineLength = grid.getheight()-2;
		westLineBorder = model.intVarArray("westLineBorder", westLineLength, new int[]{0, 2, 3});
		
	}
	
	private void createNorthLineBorderVariables(Grid grid) {
		int northLineLength = grid.getWidth()-2;
		northLineBorder = model.intVarArray("northLineBorder", northLineLength, 1, 3);
	}
	
	private void createSouthLineBorderVariables(Grid grid) {
		int southLineLength = grid.getWidth()-2;
		southLineBorder = model.intVarArray("southLineBorder", southLineLength, 1, 3);
	}
	
	private void createEastLineBorderVariables(Grid grid) {
		int eastLineLength = grid.getheight()-2;
		eastLineBorder = model.intVarArray("eastLineBorder", eastLineLength, new int[]{1, 3});
	}
	private void createMiddlePieceVariables(Grid grid) {
		int middlePieceLenght = grid.getheight()*grid.getWidth()-(grid.getheight()*2) -(grid.getWidth()*2)-4;
		middlePieces = model.intVarArray("middlePieces", middlePieceLenght, new int[]{0,1, 2,3});
	}

}
