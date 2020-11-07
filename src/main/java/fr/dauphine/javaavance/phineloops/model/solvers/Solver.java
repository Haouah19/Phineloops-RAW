package fr.dauphine.javaavance.phineloops.model.solvers;

import java.util.ArrayList;


import java.util.List;

import java.util.List;
import fr.dauphine.javaavance.phineloops.model.Grid;
/**
 * This class represents the solver, it is responsible for finding a solution to a given grid.
 * Solver must find a solution to a grid, which implies that the class manipulates the orientations of the pieces so that they are all connected together.
 */
public class Solver {
	/**
	 * grid : reference to the grid which must be solved.
	 *		  In the majority of cases, the grid is solvent and is not resolved at the time of instantiation.
	 * orientations : List which represents the possible orientations of each piece. 
	 * 				  This list will continue to be narrowed down as the execution progresses so as to have only one orientation per piece in the end.
	 * others : List of a size set by the user, There are n Threads.
				It allows to store the different Threads to be able to stop their execution, if one of the threads manages to find the solution.
	 */
	private Grid grid;
	private List<List<Integer>> orientations;
	private List<SolverThread> others ;
	
	/**
	 * This constructor allows instantiating a Solver.
	 * It allows you to fill the list with the possible orientations according to the type of parts.
	 * @param grid : the grid is given as a parameter, that it is retrieved from a text file present at the root of the project.
	 */
	public Solver(Grid grid) {
		this.grid = grid;
		this.orientations = new ArrayList<List<Integer>>();
		this.others = new ArrayList<SolverThread>();
		
		for(int i =0; i<grid.getheight()*grid.getWidth(); i++) {
			orientations.add(new ArrayList<Integer>());
			
			if(grid.getPiece(i) != null) {
				if(grid.getPiece(i).getType() == 1 
						|| grid.getPiece(i).getType() == 3
						|| grid.getPiece(i).getType() == 5 ) {
					orientations.get(i).add(0);
					orientations.get(i).add(1);
					orientations.get(i).add(2);
					orientations.get(i).add(3);
				}
				if(grid.getPiece(i).getType() == 2 ) {
					orientations.get(i).add(0);
					orientations.get(i).add(1);
				}
				if(grid.getPiece(i).getType() == 4 ) {
					orientations.get(i).add(0);
				}
			}
		}
	}

	/**
	 * Method which returns the list of possible orientations
	 * @return the orientations list
	 */
	public List<List<Integer>> getOrientations(){
		return this.orientations;
	}
	
	/**
	 * A fundamental method, it makes it possible to restrict the range of possible orientations.
	 * Thus, a part present on the corners or on the edges of the grid will have an orientation number impossible to have.
	 * for example, a piece of type 5 situated on a corner of the grid will only have one possible orientation.
	 * This method allows us for certain cases to fix the pieces.
	 */
	public void restrictDomain() {
		// Coin Nord-Ouest
		if(grid.getPiece(0) != null
				&& grid.getPiece(0).getType() == 1) {
			orientations.get(0).remove(orientations.get(0).indexOf(0));
			orientations.get(0).remove(orientations.get(0).indexOf(3));
		}else {
			if(grid.getPiece(0) != null
				&& grid.getPiece(0).getType() == 5) {
				orientations.get(0).remove(orientations.get(0).indexOf(0));
				orientations.get(0).remove(orientations.get(0).indexOf(2));
				orientations.get(0).remove(orientations.get(0).indexOf(3));
			}
		}

		// Coin Nord-Est
		if(grid.getPiece(grid.getWidth() -1) != null
				&& grid.getPiece(grid.getWidth() -1).getType() == 1) {
			orientations.get(grid.getWidth() -1).remove(orientations.get(grid.getWidth() -1).indexOf(0));
			orientations.get(grid.getWidth() -1).remove(orientations.get(grid.getWidth() -1).indexOf(1));
		}else {
			if(grid.getPiece(grid.getWidth() -1) != null
				&& grid.getPiece(grid.getWidth() -1).getType() == 5) {
				orientations.get(grid.getWidth() -1).remove(orientations.get(grid.getWidth() -1).indexOf(0));
				orientations.get(grid.getWidth() -1).remove(orientations.get(grid.getWidth() -1).indexOf(1));
				orientations.get(grid.getWidth() -1).remove(orientations.get(grid.getWidth() -1).indexOf(3));
			}
		}
		
		// Coin Sud-West
		if(grid.getPiece((grid.getWidth()*grid.getheight())-grid.getWidth()) != null
				&& grid.getPiece((grid.getWidth()*grid.getheight())-grid.getWidth()).getType() == 1) {
			orientations.get((grid.getWidth()*grid.getheight())-grid.getWidth()).remove(orientations.get((grid.getWidth()*grid.getheight())-grid.getWidth()).indexOf(2));
			orientations.get((grid.getWidth()*grid.getheight())-grid.getWidth()).remove(orientations.get((grid.getWidth()*grid.getheight())-grid.getWidth()).indexOf(3));
		}else {
			if(grid.getPiece((grid.getWidth()*grid.getheight())-grid.getWidth()) != null
				&& grid.getPiece((grid.getWidth()*grid.getheight())-grid.getWidth()).getType() == 5) {
				orientations.get((grid.getWidth()*grid.getheight())-grid.getWidth()).remove(orientations.get((grid.getWidth()*grid.getheight())-grid.getWidth()).indexOf(1));
				orientations.get((grid.getWidth()*grid.getheight())-grid.getWidth()).remove(orientations.get((grid.getWidth()*grid.getheight())-grid.getWidth()).indexOf(2));
				orientations.get((grid.getWidth()*grid.getheight())-grid.getWidth()).remove(orientations.get((grid.getWidth()*grid.getheight())-grid.getWidth()).indexOf(3));
			}
		}
		
		// Coin Sud-EST
		if(grid.getPiece((grid.getWidth()*grid.getheight())-1) != null
				&& grid.getPiece((grid.getWidth()*grid.getheight())-1).getType() == 1) {
			orientations.get((grid.getWidth()*grid.getheight())-1).remove(orientations.get((grid.getWidth()*grid.getheight())-1).indexOf(1));
			orientations.get((grid.getWidth()*grid.getheight())-1).remove(orientations.get((grid.getWidth()*grid.getheight())-1).indexOf(2));
		}else {
			if(grid.getPiece((grid.getWidth()*grid.getheight())-1) != null
				&& grid.getPiece((grid.getWidth()*grid.getheight())-1).getType() == 5) {
				orientations.get((grid.getWidth()*grid.getheight())-1).remove(orientations.get((grid.getWidth()*grid.getheight())-1).indexOf(0));
				orientations.get((grid.getWidth()*grid.getheight())-1).remove(orientations.get((grid.getWidth()*grid.getheight())-1).indexOf(1));
				orientations.get((grid.getWidth()*grid.getheight())-1).remove(orientations.get((grid.getWidth()*grid.getheight())-1).indexOf(2));
			}
		}
		
		// North Line
		for(int i= 1; i <grid.getWidth()-1 ; i++) {
			if(grid.getPiece(i) != null
					&& grid.getPiece(i).getType() == 1) {
				orientations.get(i).remove(orientations.get(i).indexOf(0));
			}
			if(grid.getPiece(i) != null
					&& grid.getPiece(i).getType() == 2) {
				orientations.get(i).remove(orientations.get(i).indexOf(0));
			}
			if(grid.getPiece(i) != null
					&& grid.getPiece(i).getType() == 3) {
					orientations.get(i).remove(orientations.get(i).indexOf(0));
					orientations.get(i).remove(orientations.get(i).indexOf(1));
					orientations.get(i).remove(orientations.get(i).indexOf(3));
			}
			if(grid.getPiece(i) != null
					&& grid.getPiece(i).getType() == 5) {
					orientations.get(i).remove(orientations.get(i).indexOf(0));
					orientations.get(i).remove(orientations.get(i).indexOf(3));
			}
		}
		
		// south line
		for(int i= (grid.getheight()*grid.getheight())-grid.getWidth()+1; i <grid.getWidth()*grid.getheight()-1 ; i++) {
			if(grid.getPiece(i) != null
					&& grid.getPiece(i).getType() == 1) {
				orientations.get(i).remove(orientations.get(i).indexOf(2));
			}
			if(grid.getPiece(i) != null
					&& grid.getPiece(i).getType() == 2) {
				orientations.get(i).remove(orientations.get(i).indexOf(0));
			}
			if(grid.getPiece(i) != null
					&& grid.getPiece(i).getType() == 3) {
					orientations.get(i).remove(orientations.get(i).indexOf(1));
					orientations.get(i).remove(orientations.get(i).indexOf(2));
					orientations.get(i).remove(orientations.get(i).indexOf(3));
			}
			if(grid.getPiece(i) != null
					&& grid.getPiece(i).getType() == 5) {
					orientations.get(i).remove(orientations.get(i).indexOf(1));
					orientations.get(i).remove(orientations.get(i).indexOf(2));
			}
		}
		
		// Est line
		for(int i= (grid.getWidth()*2)-1; i <grid.getWidth()*grid.getheight()-1 ; i+= grid.getWidth()) {
			if(grid.getPiece(i) != null
					&& grid.getPiece(i).getType() == 1) {
				orientations.get(i).remove(orientations.get(i).indexOf(1));
			}
			if(grid.getPiece(i) != null
					&& grid.getPiece(i).getType() == 2) {
				orientations.get(i).remove(orientations.get(i).indexOf(1));
			}
			if(grid.getPiece(i) != null
					&& grid.getPiece(i).getType() == 3) {
					orientations.get(i).remove(orientations.get(i).indexOf(0));
					orientations.get(i).remove(orientations.get(i).indexOf(1));
					orientations.get(i).remove(orientations.get(i).indexOf(2));
			}
			if(grid.getPiece(i) != null
					&& grid.getPiece(i).getType() == 5) {
					orientations.get(i).remove(orientations.get(i).indexOf(0));
					orientations.get(i).remove(orientations.get(i).indexOf(1));
			}
		}
		
		// West line
		for(int i= grid.getWidth(); i <(grid.getWidth()*grid.getheight())-grid.getWidth() ; i+= grid.getWidth()) {
			
			if(grid.getPiece(i) != null
					&& grid.getPiece(i).getType() == 1) {
				orientations.get(i).remove(orientations.get(i).indexOf(3));
			}
			if(grid.getPiece(i) != null
					&& grid.getPiece(i).getType() == 2) {
				orientations.get(i).remove(orientations.get(i).indexOf(1));
			}
			if(grid.getPiece(i) != null
					&& grid.getPiece(i).getType() == 3) {
					orientations.get(i).remove(orientations.get(i).indexOf(0));
					orientations.get(i).remove(orientations.get(i).indexOf(2));
					orientations.get(i).remove(orientations.get(i).indexOf(3));
			}
			if(grid.getPiece(i) != null
					&& grid.getPiece(i).getType() == 5) {
					orientations.get(i).remove(orientations.get(i).indexOf(2));
					orientations.get(i).remove(orientations.get(i).indexOf(3));
			}
		}
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
							
							if(grid.getPiece(i-grid.getWidth()) != null 
									&& orientations.get(i-grid.getWidth()).size() == 1 
									&& orientations.get(i-grid.getWidth()).get(0) == grid.getPiece(i-grid.getWidth()).getOrientation()
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
					
					// South connection
					if(i < (grid.getheight()*grid.getWidth())-grid.getWidth()) {
						
						
						if(grid.getPiece(i) != null 
								&& grid.getPiece(i).hasSouthConnection()) {
							
								if(grid.getPiece(i+grid.getWidth()) != null 
										&& orientations.get(i+grid.getWidth()).size() == 1 
										&& orientations.get(i+grid.getWidth()).get(0) == grid.getPiece(i+grid.getWidth()).getOrientation()
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
					
					
					// East Connection
					if(i%grid.getWidth() != grid.getWidth()-1) {
						
						if(grid.getPiece(i) != null 
								&& grid.getPiece(i).hasEastConnection()) {
							
							if(grid.getPiece(i+1) != null  
									&& orientations.get(i+1).size() == 1 
									&& orientations.get(i+1).get(0) == grid.getPiece(i+1).getOrientation()
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
					
					// West Connection
					if(i%grid.getWidth() != 0) {
						
						if(grid.getPiece(i) != null 
								&& grid.getPiece(i).hasWestConnection()) {
							
							if(grid.getPiece(i-1) != null  
									&& orientations.get(i-1).size() == 1 
									&& orientations.get(i-1).get(0) == grid.getPiece(i-1).getOrientation()
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
						return cut(list_of_orientations);
					}
				}
				return false;
			}
		}
		this.orientations = list_of_orientations;
		return true;
	}
	
	/**
	 * Cut allows you to create different lists according to the number of elements present in the object of these lists.
	 * We favor the creation of two lists if there are two possible orientations then 3 lists and then 4.
	 * We launch the firstSolve () method with these lists, so on until a solution has been reached or not arrived.
	 * The last call to firstSolve () will return true if a solution is found, which will have a snowball effect, until the first call to firstSolve (), 
	 * at this moment the grid is solved.
	 * @param list of the possible domain of orientations. 
	 * @return a boolean according to the result of the resolution.
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
		
		if(index2 != -1) {
			List<List<Integer>> restrictedDomain1 = createDomaine(list, list.get(index2).get(0), index2);
			List<List<Integer>> restrictedDomain2 = createDomaine(list, list.get(index2).get(1), index2); 
			
			isSolved = firstSolve(restrictedDomain1) || firstSolve(restrictedDomain2);
		}else {
			if(index3 != -1 ) {
				List<List<Integer>> restrictedDomain1 = createDomaine(list, list.get(index3).get(0), index3);
				List<List<Integer>> restrictedDomain2 = createDomaine(list, list.get(index3).get(1), index3); 
				List<List<Integer>> restrictedDomain3 = createDomaine(list, list.get(index3).get(2), index3); 
				
				isSolved = firstSolve(restrictedDomain1) 
						|| firstSolve(restrictedDomain2) 
						|| firstSolve(restrictedDomain3);
			} else {
				List<List<Integer>> restrictedDomain1 = createDomaine(list, list.get(index4).get(0), index4);
				List<List<Integer>> restrictedDomain2 = createDomaine(list, list.get(index4).get(1), index4); 
				List<List<Integer>> restrictedDomain3 = createDomaine(list, list.get(index4).get(2), index4); 
				List<List<Integer>> restrictedDomain4 = createDomaine(list, list.get(index4).get(3), index4); 
				
				isSolved = firstSolve(restrictedDomain1) 
						|| firstSolve(restrictedDomain2) 
						|| firstSolve(restrictedDomain3) 
						|| firstSolve(restrictedDomain4); 
			}
		}
		return isSolved;
	}
	
	/**
	 * method which allows the grid to be raised by calling the adjacent methods
	 * @return a boolean according to the result of the resolution.
	 */
	public  boolean solve() {
		restrictDomain(); // Pour les coins et les borders
		firstSolve(this.orientations);
		return grid.isSolved();
	}	
	
	/**
	 * Method specialized in the creation of a list with an assumption of orientation given in parameter and the index of the case also given in parameter.
	 * @param list the list of possible orientations (to restrict)
	 * @param orientation the assumption made by the calling method
	 * @param index : the index of this suppostion in the grid and in the liste (it's the same)
	 * @return the new list.
	 */
	public List<List<Integer>>  createDomaine(List<List<Integer>> list, int orientation, int index){
		List<List<Integer>> new_orientations = new ArrayList<List<Integer>>();
		
		for(int j = 0; j < grid.getheight()*grid.getWidth(); j++) {
			new_orientations.add(new ArrayList<Integer>(list.get(j)));
		}
		
		if(new_orientations.get(index).contains(0) 
				&& orientation != 0)
			new_orientations.get(index).remove(new_orientations.get(index).indexOf(0));
		
		if(new_orientations.get(index).contains(1) 
				&& orientation != 1)
			new_orientations.get(index).remove(new_orientations.get(index).indexOf(1));
		
		if(new_orientations.get(index).contains(2) 
				&& orientation != 2)
			new_orientations.get(index).remove(new_orientations.get(index).indexOf(2));
		
		if(new_orientations.get(index).contains(3) 
				&& orientation != 3)
			new_orientations.get(index).remove(new_orientations.get(index).indexOf(3));
		
		return new_orientations;
	}

	//==============================================================//
	// elements related to the multiThread version //
	/**
	 * return the grid
	 * @return the grid
	 */
	public Grid getGrid() {
		return this.grid;
	}
	
	/**
	 * Driving force of the resolution in multiThread.
	 * launches the first thread that performs the calculations. it implements the portfolio approach.
	 * @param nbThreads Number of threads allowed to use to solve the grid
	 * @return a boolean according to the result of the resolution.
	 * @throws InterruptedException To be able to wait for the end of the execution of all Threads, 
	 * 								we need a join which throws this exception
	 */
	public boolean solveThreading(Integer nbThreads) throws InterruptedException {
		restrictDomain();
		
		SolverThread st = new SolverThread(this, this.orientations, nbThreads,this.grid);
		Thread solverThread = new Thread(st);
		solverThread.start();
		
		
		solverThread.join();
		
		return this.grid.isSolved();
	}

	/**
	 * Allows to add a Thread in the list to be able to stop them when there is a solution found.
	 * @param st the Thread to add
	 */
	public void add(SolverThread st) {
		others.add(st);
	}
	
	/**
	 * Method which allows to stop all threads in execution.
	 */
	public void stopOthers() {
		for(SolverThread st : others) {
			if(st != null) {
				st.stopRunning();
			}
		}
	}
	/**
	 * This Setteur is important, 
	 * it allows the thread that has found a solution to modify the solver grid to be able to exploit this result.
	 * Because each thread works on a cloned grid. If all of the them worked on one grid, it will represent a critical section.
	 * @param grid the solved Grid
	 */
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
}
		


