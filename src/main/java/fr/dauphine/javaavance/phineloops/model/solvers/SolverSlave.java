package fr.dauphine.javaavance.phineloops.model.solvers;

import java.util.List;

import fr.dauphine.javaavance.phineloops.model.Grid;
/**
 * Class representing a slave, its sole objective is to perform calculations attributed by the master
 *
 */
public class SolverSlave implements Runnable {

	private List<List<Integer>> orientations;
	private Grid grid;
	private SolverMaitre solver;
	
	public SolverSlave(SolverMaitre solver, List<List<Integer>> orientations, Grid grid) {
		this.orientations = orientations;
		this.grid = grid.clone();
		this.solver = solver;
	}
	
	@Override
	public void run() {
			while(!(this.grid.isSolved())){
				solver.solveTheList(orientations);
				
				if(solver.getGrid().isSolved()) {
					System.out.println(solver.getGrid().isSolved());
					break;
				}
			}
			if(this.grid.isSolved())
				solver.setGrid(this.grid);	
	}
}
