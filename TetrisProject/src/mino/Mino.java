package mino;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;
import main.Movement;
import main.PlayManager;

public class Mino {

	public Block b[] = new Block[4];
	public Block tempB[] = new Block[4];
	int autoDropCounter = 0;
	public int direction = 1;// There are 4 directions(1/2/3/4)
	boolean leftCollision, rightCollision, bottomCollision;
	public boolean active = true; // auto drop variable to see if you can see auto drop
	public boolean deactivate; // check to see if the margin is deactivated to stop sliding when it fit
	// with a block

	int deactivateCounter = 0; // counts how long you can slide to fit into a block

	/**
	 * 
	 * Create the diffrent types of blocks and their colors
	 */
	public void create(Color c) {
		/**
		 * Sets different colors for different mino's
		 */
		b[0] = new Block(c);
		b[1] = new Block(c);
		b[2] = new Block(c);
		b[3] = new Block(c);
		tempB[0] = new Block(c);
		tempB[1] = new Block(c);
		tempB[2] = new Block(c);
		tempB[3] = new Block(c);

	}

	public void setXY(int x, int y) {
	}

	/**
	 * Updates the direction of the mino
	 * 
	 * @param direction
	 */
	public void updateXY(int direction) {
		checkRotationCollision(); // Change this to movement collision to test

		/**
		 * Updates x and y and checks if you rotate near the wall to make sure it
		 * doesn't go past it
		 */
		if (leftCollision == false && rightCollision == false && bottomCollision == false) {
			this.direction = direction;
			b[0].x = tempB[0].x;
			b[0].y = tempB[0].y;
			b[1].x = tempB[1].x;
			b[1].y = tempB[1].y;
			b[2].x = tempB[2].x;
			b[2].y = tempB[2].y;
			b[3].x = tempB[3].x;
			b[3].y = tempB[3].y;
		}
	}

	public void getDirection1() {
	}

	public void getDirection2() {
	}

	public void getDirection3() {
	}

	public void getDirection4() {
	}

	public void checkMovementCollision() {
		leftCollision = false;
		rightCollision = false;
		bottomCollision = false;

		// checks if the static blocks collide
		checkStaticBlockCollision();

		// checks if the mino collides or touch the left wall
		for (int i = 0; i < b.length; i++) {
			if (b[i].x == PlayManager.leftX) { // PlayManager is the walls, leftX is the left side
				leftCollision = true;
			}
		}
		// Now the right wall
		for (int i = 0; i < b.length; i++) {
			if (b[i].x + Block.SIZE == PlayManager.rightX) { // Add block size because rotating it can go out of the
																// window
				rightCollision = true;
			}
		}
		// Finally the bottom wall
		for (int i = 0; i < b.length; i++) {
			if (b[i].y + Block.SIZE == PlayManager.bottomY) {
				bottomCollision = true;
			}
		}
	}

	public void checkRotationCollision() {
		leftCollision = false;
		rightCollision = false;
		bottomCollision = false;

		// check if the static blocks collide
		checkStaticBlockCollision();

		// checks if the mino collides or touch the left wall
		for (int i = 0; i < b.length; i++) {
			if (tempB[i].x < PlayManager.leftX) { // Checks for collision using temp values
				leftCollision = true;
			}
		}
		// Now the right wall
		for (int i = 0; i < b.length; i++) {
			if (tempB[i].x + Block.SIZE > PlayManager.rightX) {
				rightCollision = true;
			}
		}
		// Finally the bottom wall
		for (int i = 0; i < b.length; i++) {
			if (tempB[i].y + Block.SIZE > PlayManager.bottomY) {
				bottomCollision = true;
			}
		}
	}

	/**
	 * Checks to see if the blocks collide with eacother so it can stack on top of
	 * each other
	 */
	private void checkStaticBlockCollision() {

		for (int i = 0; i < PlayManager.staticBlocks.size(); i++) {
			int targetX = PlayManager.staticBlocks.get(i).x;
			int targetY = PlayManager.staticBlocks.get(i).y; // Test these

			// check down for the left and right collisoin/ Test these
			for (int j = 0; j < b.length; j++) {
				if (b[j].y + Block.SIZE == targetY && b[j].x == targetX) {
					bottomCollision = true;
				}
			}

			// Check left, same concept as last and for the rest down
			for (int j = 0; j < b.length; j++) {
				if (b[j].x - Block.SIZE == targetX && b[j].y == targetY) {
					leftCollision = true;
				}
			}

			for (int j = 0; j < b.length; j++) {
				if (b[j].x + Block.SIZE == targetX && b[j].y == targetY) {
					rightCollision = true;
				}
			}
		}
	}

	/**
	 * Method to deactivate until drop for a certain amount of time until you fit
	 * the mino onto a specefic spot
	 */
	private void deactivating() {
		deactivateCounter++;

		// wait 45 frames until deactivate
		if (deactivateCounter == 45) {
			deactivateCounter = 0;
			checkMovementCollision(); // Checks if the mino is hitting the bottom

			// If the mino is still hitting the bottom after 45 frames, deactivate the mino
			if (bottomCollision) {
				active = false;
			}
		}
	}

	public void update() {
		if (deactivate) {
			deactivating();
		}

		if (bottomCollision) {
			if (deactivate == false) { //Checks if auto drop is off
				GamePanel.se.play(4, false); //Plays the bottom collision sound
			}
			deactivate = true; // Checks if the mino is still hitting the ground after 45 frames, deactivate
								// auto drop if it does
		} else {
			autoDropCounter++; // counter increases in every frame/test this
			if (autoDropCounter == PlayManager.dropInterval) {
				// the mino goes down
				b[0].y += Block.SIZE;
				b[1].y += Block.SIZE;
				b[2].y += Block.SIZE;
				b[3].y += Block.SIZE;
				autoDropCounter = 0;
			}
		}

		// Rotates the mino
		if (Movement.upPressed) {
			switch (direction) {
			case 1:
				getDirection2();
				break;
			case 2:
				getDirection3();
				break;
			case 3:
				getDirection4();
				break;
			case 4:
				getDirection1();
				break;
			}
			Movement.upPressed = false;
			GamePanel.se.play(3, false); // Plays the up sound effect
		}

		checkMovementCollision(); // calls the method to check if the mino is colliding with the wall

		/**
		 * Moves the mino down
		 */

		if (Movement.downPressed) { // moves the mino when you move down, similar conditionals have similar concepts
			if (bottomCollision == false) { // Checks to see if the mino is not colliding with the wall
				b[0].y += Block.SIZE;
				b[1].y += Block.SIZE;
				b[2].y += Block.SIZE;
				b[3].y += Block.SIZE;

				autoDropCounter = 0; // Reset the counter when you move down
			}
			Movement.downPressed = false; // Set it to false so the mino doesn't ifinitly go down
		}
		/**
		 * Moves the mino left
		 */
		if (Movement.leftPressed) {
			if (leftCollision == false) {
				b[0].x -= Block.SIZE;
				b[1].x -= Block.SIZE;
				b[2].x -= Block.SIZE;
				b[3].x -= Block.SIZE;

			}
			Movement.leftPressed = false;
		}
		/**
		 * Moves the mino right
		 */
		if (Movement.rightPressed) {
			if (rightCollision == false) {
				b[0].x += Block.SIZE;
				b[1].x += Block.SIZE;
				b[2].x += Block.SIZE;
				b[3].x += Block.SIZE;
			}
			Movement.rightPressed = false;
		}

	}

	public void draw(Graphics2D g2) {
		int margin = 2; // puts black lines onto the mino
		g2.setColor(b[0].c); // sets the color to the specefic mino color
		g2.fillRect(b[0].x + margin, b[0].y + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
		g2.fillRect(b[1].x + margin, b[1].y + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
		g2.fillRect(b[2].x + margin, b[2].y + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
		g2.fillRect(b[3].x + margin, b[3].y + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
	}
}
