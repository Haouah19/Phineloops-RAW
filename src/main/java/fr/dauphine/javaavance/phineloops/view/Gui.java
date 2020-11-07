package fr.dauphine.javaavance.phineloops.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gui extends JFrame {
	private JPanel jp;
	Main m;
	Gui(Main m){
		this.m=m;
		setSize(400,400);
		setLocationRelativeTo(this);
		setTitle("Game");
		jp=m.display();
		add(jp,"Center");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main m = new Main();
		Gui g = new Gui(m);
		g.setVisible(true);

	}
}
