package fr.dauphine.javaavance.phineloops.model.solvers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.dauphine.javaavance.phineloops.model.Grid;
/**
 * Experimenting the Master/Slave approach using thread pools.
 * Because we save the creation and destruction of Threads
 *
 */
public class SolverMaitre {
	/**
	 * the documentation for this class is completely similar to that of ThreadSolver
	 * except that it adds a thread pool of a size fixed during instantiation to manage the number of threads
	 */
	private ExecutorService pool;
	private Grid grid;
	private Solver solver;
	
	
	public SolverMaitre(int nbThreads, Grid grid) {
		this.grid = grid;
		this.pool = Executors.newFixedThreadPool(nbThreads-1);
		this.solver = new Solver(grid);
	}
	
	public boolean solve() {
		solver.restrictDomain();
		
		solveTheList(solver.getOrientations());
		
		return this.grid.isSolved();
	}
	/**
	 * allows stopping all tasks in progress if a solution is found.
	 * @param grid
	 */
	public void setGrid(Grid grid) {
		this.grid = grid;
		pool.shutdownNow();
	}
	
	/**
	 * This method does two things:
	 * 		First, define the orientation for the Pieces having a size 1 domain, that is to say that we are sure that it is the correct orientation.
	 * 		Second, by fixing the orientation of this Piece, We restrict the domain of its neighbors.
	 * For example, a fixed part with a north orientation. Its neighbor to the north must have a connection to the south.
	 * This means that we are going to eliminate in this neighbor domain all the orientations having no southern connection.
	 * 
	 * Then, when we no longer have a fixed Piece, we will make assumptions by calling another method.
	 * @param list_of_orientations the list of possible orientation, 
	 * 							   Pass it as a parameter because it is easier to verify that we are not in an infinite loop without a solution 
	 * @return a boolean according to the result of the resolution
	 */
	public boolean solveTheList(List<List<Integer>> list_of_orientations) {
		while(!grid.isSolved()) {

			ArrayList<ArrayList<Integer>> tmp = new ArrayList<ArrayList<Integer>>();
			for(int i = 0; i < grid.getheight()*grid.getWidth(); i++) {
				tmp.add(new ArrayList<Integer>(list_of_orientations.get(i)));
			}

			for (int i = 0; i < grid.getheight()*grid.getWidth(); i++) {
				if(list_of_orientations.get(i).size() == 1) {
					grid.putOrientation(i, list_of_orientations.get(i).get(0));// get(0) car il y a qu'un seul element dans la liste
					
					// North connection
					if(i >= grid.getWidth()) {
						
						if(grid.getPiece(i) != null 
								&& grid.getPiece(i).hasNorthConnection()) {
							
							if(grid.getPiece(i-grid.getWidth()) != null 
									&& solver.getOrientations().get(i-grid.getWidth()).size() == 1 
									&& solver.getOrientations().get(i-grid.getWidth()).get(0) == grid.getPiece(i-grid.getWidth()).getOrientation()
									&& !(grid.getPiece(i-grid.getWidth()).hasSouthConnection()))
									return false;
							
							if(grid.getPiece(i-grid.getWidth()) != null ) {
								
								switch(grid.getPiece(i-grid.getWidth()).getType()) {
									case 1:
										if(list_of_orientations.get(i-grid.getWidth()).contains(0))
											list_of_orientations.get(i-grid.getWidth()).remove(list_of_orientations.get(i-grid.getWidth()).indexOf(0));
										
										if(list_of_orientations.get(i-grid.getWidth()).contains(1))
											list_of_orientations.get(i-grid.getWidth()).remove(list_of_orientations.get(i-grid.getWidth()).indexOf(1));
										
										if(list_of_orientations.get(i-grid.getWidth()).contains(3))
											list_of_orientations.get(i-grid.getWidth()).remove(list_of_orientations.get(i-grid.getWidth()).indexOf(3));
										break;
										
									case 2:
										if(list_of_orientations.get(i-grid.getWidth()).contains(1))
											list_of_orientations.get(i-grid.getWidth()).remove(list_of_orientations.get(i-grid.getWidth()).indexOf(1));
										break;
									
									case 3:
										if(list_of_orientations.get(i-grid.getWidth()).contains(0))
											list_of_orientations.get(i-grid.getWidth()).remove(list_of_orientations.get(i-grid.getWidth()).indexOf(0));
										break;
									case 5:
										if(list_of_orientations.get(i-grid.getWidth()).contains(0))
											list_of_orientations.get(i-grid.getWidth()).remove(list_of_orientations.get(i-grid.getWidth()).indexOf(0));
										if(list_of_orientations.get(i-grid.getWidth()).contains(3))
											list_of_orientations.get(i-grid.getWidth()).remove(list_of_orientations.get(i-grid.getWidth()).indexOf(3));
										break;
										
								}
							}
						}
					}
					
					
					if(i < (grid.getheight()*grid.getWidth())-grid.getWidth()) {
						
						
						if(grid.getPiece(i) != null 
								&& grid.getPiece(i).hasSouthConnection()) {
							
								if(grid.getPiece(i+grid.getWidth()) != null 
										&& solver.getOrientations().get(i+grid.getWidth()).size() == 1 
										&& solver.getOrientations().get(i+grid.getWidth()).get(0) == grid.getPiece(i+grid.getWidth()).getOrientation()
										&& !(grid.getPiece(i+grid.getWidth()).hasNorthConnection()))
										return false;
						
								if(grid.getPiece(i+grid.getWidth()) != null) {
									
									switch(grid.getPiece(i+grid.getWidth()).getType()) {
										case 1:
											if(list_of_orientations.get(i+grid.getWidth()).contains(1))
												list_of_orientations.get(i+grid.getWidth()).remove(list_of_orientations.get(i+grid.getWidth()).indexOf(1));
											
											if(list_of_orientations.get(i+grid.getWidth()).contains(2))
												list_of_orientations.get(i+grid.getWidth()).remove(list_of_orientations.get(i+grid.getWidth()).indexOf(2));
											
											if(list_of_orientations.get(i+grid.getWidth()).contains(3))
												list_of_orientations.get(i+grid.getWidth()).remove(list_of_orientations.get(i+grid.getWidth()).indexOf(3));
											break;
											
										case 2:
											if(list_of_orientations.get(i+grid.getWidth()).contains(1))
												list_of_orientations.get(i+grid.getWidth()).remove(list_of_orientations.get(i+grid.getWidth()).indexOf(1));
											break;
										
										case 3:
											if(list_of_orientations.get(i+grid.getWidth()).contains(2))
												list_of_orientations.get(i+grid.getWidth()).remove(list_of_orientations.get(i+grid.getWidth()).indexOf(2));
											break;
										case 5:
											if(list_of_orientations.get(i+grid.getWidth()).contains(1))
												list_of_orientations.get(i+grid.getWidth()).remove(list_of_orientations.get(i+grid.getWidth()).indexOf(1));
											if(list_of_orientations.get(i+grid.getWidth()).contains(2))
												list_of_orientations.get(i+grid.getWidth()).remove(list_of_orientations.get(i+grid.getWidth()).indexOf(2));
											break;
										
								}
							}
						}
					}
					
					if(i%grid.getWidth() != grid.getWidth()-1) {
						
						if(grid.getPiece(i) != null 
								&& grid.getPiece(i).hasEastConnection()) {
							
							if(grid.getPiece(i+1) != null  
									&& solver.getOrientations().get(i+1).size() == 1 
									&& solver.getOrientations().get(i+1).get(0) == grid.getPiece(i+1).getOrientation()
									&& !(grid.getPiece(i+1).hasWestConnection()))
									return false; 
							
								if(grid.getPiece(i+1) != null ) {
									
									switch(grid.getPiece(i+1).getType()) {
										case 1:
											if(list_of_orientations.get(i+1).contains(0))
												list_of_orientations.get(i+1).remove(list_of_orientations.get(i+1).indexOf(0));
											
											if(list_of_orientations.get(i+1).contains(1))
												list_of_orientations.get(i+1).remove(list_of_orientations.get(i+1).indexOf(1));
											
											if(list_of_orientations.get(i+1).contains(2))
												list_of_orientations.get(i+1).remove(list_of_orientations.get(i+1).indexOf(2));
											break;
											
										case 2:
											if(list_of_orientations.get(i+1).contains(0))
												list_of_orientations.get(i+1).remove(list_of_orientations.get(i+1).indexOf(0));
											break;
										
										case 3:
											if(list_of_orientations.get(i+1).contains(1))
												list_of_orientations.get(i+1).remove(list_of_orientations.get(i+1).indexOf(1));
											break;
										case 5:
											if(list_of_orientations.get(i+1).contains(0))
												list_of_orientations.get(i+1).remove(list_of_orientations.get(i+1).indexOf(0));
											if(list_of_orientations.get(i+1).contains(1))
												list_of_orientations.get(i+1).remove(list_of_orientations.get(i+1).indexOf(1));
											break;
											
								}
							}
						}
					}
					if(i%grid.getWidth() != 0) {
						
						if(grid.getPiece(i) != null 
								&& grid.getPiece(i).hasWestConnection()) {
							
							if(grid.getPiece(i-1) != null  
									&& solver.getOrientations().get(i-1).size() == 1 
									&& solver.getOrientations().get(i-1).get(0) == grid.getPiece(i-1).getOrientation()
									&& !(grid.getPiece(i-1).hasEastConnection()))
									return false; 
							
								if(grid.getPiece(i-1) != null ) {
									
									switch(grid.getPiece(i-1).getType()) {
										case 1:
											if(list_of_orientations.get(i-1).contains(0))
												list_of_orientations.get(i-1).remove(list_of_orientations.get(i-1).indexOf(0));
											
											if(list_of_orientations.get(i-1).contains(2))
												list_of_orientations.get(i-1).remove(list_of_orientations.get(i-1).indexOf(2));
											
											if(list_of_orientations.get(i-1).contains(3))
												list_of_orientations.get(i-1).remove(list_of_orientations.get(i-1).indexOf(3));
											break;
											
										case 2:
											if(list_of_orientations.get(i-1).contains(0))
												list_of_orientations.get(i-1).remove(list_of_orientations.get(i-1).indexOf(0));
											break;
										
										case 3:
											if(list_of_orientations.get(i-1).contains(3))
												list_of_orientations.get(i-1).remove(list_of_orientations.get(i-1).indexOf(3));
											break;
										case 5:
											if(list_of_orientations.get(i-1).contains(2))
												list_of_orientations.get(i-1).remove(list_of_orientations.get(i-1).indexOf(2));
											if(list_of_orientations.get(i-1).contains(3))
												list_of_orientations.get(i-1).remove(list_of_orientations.get(i-1).indexOf(3));
											break;
										
								}
							}
						}
					}
				}	
			}
			if(tmp.equals(list_of_orientations)){
				for(int i = 0; i < grid.getheight()*grid.getWidth(); i++) {
					if(list_of_orientations.get(i).size() > 1) {
						cut(list_of_orientations);
					}
				}
				return false;
			}
		}
		return true;
	}
	
	/**
	 * put tasks in the thread pool to parallelize the calculation, 
	 * instead of launching the treads, because the threads are recovered at the end of an execution
	 * @param list
	 */
	public void cut(List<List<Integer>> list){
		int index2 = -1;
		int index3 = -1;
		int index4 = -1;
		for(int i = 0; i < grid.getheight()*grid.getWidth(); i++) {
				
			if(list.get(i) != null 
				&& list.get(i).size() == 2) {
				index2 = i;	
				break;
			}
			
			if(list.get(i) != null 
					&& list.get(i).size() == 3) {
				index3 = i;
			}
			
			if(list.get(i) != null 
					&& list.get(i).size() == 4) {
				index4 = i ;
			}
			
		}
		
		if(index2 != -1) {
			List<List<Integer>> restrictedDomain1 = solver.createDomaine(list, list.get(index2).get(0), index2);
			List<List<Integer>> restrictedDomain2 = solver.createDomaine(list, list.get(index2).get(1), index2); 
			
			pool.execute(new SolverSlave(this, restrictedDomain1, this.grid)); // puts Tasks 
			pool.execute(new SolverSlave(this, restrictedDomain2, this.grid));
		}else {
			if(index3 != -1 ) {
				List<List<Integer>> restrictedDomain1 = solver.createDomaine(list, list.get(index3).get(0), index3);
				List<List<Integer>> restrictedDomain2 = solver.createDomaine(list, list.get(index3).get(1), index3); 
				List<List<Integer>> restrictedDomain3 = solver.createDomaine(list, list.get(index3).get(2), index3); 
				
				pool.execute(new SolverSlave(this, restrictedDomain1, this.grid));
				pool.execute(new SolverSlave(this, restrictedDomain2, this.grid));
				pool.execute(new SolverSlave(this, restrictedDomain3, this.grid));
				
			} else {
				List<List<Integer>> restrictedDomain1 = solver.createDomaine(list, list.get(index4).get(0), index4);
				List<List<Integer>> restrictedDomain2 = solver.createDomaine(list, list.get(index4).get(1), index4); 
				List<List<Integer>> restrictedDomain3 = solver.createDomaine(list, list.get(index4).get(2), index4); 
				List<List<Integer>> restrictedDomain4 = solver.createDomaine(list, list.get(index4).get(3), index4); 
				
				pool.execute(new SolverSlave(this, restrictedDomain1, this.grid));
				pool.execute(new SolverSlave(this, restrictedDomain2, this.grid));
				pool.execute(new SolverSlave(this, restrictedDomain3, this.grid));
				pool.execute(new SolverSlave(this, restrictedDomain4, this.grid));
			}
		}
	}

	public Grid getGrid() {
		return this.grid;
	}
}
