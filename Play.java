package dsa.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class Play implements ActionListener{
	
public void actionPerformed(ActionEvent e) {
	
	JFrame window = new JFrame("Game 2048");
	Game2048 game = new Game2048();
	window.add(game);
	game.start();

	window.setSize(950,900);
	window.setResizable(false);
	window.setLocationRelativeTo(null);
	window.setVisible(true);
//	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
