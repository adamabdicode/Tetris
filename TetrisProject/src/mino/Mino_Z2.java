package mino;

import java.awt.Color;

public class Mino_Z2 extends Mino {
	public Mino_Z2() {
		create(Color.green); // makes the mino color green
	}

	/**
	 * Creates the mino varation
	 */
	public void setXY(int x, int y) {
		//  0
		//  0 0
		//    0 

		b[0].x = x;
		b[0].y = y; // creates the middle block, doesn't change so I don't have to do much
		b[1].x = b[0].x;
		b[1].y = b[0].y - Block.SIZE; // for the top block, you must substract the block size
		b[2].x = b[0].x + Block.SIZE;
		b[2].y = b[0].y;
		b[3].x = b[0].x + Block.SIZE; // b3 is the bottom right block
		b[3].y = b[0].y + Block.SIZE;
	}

	/**
	 * Rotates the mino to a certain direction
	 */
	public void getDirection1() {
		// 0 
		// 0 0
		//   0 
		
		tempB[0].x = b[0].x;
		tempB[0].y = b[0].y;
		tempB[1].x = b[0].x;
		tempB[1].y = b[0].y - Block.SIZE;
		tempB[2].x = b[0].x + Block.SIZE;
		tempB[2].y = b[0].y;
		tempB[3].x = b[0].x + Block.SIZE;
		tempB[3].y = b[0].y + Block.SIZE;

		updateXY(1); // updates so the mino rotates

	}

	public void getDirection2() {
		//  0 0
		//0 0
		tempB[0].x = b[0].x;
		tempB[0].y = b[0].y;
		tempB[1].x = b[0].x - Block.SIZE;
		tempB[1].y = b[0].y;
		tempB[2].x = b[0].x;
		tempB[2].y = b[0].y - Block.SIZE;
		tempB[3].x = b[0].x + Block.SIZE;
		tempB[3].y = b[0].y - Block.SIZE;

		updateXY(2); // updates so the mino rotates
	}

	/**
	 * Z2 Block only has two directions, so I repated the method
	 */
	public void getDirection3() {
		getDirection1();
	}

	public void getDirection4() {
		getDirection2();
}
}
