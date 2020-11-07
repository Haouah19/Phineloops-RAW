package fr.dauphine.javaavance.phineloops.controller;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import fr.dauphine.javaavance.phineloops.model.Generator;
import fr.dauphine.javaavance.phineloops.model.Grid;
import fr.dauphine.javaavance.phineloops.model.solvers.Solver;
import fr.dauphine.javaavance.phineloops.model.utils.Piece;
import fr.dauphine.javaavance.phineloops.view.Observer;

/**
 * This Class will be responsible for making the link between our view and our model.
 * It will intercept the actions of the user, who will click on a Piece to change the orientation, 
 * and send it to the model.
 * since the controller must interact with the model, it will have an instance of our model.
 * the informations to be displayed in the view will be sent via the implementation of the observer pattern between our model and our view.
 */
public class Controller {
	/**
	 * Since the controller must interact with the model, it will have to have an instance of our model
	 */
	private Grid grid; // The grid is null until you click on the button to generate the grid
	private ArrayList<JButton> boutons = new ArrayList<JButton>();	/**
	 * allows to instantiate the Controller with the attributes retrieve from the view.
	 * and instantiate the grid with the observer 
	 * @param obs Observer the main class of the GUI
	 * @param width represents the width of the grid
	 * @param height represents the length of the grid
	 * @param nbC number of related components
	 */
	public Controller(Observer obs, int height, int width, int nbc) {
		Generator gn = new Generator();
		this.grid = gn.generateGrid(width, height);
		this.grid.addObserver(obs);
		System.out.println(grid);
		for (int i = 0; i < height*width; i++) {
			boutons.add(new JButton(new ImageIcon("pics/1 0.png")));
		}
	}

	// Only for tests
	public Controller(Grid grid) {
		this.grid = grid;
	}
	public ArrayList<JButton> getboutons(){
		return boutons;
	}
	public void solve() {
		Solver sv = new Solver(grid);
		sv.solve();
	}
	
	/**
	 * The main goal of this method is to allow to change the orientation of a piece in the grid.
	 * When the user clicks on a Piece, he changes the orientation of this Piece.
	 * This method "putModification ()" allows to modify the model, so this modification is taken into account in the view.
	 * @param index : the index of the piece in the Grid, a number between 0 and the size of the Grid
	 * @param orientation The new orientation of the Piece
	 */
	public void putModification(int index) {
		if(grid.getPiece(index) != null) {
			int orientation = grid.getPiece(index).getOrientation();
			switch(grid.getPiece(index).getType()) {
				case 1:
					grid.putOrientation(index, (orientation+1)%4);
					break;
				case 2:
					grid.putOrientation(index,(grid.getPiece(index).getOrientation()+1)%2);
					break;
				case 3:
					grid.putOrientation(index,(grid.getPiece(index).getOrientation()+1)%4);
					break;
				case 4:
					break;
				case 5:
					grid.putOrientation(index,(grid.getPiece(index).getOrientation()+1)%4);
					break;
			}
		}
		
	}
	
	/**
	 * This method returns the Pieces list. It is used to display the Pieces of the Grid
	 * @return the grid pieces list
	 */
	public ArrayList<Piece> getGrid(){
		return grid.getGrid();
	}
	
	/**
	 * We call this method to find out if the grid is resolved.
 	 * It is called when the user changes the orientation of a part.
	 * @return a boolean according to the state of the Grid
	 */
	public boolean isGridSolved() {
		return grid.isSolved();
	}
}
