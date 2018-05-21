package dsa.project;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Help implements ActionListener{
	
public void actionPerformed(ActionEvent e) {
	
	JFrame help = new JFrame("Game 2048");
	
	JPanel panel = new JPanel();
	help.add(panel);
	panel.setBackground(Color.ORANGE);
	
	JLabel label2 = new JLabel();
	Image instruc = Toolkit.getDefaultToolkit().createImage("instruc.jpg"); 
	label2.setBounds(0, 5, 1200, 400);
	label2.setIcon(new ImageIcon(instruc));
	
	panel.setLayout(null);
	
	JButton play = new JButton("PLAY");
	play.setBounds(1060,405,140 ,60);
	panel.add(play);
	Image playimg  = Toolkit.getDefaultToolkit().createImage("playimg.jpg"); 
	play.setIcon(new ImageIcon(playimg));
	play.addActionListener(new Play());
	panel.add(label2);
	
	
	help.setSize(1205,500);
	help.setResizable(false);
	help.setLocationRelativeTo(null);
	help.setVisible(true);
	}
}