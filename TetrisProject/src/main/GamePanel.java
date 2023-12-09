package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * 
 * @author Adam Abdi Class for all the functioins of a JPanel
 */
public class GamePanel extends JPanel implements Runnable {
	public static final int WIDTH = 1280;// Screen size for Width
	public static final int HEIGHT = 720;// Screen size for Height
	final int FPS = 60; // sets the FPS
	Thread gameThread;
	PlayManager pm;
	public static Soundtrack music = new Soundtrack();  //Implements the sound track class
	public static Soundtrack se= new Soundtrack();

	public GamePanel() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));// sets the size, almost like frame.setSize
		this.setBackground(Color.black);
		this.setLayout(null);// check what this does

		pm = new PlayManager(); // calls the playmanager class

		// Implements the Movement
		this.addKeyListener(new Movement());
		this.setFocusable(true); // gets the key input
	}

	/**
	 * Launches the game
	 */
	public void launchGame() {
		gameThread = new Thread(this);
		gameThread.start(); // calls the run method
		
		//Plays the music
		music.play(0,true);
		music.loop();
	}

	@Override
	public void run() {
		// Game Loop
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime(); // Stops time in nanoseconds
		long currentTime;

		// Runs the game
		while (gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			if (delta >= 1) {
				update(); // calls the update method 60 times per second
				repaint(); // calls the repaint method 60 times per second
				delta--;
			}
		}
	}

	/**
	 * Check to see if the game is over or if the game is paused. If it is, end or
	 * pause the game
	 */
	private void update() {
		if (Movement.pausePressed == false && pm.gameOver == false) {
			pm.update();
		}
	}

	/**
	 * paints the pictures on the game
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);// calls the paint method

		Graphics2D g2 = (Graphics2D) g;
		pm.draw(g2);
	}
}
