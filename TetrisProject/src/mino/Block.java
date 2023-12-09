package mino;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * 
 * @author Adam Abdi Sets the size of the tetramino and sets the color Mino
 *         means tetramino (which are the geometry blocks)
 */
public class Block extends Rectangle {
	public int x, y;
	public static int SIZE = 30; // 30x30 block, sets the size of the block
	public Color c;

	public Block(Color c) {
		this.c = c;
	}

	public void draw(Graphics2D g2) {
		int margin = 2;
		g2.setColor(c); // sets the color of the block
		g2.fillRect(x + margin, y + margin, SIZE - (margin * 2), SIZE - (margin * 2)); // Make the mino visible when
																						// they stack

	}
}
