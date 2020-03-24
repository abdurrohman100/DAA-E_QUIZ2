package com.cma.minesweeper.assets;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * @author seijaku
 * Class BufferedIMageLoader
 * Memuat gambar dari system melalui path relatif yang diberikan dan mengembalikan sebuah Buffered Image
 *
 */

public class BufferedImageLoader {
	private BufferedImage image;
	public BufferedImage loadImage(String path) throws IOException{
		try {
			image = ImageIO.read(getClass().getResource(path));
			return image;			
		}catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
 
}