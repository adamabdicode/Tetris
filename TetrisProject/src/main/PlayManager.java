package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import mino.Block;
import mino.Mino;
import mino.Mino_Bar;
import mino.Mino_L1;
import mino.Mino_L2;
import mino.Mino_Square;
import mino.Mino_T;
import mino.Mino_Z1;
import mino.Mino_Z2;

/**
 * 
 * @author Adam Abdi Handels basic gameplay elements
 * 
 *         Draws the play area Manages the teteromines(geometric shapes) Handles
 *         gameplay actions(Draws the play area, manages teteromines, adding
 *         scores, etc.)
 */
public class PlayManager {

	final int WIDTH = 360; // size of the frame
	final int HEIGHT = 600;
	public static int leftX; // left of the frame
	public static int rightX; // right of the frame
	public static int topY; // top of the frame
	public static int bottomY;// bottom of the frame

	// Tetramino Blocks
	Mino currentMino;
	final int MinoStartX;
	final int MinoStartY;

	// Mino blocks for next mino
	Mino nextMino;
	final int nextMinoX;
	final int nextMinoY;
	public static ArrayList<Block> staticBlocks = new ArrayList<>(); // Blocks for the inactive mino (The mino once it
																		// hits the ground)
	// Effects
	boolean effectCounterOn;
	int effectCounter;
	ArrayList<Integer> effectY = new ArrayList<>();

	// Score
	int level = 1;
	int lines;
	int score;

	// Others
	public static int dropInterval = 60;// mino drops in 60 frames speed, dictates the mino drop speed
	boolean gameOver;

	public PlayManager() {
		// Main play area frame
		leftX = (GamePanel.WIDTH / 2) - (WIDTH / 2); // 1280/2-360/2-460
		rightX = leftX + WIDTH;
		topY = 50;
		bottomY = topY + HEIGHT;

		MinoStartX = leftX + (WIDTH / 2) - Block.SIZE; // divides the play area(Width) to be able to fit onto the game
														// for the mino

		MinoStartY = topY + Block.SIZE; // adds the block size onto the Mino to create it

		nextMinoX = rightX + 175;
		nextMinoY = topY + 500;

		// Sets the starting mino
		currentMino = pickMino(); // Activates the pick mino method and random mino's appear
		currentMino.setXY(MinoStartX, MinoStartY);

		// Puts the next mino onto the NEXT window
		nextMino = pickMino();
		nextMino.setXY(nextMinoX, nextMinoY);

	}

	/**
	 * Picks a random mino at the start of the game
	 * 
	 * @return random mino
	 */
	private Mino pickMino() {
		Mino mino = null;
		int i = new Random().nextInt(7);
		mino = new Mino_Z2();
		switch (i) {
		case 0:
			mino = new Mino_L1();
			break;
		case 1:
			mino = new Mino_L2();
			break;
		case 2:
			mino = new Mino_Square();
			break;
		case 3:
			mino = new Mino_Bar();
			break;
		case 4:
			mino = new Mino_T();
			break;
		case 5:
			mino = new Mino_Z1();
			break;
		case 6:
			mino = new Mino_Z2();
			break;
		}

		return mino;
	}

	public void update() {
		// checks to see if the current mino is active (auto drop is active)
		if (currentMino.active == false) {
			// If the mino is not active, put it into the staicBlocks
			staticBlocks.add(currentMino.b[0]);
			staticBlocks.add(currentMino.b[1]);
			staticBlocks.add(currentMino.b[2]);
			staticBlocks.add(currentMino.b[3]);

			// Check if the game is over, you do this by checking if the x and y are equal
			// to the next mino
			if (currentMino.b[0].x == MinoStartX && currentMino.b[0].y == MinoStartY) {
				gameOver = true;
				GamePanel.music.stop();
				GamePanel.se.play(2, false);
			}
			currentMino.deactivate = false; // Checks if the mino is hitting the ground after 45 frames

			// Replaces the current mino with the next Mino on the NEXT window
			currentMino = nextMino;
			currentMino.setXY(MinoStartX, MinoStartY);
			nextMino = pickMino();
			nextMino.setXY(nextMinoX, nextMinoY);// Test this

			// When an mino is inactive, check if it can be deleted
			checkDelete();

		} else {
			currentMino.update(); // updates the current mino onto the game/ Test this
		}
	}

	/**
	 * Deletes the block if a whole row has 12 blocks This is how you score points
	 * in the game
	 */
	private void checkDelete() {
		int x = leftX;
		int y = topY;
		int blockCount = 0;
		int lineCount = 0;

		while (x < rightX && y < bottomY) {
			for (int i = 0; i < staticBlocks.size(); i++) {
				if (staticBlocks.get(i).x == x && staticBlocks.get(i).y == y) {
					// Increase the count if there is a static block, TEST THIS
					blockCount++;
				}
			}
			x += Block.SIZE;
			if (x == rightX) {

				// If the block count is 12, that means the current y line is all filled with
				// blocks. So it can be deleted
				if (blockCount == 12) {
					effectCounterOn = true; // set the effects to true when you delete a line
					effectY.add(y);

					for (int i = staticBlocks.size() - 1; i > -1; i--) {
						// remove all blocks in the current Y line
						if (staticBlocks.get(i).y == y) {
							staticBlocks.remove(i);
						}
					}

					lineCount++; // increase the line count score when you delete a line
					lines++;

					// Increases the drop speed if the line hits a certain score when you delete a
					// line. 1 is the fastest
					if (lines % 10 == 0 && dropInterval > 1) { // Level increase every 10 lines, drop speed also
																// increases
						level++;
						if (dropInterval > 10) {
							dropInterval -= 10;
						} else {
							dropInterval -= 1;
						}
					}
					// a line has been deleted so need to slide down the block that are above it
					for (int i = 0; i < staticBlocks.size(); i++) {
						// if a block is above the current y, move it down by the block size
						if (staticBlocks.get(i).y < y) {
							staticBlocks.get(i).y += Block.SIZE;
						}
					}
				}

				blockCount = 0; // resets the block count, TEST THIS
				x = leftX;
				y += Block.SIZE;
			}
		}

		// Adds the scores
		if (lineCount > 0) {
			GamePanel.se.play(1, false); // Plays the delete line sound
			int singleLineScore = 10 * level; // Each line gives you 10 points
			score += singleLineScore + lineCount;
		}
	}

	public void draw(Graphics2D g2) {

		// Draws Play Area Frame (Big Rectangle)
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(4f)); // make's the rectangle bolder
		g2.drawRect(leftX - 4, topY - 4, WIDTH + 8, HEIGHT + 8);// draws the big white rectangle, Subtract 4 to avoid
																// collison on the corners

		// draws a mini frame (the waiting room for the tetraminos)
		int x = rightX + 100;
		int y = bottomY - 200;
		g2.drawRect(x, y, 200, 200); // draws the mini rectangle

		g2.setFont(new Font("Arial", Font.PLAIN, 30)); // sets the font for the text
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.drawString("NEXT", x + 60, y + 60); // draws the large text "NEXT"

		// Draw the score and the frame
		g2.drawRect(x, topY, 250, 300);
		x += 40;
		y = topY + 90;
		g2.drawString("LEVEL " + level, x, y);
		y += 70;
		g2.drawString("LEVEL " + lines, x, y);
		y += 70;
		g2.drawString("LEVEL " + score, x, y);

		// Draw the currentMino
		if (currentMino != null) {
			currentMino.draw(g2); // avoids null exception error and draws the Mino
		}
		// Draws the next mino onto the NEXT window
		nextMino.draw(g2);

		// Draws the static Blocks
		for (int i = 0; i < staticBlocks.size(); i++) {
			staticBlocks.get(i).draw(g2); // Make's sure that the static blocks stay down when it hits the bottom
		}

		// Draw effects
		if (effectCounterOn) {
			effectCounter++;

			g2.setColor(Color.red);
			for (int i = 0; i < effectY.size(); i++) {
				g2.fillRect(leftX, effectY.get(i), WIDTH, Block.SIZE);
			}
			if (effectCounter == 10) {
				effectCounterOn = false;
				effectCounter = 0;
				effectY.clear();
			}
		}
		// Draws the pause menu and game over message
		g2.setColor(Color.yellow);
		g2.setFont(g2.getFont().deriveFont(50f));
		if (gameOver) {
			x = leftX + 25;
			y = topY + 320;
			g2.drawString("GAME OVER", x, y);
		} else if (Movement.pausePressed) {
			x = leftX + 70;
			y = topY + 320;
			g2.drawString("PAUSED", x, y);
		}

		// Draw the game title
		x = 35;
		y = topY + 320;
		g2.setColor(Color.white);
		g2.setFont(new Font("Times New Roman", Font.ITALIC, 60));
		g2.drawString("Simple Tetris", x + 20, y);

	}
}
