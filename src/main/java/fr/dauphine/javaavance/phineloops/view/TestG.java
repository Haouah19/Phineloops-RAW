package fr.dauphine.javaavance.phineloops.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TestG extends JFrame implements  ActionListener{
	private JPanel jp,panelSouth;
	private JTextField textfieldNbThread;
	private JLabel labelNbThread;
	private JButton boutonSolve;
	private Main m;
	public TestG(Main m) {
		setSize(400,400);
		setLocationRelativeTo(this);
		setTitle("Game");
		jp=m.display();
		add(jp,"Center");
		panelSouth = new JPanel();
		add(panelSouth,"South");
		labelNbThread = new JLabel("Nombre de thread");
		panelSouth.add(labelNbThread);
		
		textfieldNbThread = new JTextField(3);
		panelSouth.add(textfieldNbThread);
		
		boutonSolve = new JButton("Solve");
		panelSouth.add(boutonSolve);
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main m = new Main();
		TestG g = new TestG(m);
		g.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()== boutonSolve) {
			m.getController().solve();
			jp=m.display();
		}
		
	}



}
