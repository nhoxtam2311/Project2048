package dsa.project;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameApp{
	
	public static void main(String[] args)  {
		JFrame menu = new JFrame("Game 2048");
		
		JPanel panel = new JPanel();
		menu.add(panel);
		
		JLabel label1 = new JLabel();
		Image menuimg  = Toolkit.getDefaultToolkit().createImage("menuimg.jpg"); 
		label1.setBounds(0, 0, 300, 550);
		label1.setIcon(new ImageIcon(menuimg));
		
		panel.setLayout(null);
	    
		JButton play= new JButton("PLAY");
		play.setBounds(79,100,140 ,60);
		panel.add(play);
		Image playimg  = Toolkit.getDefaultToolkit().createImage("playimg.png"); 
		play.setIcon(new ImageIcon(playimg));
		play.addActionListener(new Play());
		
		JButton help = new JButton("HELP");
		help.setBounds(79,180,140,60);
		panel.add(help);
		Image helpimg  = Toolkit.getDefaultToolkit().createImage("helpimg.png"); 
		help.setIcon(new ImageIcon(helpimg));
		help.addActionListener(new Help());
		
		
		JButton players = new JButton("2 Player");
		players.setBounds(79,260,140,60);
		panel.add(players);
		Image playersimg  = Toolkit.getDefaultToolkit().createImage("2playerimg.png"); 
		players.setIcon(new ImageIcon(playersimg));
		players.addActionListener(new twoPlayerapp());
			
		panel.add(label1);	
		
		menu.setSize(300,585);
		menu.setResizable(false);
		menu.setLocationRelativeTo(null);
		menu.setVisible(true);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

}
