package mino;

import java.awt.Color;

public class Mino_Square extends Mino {

	/**
	 * Changes the Mino color to yellow
	 */
	public Mino_Square() {
		create(Color.yellow);
	}

	/**
	 * Creates the T mino
	 */
	public void setXY(int x, int y) {
		// 0 0
		// 0 0

		b[0].x = x;
		b[0].y = y; // creates the middle block, doesn't change so I don't have to do much
		b[1].x = b[0].x;
		b[1].y = b[0].y + Block.SIZE; // for the top block, you must substract the block size
		b[2].x = b[0].x + Block.SIZE;
		b[2].y = b[0].y;
		b[3].x = b[0].x + Block.SIZE; // b3 is the bottom right block
		b[3].y = b[0].y + Block.SIZE;
	}

	/**
	 * You can't rotate a square, so no need to implement code for direction
	 */
	public void getDirection1() {
	}

	public void getDirection2() {
	}

	public void getDirection3() {
	}

	public void getDirection4() {
	}
}
