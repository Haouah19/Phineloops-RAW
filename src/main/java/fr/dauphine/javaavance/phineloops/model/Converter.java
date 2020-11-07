package fr.dauphine.javaavance.phineloops.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import fr.dauphine.javaavance.phineloops.model.utils.FourConnections;
import fr.dauphine.javaavance.phineloops.model.utils.L;
import fr.dauphine.javaavance.phineloops.model.utils.OneConnections;
import fr.dauphine.javaavance.phineloops.model.utils.Piece;
import fr.dauphine.javaavance.phineloops.model.utils.ThreeConnections;
import fr.dauphine.javaavance.phineloops.model.utils.TwoConnections;
/**
 * Converter acts as a portal between files and our application.
 * This class has two static functions, one translates a Grid into a file respecting the format described by the statement. 
 * The returned file will be at the root of the project.
 * The second function converts a file to an instance of Grid.
 */
public class Converter {
	/**
	 * construcrGrid : Convert a given file as a parameter to an instance of the Grid class
	 * This function is static so you can access it from anywhere without being able to instantiate a converter
	 * The file must have a very specific format for this extraction to work correctly
	 * @param fileName the name of the file where the grid representation is located
	 * @return a grid with a height and a width recover from the file, create and fill the pieces respecting their orientations and their types
 	 * @exception FileNotFoundException to avoid the exception, you must specify a file present in the project and respect its name
 	 * @exception IOException Signals that an I/O exception of some sort has occurred. 
	 */
	public static Grid constrcutGrid(String fileName)  {
		BufferedReader in;
		File file = new File(fileName);
		ArrayList<String> lines = new ArrayList<>();
		try {
			in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
			String line;
			while ((line = in.readLine()) != null){
				lines.add(line);
			}
			
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int width = Integer.parseInt(lines.get(0));
		int height = Integer.parseInt(lines.get(1));
		
		Grid grid = new Grid(width, height);
		
		/** To facilitate extraction, we remove the two unnecessary elements (width / length) from the list */
		lines.remove(0); 
		lines.remove(0); 

		for(int i=0; i<lines.size(); i++) {
			String element = lines.get(i);
			int numero_piece = Integer.parseInt(String.valueOf(element.charAt(0)));
			int orientation = Integer.parseInt(String.valueOf(element.charAt(2)));
			
			switch(numero_piece) {
				case 0:
					grid.putPiece(i, null); // add null when the Piece is empty
					break;
				case 1:
					Piece piece1 = new OneConnections(i, orientation);
					grid.putPiece(i, piece1);
					break;
				case 2:
					Piece piece2 = new TwoConnections(i, orientation);
					grid.putPiece(i, piece2);
					break;
				case 3:
					Piece piece3 = new ThreeConnections(i, orientation);
					grid.putPiece(i, piece3);
					break;
				case 4:
					Piece piece4 = new FourConnections(i, orientation);
					grid.putPiece(i, piece4);
					break;
				case 5:
					Piece piece5 = new L(i, orientation);
					grid.putPiece(i, piece5);
					break;
			}
		}
		return grid;
	}
	/**
	 * this method converts an instance of Grid into a file called file.txt, this file will be found at the root of the project
	 * @param grid The grid instance that the method will convert
	 * @param outPutFile the name of the file to create 
	 * @exception IOException Signals that an I/O exception of some sort has occurred.
	 */
	public static void constrcutFile(Grid grid, String outputFile) {
	    PrintWriter writer= null;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
			
			writer.println(grid.getheight());
			writer.println(grid.getWidth());
			
			for(int i =0 ; i<=grid.getheight()*grid.getWidth()-1; i++) {
				if(grid.getPiece(i) != null) {
					writer.println(grid.getPiece(i).getType()+" "+grid.getPiece(i).getOrientation());
				}else {
					writer.println("0 0"); // If the Piece is null, we add a line in the file of type 0 and orientation 0
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	  
	    writer.close();
	}
}
