package dsa.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class twoPlayerapp implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		JFrame window = new JFrame("Game 2048");		
		twoPlayer players = new twoPlayer();
		window.add(players);
		players.start2player();
		
		window.setSize(1500, 900);	
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}

