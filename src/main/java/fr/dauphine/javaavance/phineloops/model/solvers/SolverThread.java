package fr.dauphine.javaavance.phineloops.model.solvers;

import java.util.ArrayList;
import java.util.List;

import fr.dauphine.javaavance.phineloops.model.Grid;
/**
 * This class allows to parallelize calculations.
 */
public class SolverThread implements Runnable {
	/**
	 * nbThreads : the number of the threads allowed to create
	 * cpt : the number of threads created
	 * mutex : Object to make efficient the synchronization 
	 * isRunning : boolean used to control the execution of the Threads. The Thread run when it's True and stops when it's False.
	 * orientations : the list of the domain to search
	 * grid : a reference to the grid, to make calculations on it
	 * solver : a reference to the solver, to replace the grid by the solved grid 
	 */
	private final int nbThreads;
	private static int cpt = 0;
	private static Object mutex = new Object();
	private boolean isRunning;
	private List<List<Integer>> orientations;
	private Grid grid;
	private Solver solver;
	
	/**
	 * Create a SolverThread
	 * @param solver : the solver who receives the call form the main class, to solve the grid using an MutliThreading method.
	 * @param orientations : the list of possible orientations
	 * @param nbThreads : the number of thread allowed to create 
	 * @param grid : the reference to a grid, cloned to avoid critical section
	 */
	public SolverThread(Solver solver, List<List<Integer>> orientations, int nbThreads, Grid grid) {
		this.orientations = orientations;
		this.nbThreads = nbThreads-1;
		this.isRunning = true;
		
		this.grid = grid.clone();
		this.solver = solver;
	}
	
	/**
	 * Run until a thread fin a solution, we suppose that the grid is solvable.
	 */
	@Override
	public void run() {
		while(isRunning){
			firstSolve(orientations);
			if(solver.getGrid().isSolved()) {
				this.isRunning = false;
			}
		}
	}
	
	/**
	 * This method is similar to that of the Clase Solver, except that in the event of success, it stop all the other threads, and replace the solver grid by the resolved one.
	 * There is a reuse of code in this case, because the solverthread when there is no more Thread at its disposal to create elements, itself continues the exploration of the search tree,
	 * and when there is threads still available, it shares the exploration.
	 * 
	 * @param list_of_orientations the list of possible orientation, 
	 *					   		   Pass it as a parameter because it is easier to verify that we are not in an infinite loop without a solution 
	 * @return a boolean according to the result of the resolution
	 */
	public boolean firstSolve(List<List<Integer>> list_of_orientations) {
		while(!grid.isSolved()) {
			//System.out.println(list_of_orientations);
			// Pour sortir de la boucle, soit réussite, soit on compare tmp et la nouvelle liste
			// si c'est la même, c'est que c'est une boucle infini
			ArrayList<ArrayList<Integer>> tmp = new ArrayList<ArrayList<Integer>>();
			for(int i = 0; i < grid.getheight()*grid.getWidth(); i++) {
				tmp.add(new ArrayList<Integer>(list_of_orientations.get(i)));
			}

			// On parcours la grille
			for (int i = 0; i < grid.getheight()*grid.getWidth(); i++) {
				if(list_of_orientations.get(i).size() == 1) {
					grid.putOrientation(i, list_of_orientations.get(i).get(0));// get(0) car il y a qu'un seul element dans la liste
					
					// North connection
					if(i >= grid.getWidth()) {
						if(grid.getPiece(i) != null 
								&& grid.getPiece(i).hasNorthConnection()) {
							if(grid.getPiece(i-grid.getWidth()) != null) {
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
								if(grid.getPiece(i+1) != null) {
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
								if(grid.getPiece(i-1) != null) {
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
						return cut(list_of_orientations);
					}
					
				}
				return false;
			}
		}
		solver.stopOthers();
		solver.setGrid(grid);
		return true;
	}
	
	/**
	 * This method is similar to that of the Class Solver,except that the Method synchronized by a mutex object shared by all instances of SolverThread 
	 * because on must not have a number of threads greater than the number given by the user. 
	 * The different threads when it parallelizes the calculation, see if the thread counter has not reached the number, if this is the case, continue the caclul themselves as in a non-threaded version 
	 * otherwise, increment the counter and launch the threads created.
	 * @param list the list to cut
	 * @return a boolean according to the state of the grid
	 */
	public boolean cut(List<List<Integer>> list){
		boolean isSolved = false;
		
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
		synchronized (mutex) {
			if(index2 != -1) {
					if(cpt < nbThreads) {
						List<List<Integer>> restrictedDomain1 = solver.createDomaine(list, list.get(index2).get(0), index2);
						List<List<Integer>> restrictedDomain2 = solver.createDomaine(list, list.get(index2).get(1), index2); 
						
						SolverThread st1 = new SolverThread(this.solver, restrictedDomain1, this.nbThreads, this.grid);
						Thread th1 = new Thread(st1);
						th1.start();
						cpt++;
					
						isSolved = firstSolve(restrictedDomain2);
	
					} else {
						List<List<Integer>> restrictedDomain1 = solver.createDomaine(list, list.get(index2).get(0), index2);
						List<List<Integer>> restrictedDomain2 = solver.createDomaine(list, list.get(index2).get(1), index2); 
						
						isSolved = firstSolve(restrictedDomain1) 
								|| firstSolve(restrictedDomain2);
					}
				
	
			}else {
				
				if(index3 != -1 ) {
						if(cpt < nbThreads) {
							List<List<Integer>> restrictedDomain1 = solver.createDomaine(list, list.get(index3).get(0), index3);
							List<List<Integer>> restrictedDomain2 = solver.createDomaine(list, list.get(index3).get(1), index3); 
							List<List<Integer>> restrictedDomain3 = solver.createDomaine(list, list.get(index3).get(2), index3); 
							
							SolverThread st1 = new SolverThread(this.solver, restrictedDomain1, this.nbThreads, this.grid);
							SolverThread st2 = new SolverThread(this.solver, restrictedDomain2, this.nbThreads, this.grid);
							
							Thread th1 = new Thread(st1);
							Thread th2 = new Thread(st2);
	
							
							th1.start();
							cpt++;
							th2.start();
							cpt++;
							
							isSolved = firstSolve(restrictedDomain3);
						}else {
							List<List<Integer>> restrictedDomain1 = solver.createDomaine(list, list.get(index3).get(0), index3);
							List<List<Integer>> restrictedDomain2 = solver.createDomaine(list, list.get(index3).get(1), index3); 
							List<List<Integer>> restrictedDomain3 = solver.createDomaine(list, list.get(index3).get(2), index3); 
							
							isSolved = firstSolve(restrictedDomain1) 
									|| firstSolve(restrictedDomain2) 
									|| firstSolve(restrictedDomain3);
						}
	
				} else {
						if(cpt < nbThreads) {
							List<List<Integer>> restrictedDomain1 = solver.createDomaine(list, list.get(index3).get(0), index4);
							List<List<Integer>> restrictedDomain2 = solver.createDomaine(list, list.get(index3).get(1), index4); 
							List<List<Integer>> restrictedDomain3 = solver.createDomaine(list, list.get(index3).get(2), index4); 
							List<List<Integer>> restrictedDomain4 = solver.createDomaine(list, list.get(index4).get(3), index4); 
							
							SolverThread st1 = new SolverThread(this.solver, restrictedDomain1, this.nbThreads, this.grid);
							SolverThread st2 = new SolverThread(this.solver, restrictedDomain2, this.nbThreads, this.grid);
							SolverThread st3 = new SolverThread(this.solver, restrictedDomain3, this.nbThreads, this.grid);
						
	
							
							Thread th1 = new Thread(st1);
							Thread th2 = new Thread(st2);
							Thread th3 = new Thread(st3);
	
							th1.start();
							cpt++;
							th2.start();
							cpt++;
							th3.start();
							cpt++;
							
							isSolved = firstSolve(restrictedDomain4);
							
						}else {
							List<List<Integer>> restrictedDomain1 = solver.createDomaine(list, list.get(index4).get(0), index4);
							List<List<Integer>> restrictedDomain2 = solver.createDomaine(list, list.get(index4).get(1), index4); 
							List<List<Integer>> restrictedDomain3 = solver.createDomaine(list, list.get(index4).get(2), index4); 
							List<List<Integer>> restrictedDomain4 = solver.createDomaine(list, list.get(index4).get(3), index4); 
							
							isSolved = firstSolve(restrictedDomain1) 
									|| firstSolve(restrictedDomain2) 
									|| firstSolve(restrictedDomain3) 
									|| firstSolve(restrictedDomain4);
						}
					}
				}
		}
		return isSolved;
	}
	/**
	 * Method for stopping thread execution
	 */
	public void stopRunning() {
			this.isRunning = false;
	}

}
