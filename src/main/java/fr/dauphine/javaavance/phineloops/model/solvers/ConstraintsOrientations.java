package fr.dauphine.javaavance.phineloops.model.solvers;


import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.ESat;

import fr.dauphine.javaavance.phineloops.model.Grid;

import org.chocosolver.solver.constraints.*;
import org.chocosolver.solver.exception.ContradictionException;
/**
 * Class representing a constraint, the problem was to create variables on orientations, and to apply constraints on the number of parts connection.
 * creating our own constraints seemed like a good solution. this class uses this logic
 */
public final class ConstraintsOrientations extends Propagator<IntVar> {
	/**
	 * grid : to get all the information needed to calculate the solution
	 * position : to manipulate the attributes
	 * the variable to fix the constraint. 
	 */
	private Grid grid;
	private int position;
	private IntVar x;

	
	public ConstraintsOrientations(Grid grid,int position, IntVar x) {
		super(new IntVar[]{x}, PropagatorPriority.BINARY,false);
		this.grid = grid;
		this.position = position;
		this.x = x;
	}
	
	/**
	 * return a boolean according to the state of the Piece.
	 */
	@Override
	public ESat isEntailed() {
		if(!this.grid.getPiece(position).isConnected()) {
			return ESat.TRUE;
		}
		return ESat.FALSE;
	}

	/**
	 * look if the constraint is right, and give to the variable the right orientation by redefining it.
	 */
	@Override
	public void propagate(int arg0) throws ContradictionException {
		this.grid.getPiece(position).putOrientation(x.getValue());
		this.grid.calculateConnections(this.grid.getPiece(position));
		
		if(this.grid.getPiece(position).isConnected()) {
			x = model.intVar("Piece" + position, 1);
		}
	}
	

}
