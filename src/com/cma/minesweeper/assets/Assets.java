package com.cma.minesweeper.assets;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import com.cms.minespweeper.game.CellStates;


/**
 * @author seijaku
 * Class untuk meng-inisialisasi asset
 * Mengolah dan melakukan drawing gambar
 */
public class Assets {
	
	public static final int spriteWidth=16;
	public static BufferedImage covered;
	public static BufferedImage[] uncovered;
	public static BufferedImage mine,flag;
	public static BufferedImage bomb,wrongFlag;
	
	

	/**
	 * @author seijaku
	 * Fungsi static untuk menginsialisasi gambar
	 * 
	 */
	public static void init() {
		//load image
		BufferedImageLoader loader = new BufferedImageLoader();
		SpriteSheets sheet = null;
		try {
			sheet = new SpriteSheets(loader.loadImage("res/predatorskin.bmp"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//crop image
		uncovered= new BufferedImage[9];
		for(int i=0; i<uncovered.length;i++) {
			uncovered[i]= sheet.crop(0,i,spriteWidth);
		}
		covered = sheet.crop(1, 0, spriteWidth);
		mine = sheet.crop(1, 2, spriteWidth);
		flag = sheet.crop(1, 3, spriteWidth);
		wrongFlag = sheet.crop(1, 4, spriteWidth);
		bomb = sheet.crop(1, 5, spriteWidth);
	}
	
	
	/**
	 * @author seijaku
	 * Class untuk meng-inisialisasi asset
	 * Mengolah dan melakukan drawing gambar
	 */
	public static void draw(int row, int col, CellStates state, Graphics g) {
		BufferedImage img = covered;
		//decide the image based on CellStates
		switch (state) {
		case COVERED:
			img = covered;
			break;
		case FLAGGED:
			img = flag;
			break;
		case WRONG_FLAG:
			img = wrongFlag;
			break;
		case MINE:
			img = mine;
			break;
		case FIRED_MINE:
			img = bomb;
			break;
		case UNC0:
			img = uncovered[0];
			break;
		case UNC1:
			img = uncovered[1];
			break;
		case UNC2:
			img = uncovered[2];
			break;
		case UNC3:
			img = uncovered[3];
			break;
		case UNC4:
			img = uncovered[4];
			break;
		case UNC5:
			img = uncovered[5];
			break;
		case UNC6:
			img = uncovered[6];
			break;
		case UNC7:
			img = uncovered[7];
			break;
		case UNC8:
			img = uncovered[8];
			break;

		}
		//draw image
		g.drawImage(img, spriteWidth * col, spriteWidth * row, null);
	}

}
