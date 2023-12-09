package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame window = new JFrame("Simple Tetris");
		window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Tetris");

		// Adds GamePanel to the window
		GamePanel gp = new GamePanel();
		window.add(gp);// adds the GamePanel
		window.pack(); // adds the size to the GamePanel

		window.setLocationRelativeTo(null);// does not set any specific location onto the screen
		window.setVisible(true);

		gp.launchGame();// lanuches the game

	}
}
