package fr.dauphine.javaavance.phineloops.view;

import javax.swing.JFrame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import fr.dauphine.javaavance.phineloops.controller.Controller;
/**
 * Main class of the GUI. 
 * This class implements Observer, Requests the state of the model when it displays the grid,
 * if there is a change in the model, the view is notified and updated.
 *
 */
public class Main extends JFrame implements Observer,ActionListener{
	private JPanel panelNorth,panelSouth,panelCenter;
	private JLabel labelHeight,labelWidht,labelNbc,labelNbThread;
	private JTextField textfieldNBC,textFieldHeight,textfielwidht,textfieldNbThread;
	private JButton boutonGenerer,boutonSolve,boutonl,lab,lab2,lab3,lab4,b;
	

	
	/**
	 * There is a reference to the controller, because he is responsible for managing user actions and informing the model
	 */
	private Controller controller;
	
	/**
	 * Constructs an instance of the Main class with a Controller taken as a parameter
	 * @param controller : The controller is taken as a parameter because there is a main class responsible for creating any essential element to create and launch the graphical interface.
	 */
	public Main() {
		setTitle("phine loops");
		setSize(700,700);
		setLocationRelativeTo(this);
		setVisible(true);
		
		panelNorth=new JPanel();
		add(panelNorth,"North");
		
		labelHeight=new JLabel("longueur de la grille ");
		panelNorth.add(labelHeight);
		
		textFieldHeight = new JTextField(3);
		panelNorth.add(textFieldHeight);
		
		labelWidht = new JLabel("largeur de la grille");
		panelNorth.add(labelWidht);
		
		textfielwidht = new JTextField(3);
		panelNorth.add(textfielwidht);
		
		labelNbc = new JLabel("NBC");
		panelNorth.add(labelNbc);
		
		textfieldNBC = new JTextField(3);
		panelNorth.add(textfieldNBC);
		
		boutonGenerer=new JButton("Generer");
		panelNorth.add(boutonGenerer);
		boutonGenerer.addActionListener(this);
		
		panelSouth = new JPanel();
		add(panelSouth,"South");
	
		labelNbThread = new JLabel("Nombre de thread");
		panelSouth.add(labelNbThread);
		
		textfieldNbThread = new JTextField(3);
		panelSouth.add(textfieldNbThread);
		
		boutonSolve = new JButton("Solve");
		panelSouth.add(boutonSolve);
		
		boutonSolve.addActionListener(this);
		
		panelCenter = new JPanel();
		add(panelCenter,"Center");
		panelCenter.setVisible(true);
		
	
		
		
	}
	public Controller getController() {
		return this.controller;
	}
	
	
	/**
	 * main purpose is the grid display
	 * Browse the list and display the icons according to the orientations and types
	 */
	public JPanel display() {
		//System.out.println("Le controller est : "+ this.controller.getGrid());
		panelCenter.setVisible(true);
		add(panelCenter);
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new GridLayout(Integer.parseInt(textFieldHeight.getText()),Integer.parseInt(textfielwidht.getText())));
		// je pense qu'on ajoute de nouveauc buttons à la fin de la liste grid layout à chaque foiss
		for (int i = 0; i <Integer.parseInt(textFieldHeight.getText())*Integer.parseInt(textfielwidht.getText()); i++) {
			if(controller.getGrid().get(i) != null) {
				switch (controller.getGrid().get(i).getType()) {
				case 1 :
					switch (controller.getGrid().get(i).getOrientation()) {
						case 0:
							controller.getboutons().get(i).setIcon(new ImageIcon("pics/1 0.png"));
							//JButton b = new JButton(new ImageIcon("pics/1 0.png")); 
							controller.getboutons().get(i).addActionListener(this);
							panelCenter.add(controller.getboutons().get(i));
					//		boutons.add(b);
							break;
						case 1 : 
							controller.getboutons().get(i).setIcon(new ImageIcon("pics/1 1.png"));
							controller.getboutons().get(i).addActionListener(this);
							panelCenter.add(controller.getboutons().get(i));
							
							//JButton b1 = new JButton(new ImageIcon("pics/1 1.png")); 
							//b1.addActionListener(this);
							//panelCenter.add(b1);
							//boutons.add(b1);
							break;
						case 2 : 
							controller.getboutons().get(i).setIcon(new ImageIcon("pics/1 2.png"));
							controller.getboutons().get(i).addActionListener(this);
							panelCenter.add(controller.getboutons().get(i));
							
							
							
							//JButton b2 = new JButton(new ImageIcon("pics/1 2.png")); 
							//b2.addActionListener(this);
							//panelCenter.add(b2);
							//boutons.add(b2);
							break;
						case 3 : 
							controller.getboutons().get(i).setIcon(new ImageIcon("pics/1 3.png"));
							controller.getboutons().get(i).addActionListener(this);
							panelCenter.add(controller.getboutons().get(i));
							
							
							
							//JButton b3 = new JButton(new ImageIcon("pics/1 3.png")); 
							//b3.addActionListener(this);
							//panelCenter.add(b3);
							//boutons.add(b3);
							break;
					default:
						break;
					}
					
					break;
				case 2 :
					switch (controller.getGrid().get(i).getOrientation()) {
						case 0:
							controller.getboutons().get(i).setIcon(new ImageIcon("pics/2 0.png"));
							controller.getboutons().get(i).addActionListener(this);
							panelCenter.add(controller.getboutons().get(i));
							
							
							
							//JButton b = new JButton(new ImageIcon("pics/2 0.png")); 
							//b.addActionListener(this);
							//panelCenter.add(b);
							//boutons.add(b);
							break;
						case 1 : 
							controller.getboutons().get(i).setIcon(new ImageIcon("pics/2 1.png"));
							controller.getboutons().get(i).addActionListener(this);
							panelCenter.add(controller.getboutons().get(i));
							
							
							
							//JButton b2 = new JButton(new ImageIcon("pics/2 1.png")); 
							//b2.addActionListener(this);
							//panelCenter.add(b2);
							//boutons.add(b2);
							break;
					default:
						break;
					}
					break;
				case 3 : 
					switch (controller.getGrid().get(i).getOrientation()) {
						case 0:
							controller.getboutons().get(i).setIcon(new ImageIcon("pics/3 0.png"));
							controller.getboutons().get(i).addActionListener(this);
							panelCenter.add(controller.getboutons().get(i));
							
							
							//JButton b = new JButton(new ImageIcon("pics/3 0.png")); 
							//b.addActionListener(this);
							//panelCenter.add(b);
							//boutons.add(b);
							break;
						case 1 : 
							controller.getboutons().get(i).setIcon(new ImageIcon("pics/3 1.png"));
							controller.getboutons().get(i).addActionListener(this);
							panelCenter.add(controller.getboutons().get(i));
							
							
							
							//JButton b1 = new JButton(new ImageIcon("pics/3 1.png")); 
							//b1.addActionListener(this);
							//panelCenter.add(b1);
							//boutons.add(b1);
							break;
						case 2 : 
							controller.getboutons().get(i).setIcon(new ImageIcon("pics/3 2.png"));
							controller.getboutons().get(i).addActionListener(this);
							panelCenter.add(controller.getboutons().get(i));
							
							
							//JButton b2 = new JButton(new ImageIcon("pics/3 2.png"));
							//b2.addActionListener(this);
							//panelCenter.add(b2);
							//boutons.add(b2);
							break;
						case 3 : 
							controller.getboutons().get(i).setIcon(new ImageIcon("pics/3 3.png"));
							controller.getboutons().get(i).addActionListener(this);
							panelCenter.add(controller.getboutons().get(i));
							
							//JButton b3 = new JButton(new ImageIcon("pics/3 3.png")); 
							//b3.addActionListener(this);
							//panelCenter.add(b3);
							//boutons.add(b3);
							break;
		
						default:
							break;
					}
					
					break;
				
				case 4 : 
					switch (controller.getGrid().get(i).getOrientation()) {
						case 0:
							controller.getboutons().get(i).setIcon(new ImageIcon("pics/4 0.png"));
							controller.getboutons().get(i).addActionListener(this);
							panelCenter.add(controller.getboutons().get(i));
							
							
							//JButton b = new JButton(new ImageIcon("pics/4 0.png")); 
							//b.addActionListener(this);
							//panelCenter.add(b);
							//boutons.add(b);
							break;
					default:
						break;
					}
					
					break;
				
				case 5 : 
					switch (controller.getGrid().get(i).getOrientation()) {
						case 0:
							controller.getboutons().get(i).setIcon(new ImageIcon("pics/5 0.png"));
							controller.getboutons().get(i).addActionListener(this);
							panelCenter.add(controller.getboutons().get(i));
							
							
							//JButton b = new JButton(new ImageIcon("pics/5 0.png"));
							//b.addActionListener(this);
							//panelCenter.add(b);
							//boutons.add(b);
							break;
						case 1 : 
							controller.getboutons().get(i).setIcon(new ImageIcon("pics/5 1.png"));
							controller.getboutons().get(i).addActionListener(this);
							panelCenter.add(controller.getboutons().get(i));
							
							
							
							//JButton b1 = new JButton(new ImageIcon("pics/5 1.png"));
							//b1.addActionListener(this);
							//panelCenter.add(b1);
							//boutons.add(b1);
							break;
						case 2 :
							controller.getboutons().get(i).setIcon(new ImageIcon("pics/5 2.png"));
							controller.getboutons().get(i).addActionListener(this);
							panelCenter.add(controller.getboutons().get(i));
							
							
							//JButton b2 = new JButton(new ImageIcon("pics/5 2.png"));
							//b2.addActionListener(this);
							//panelCenter.add(b2);
							//boutons.add(b2);
							break;
						case 3 : 
							controller.getboutons().get(i).setIcon(new ImageIcon("pics/5 3.png"));
							controller.getboutons().get(i).addActionListener(this);
							panelCenter.add(controller.getboutons().get(i));
							
							
							//JButton b3 = new JButton(new ImageIcon("pics/5 3.png"));
							//b3.addActionListener(this);
							//panelCenter.add(b3);
							//boutons.add(b3);
							break;
		
						default:
							break;
					}
					break;
			
	
				default:
					break;
				}
			}else {
				controller.getboutons().get(i).setIcon(new ImageIcon(""));
				controller.getboutons().get(i).addActionListener(this);
				panelCenter.add(controller.getboutons().get(i));
				
				
				//JButton b = new JButton(new ImageIcon(""));
				//panelCenter.add(b);
				//boutons.add(b);
			}
		} 
		return panelCenter;

	}
	
	//  controller.isGridSolved()
	
	/**
	 * Mandatory method to implement.
	 * Called by the observable when there is a change in the model so that it redisplays the changed grid.
	 */
	@Override
	public void update() {
		this.panelCenter=this.display();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main m = new Main();
		m.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == boutonGenerer) {
			int height =Integer.parseInt(textFieldHeight.getText());
			int widht =Integer.parseInt(textfielwidht.getText());
			int nbc =Integer.parseInt(textfieldNBC.getText());
			
			
			
			this.controller = new Controller(this, height,widht,nbc);
			TestG g = new TestG(this);
			g.setVisible(true);
			
			//display(this.panelCenter);
			//add(this.panelCenter,"Center");
			
			
			
			
			//c.isGridSolved();
		}
		if (e.getSource()==boutonSolve) {
			controller.solve();
			Gui g = new Gui(this);
			g.setVisible(true);
			
		}
		/*
		for (int i = 0; i < boutons.size(); i++) {
			if (e.getSource()==boutons.get(i)) {
				controller.putModification(i);
				if(controller.isGridSolved())
					System.out.println("Grille résolu");
			}
		}*/
		
	} 
}
