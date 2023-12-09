package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Class for the movement of the tetramino
 * 
 * @author Adam Abdi
 *
 */
public class Movement implements KeyListener {
	public static boolean upPressed, downPressed, leftPressed, rightPressed, pausePressed; // Variables to check what
																							// the user clicks

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode(); // checks if the user clicks an arrow

		if (code == KeyEvent.VK_W) {
			upPressed = true; // If the user clicks w, go left. All of the statements apply
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if (code == KeyEvent.VK_SPACE) {
			if (pausePressed) { // Checks if you paused the game with the space key
				pausePressed = false;
				GamePanel.music.play(0, true);
				GamePanel.music.loop();// Music keeps playing when the user unpauses the game
			} else {
				pausePressed = true;
				GamePanel.music.stop();// Stops plaing the music when you pause
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
