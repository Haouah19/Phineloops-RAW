package fr.dauphine.javaavance.phineloops.view;



import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoadGame extends JFrame implements ActionListener{
	private JPanel p1 ,p2,p3;
	private JLabel l1;
	private JButton b;
	public LoadGame() {
		setTitle("Phine Loops");
		setSize(470,700);
		setLocationRelativeTo(this);
		p1 = new JPanel();
		add(p1,"North");
		l1=new JLabel("Phine Loops");
		
		Font font = new Font("Arial",Font.BOLD,25);
		l1.setFont(font);
		p1.add(l1);
		p2=new JPanel();
		add(p2,"Center");
		ImageIcon im=new ImageIcon("pics/im1.png"); 
		JLabel lab = new JLabel(im);
		p2.add(lab);
		p3=new JPanel();
		add(p3,"South");
		p3.setBackground(Color.GRAY);
		b=new JButton("Commencer la partie");
		b.setBackground(Color.LIGHT_GRAY);
		p3.add(b);
		b.addActionListener(this);
	}

	
	public void start () {
		LoadGame i1= new LoadGame();
		i1.setVisible(true);
	}
	
	public static void main(String [] args) {
		LoadGame i1= new LoadGame();
		i1.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==b) {
			Main j = new Main();
			j.setVisible(true);
			this.dispose();
			
		}
		
	}

}

