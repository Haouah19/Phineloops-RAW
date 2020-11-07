package fr.dauphine.javaavance.phineloops; 

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Option.Builder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import fr.dauphine.javaavance.phineloops.model.Converter;
import fr.dauphine.javaavance.phineloops.model.Generator;
import fr.dauphine.javaavance.phineloops.model.Grid;
import fr.dauphine.javaavance.phineloops.model.solvers.Solver;
import fr.dauphine.javaavance.phineloops.model.utils.Piece;
import fr.dauphine.javaavance.phineloops.view.LoadGame;;

public class Main {
    private static String inputFile = null;  
    private static String outputFile = null;
    private static Integer width = -1;
    private static Integer height = -1;
    private static Integer maxcc = -1; 
    
    private static Integer nbThreads = 1;
    

    private static void generate(int width, int height, String outputFile){
    	Generator gn = new Generator();
    	Grid grid = gn.generateGrid(width, height);
    	Converter.constrcutFile(grid, outputFile);
    }

    private static boolean solve(String inputFile, String outputFile){
    	System.out.println("launch of the almost exhaustive solver...");
    	
    	Grid grid = Converter.constrcutGrid(inputFile);
    	Solver solver = new Solver(grid);
    	int totalNbConnections = 0;
		for (Piece p : grid.getGrid()) 
		{
			if(p != null)
				totalNbConnections+=p.getNbConncetionsPossible();
		}
		
		if(totalNbConnections%2 != 0)
		{
			Converter.constrcutFile(grid, outputFile);
			return false;
		}
		else
		{
			for (int i =0;i<grid.getWidth()*grid.getheight(); i++) 
			{
				if(grid.getPiece(i) != null)
				{
					int nbrVoisinsNonNulls=grid.calculatePossibleConnections(grid.getPiece(i));
					totalNbConnections -= min(grid.getPiece(i).getNbConncetionsPossible(),nbrVoisinsNonNulls);
				}
			}
			if(totalNbConnections != 0)
			{
				Converter.constrcutFile(grid, outputFile);
				return false;
			}
		}
		
		
    	boolean isSolved = solver.solve();
    	Converter.constrcutFile(grid, outputFile);
    	
    	return isSolved;
    }

	private static boolean solve(String inputFile, String outputFile, Integer nbThreads) {
    	System.out.println("Since you specify the number of Threads, the solver in a multi-threaded version is launched...");
		
    	Grid grid = Converter.constrcutGrid(inputFile);
    	Solver solver = new Solver(grid);
    	boolean isSolved = false;
    	
    	try {
    		isSolved = solver.solveThreading(nbThreads);
    	}catch( InterruptedException e) {
    		System.out.println("Problem with the multiThreading version, an interruptedException has occured");
    	}catch(Exception e) {
    		System.out.println("Problem with the multiThreading version");
    	}
    	
    	Converter.constrcutFile(grid, outputFile);
		return isSolved ;
	}
	
    private static boolean check(String inputFile){
    	Grid g=Converter.constrcutGrid(inputFile);
    	for (Piece p : g.getGrid()) 
    	{
    		g.calculateConnections(p);
		}
    	return g.isSolved(); 
    }
    
    private static int min(int a, int b)
	{
		if(a<b)
			return a;
		else
			return b;
	}
    
    public static void main(String[] args) {
        Options options = new Options();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        
        options.addOption("g", "generate ", true, "Generate a grid of size height x width.");
        options.addOption("c", "check", true, "Check whether the grid in <arg> is solved.");
        
        options.addOption("s", "solve", true, "Solve the grid stored in <arg>.");   
        options.addOption("o", "output", true, "Store the generated or solved grid in <arg>. (Use only with --generate and --solve.)");
        options.addOption("t", "threads", true, "Maximum number of solver threads. (Use only with --solve.)");
        options.addOption("x", "nbcc", true, "Maximum number of connected components. (Use only with --generate.)");
        options.addOption("G", "gui", true, "Run with the graphic user interface.");
        options.addOption("h", "help", false, "Display this help");
        
        try {
            cmd = parser.parse( options, args);         
        } catch (ParseException e) {
            System.err.println("Error: invalid command line format.");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "phineloops", options );
            System.exit(1);
        }       
                
	try{  

		if(cmd.hasOption("G")) {
			System.out.println("Starting the GUI...\n");
			LoadGame lg = new LoadGame();
			lg.start();
			
			System.out.println("If the GUI does not launch properly, we apologize.\n" + 
					"go to src/main/java/dauphine/javaavance/phineloops/view/LoadGame.java and launch the main.\n\n");
		
		} 
		
		if( cmd.hasOption( "g" ) ) {
			System.out.println("Running phineloops generator.\n" + "The generated file is at the root of the project.");
			String[] gridformat = cmd.getOptionValue( "g" ).split("x");
			width = Integer.parseInt(gridformat[0]);
			height = Integer.parseInt(gridformat[1]); 
			if(! cmd.hasOption("o")) throw new ParseException("Missing mandatory --output argument.");
			outputFile = cmd.getOptionValue( "o" );
			
			if(cmd.hasOption("x"))
				System.out.println("We try to approach the number of connected components requested.");
		

			generate(width, height, outputFile); 
			
	    }else if( cmd.hasOption( "s" ) ) {
	    	
			System.out.println("Running phineloops solver.\n" + "The generated file is at the root of the project.");
			inputFile = cmd.getOptionValue( "s" );
			if(! cmd.hasOption("o")) throw new ParseException("Missing mandatory --output argument.");      
			outputFile = cmd.getOptionValue( "o" );
			
			if( cmd.hasOption( "t" ) )
				nbThreads = Integer.parseInt(cmd.getOptionValue("t"));
			
			boolean solved = false;
	
			if(nbThreads == 1)
				solved = solve(inputFile, outputFile); 
			else 
				solved = solve(inputFile, outputFile, nbThreads);
			
			System.out.println("SOLVED: " + solved);            
	    }else if( cmd.hasOption( "c" )) {
	    	
			System.out.println("Running phineloops checker.");
			inputFile = cmd.getOptionValue( "c" );
			
			boolean solved = check(inputFile); 
	
			System.out.println("SOLVED: " + solved);     
			
	    } else {
	    	throw new ParseException("You must specify at least one of the following options: -generate -check -solve ");           
	    }
	    
	} catch (ParseException e) {
            System.err.println("Error parsing commandline : " + e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( "phineloops", options );         
            System.exit(1); // exit with error      
	}
        System.exit(0); // exit with success                            
    }

}
