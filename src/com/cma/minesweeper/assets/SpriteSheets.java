package com.cma.minesweeper.assets;
import java.awt.image.BufferedImage;


/**
 * @author seijaku
 * Utilitas untuk melakukan cropping pada sebuah spritesheet
 */
public class SpriteSheets {

	
	private BufferedImage sheet;

	public SpriteSheets(BufferedImage sheet) {
		this.sheet = sheet;
	}

	public BufferedImage crop(int row, int col, int width) {
		int y = width * row;
		int x = width * col;
		return sheet.getSubimage(x, y, width, width);
	}
}
