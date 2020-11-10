package com.wings2d.framework;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

public class ImageUtils{
	/**
	 * Represents a side of an image
	 */
	public enum ImageSide {
		RIGHT,
		LEFT,
		TOP,
		BOTTOM,
	}

	public static BufferedImage expandImageOnSide(BufferedImage img, ImageSide side)
	{
		BufferedImage newImg = null;
		Graphics2D g2d;
		switch(side)
		{
		case RIGHT:
			newImg = new BufferedImage(img.getWidth() + 1, img.getHeight(), BufferedImage.TYPE_INT_ARGB);
			g2d = newImg.createGraphics();
			clearImage(newImg);
			g2d.drawImage(img, null, 0, 0);
			break;
		case LEFT:
			newImg = new BufferedImage(img.getWidth() + 1, img.getHeight(), BufferedImage.TYPE_INT_ARGB);
			g2d = newImg.createGraphics();
			clearImage(newImg);
			g2d.drawImage(img, null, 1, 0);
			break;
		case TOP:
			newImg = new BufferedImage(img.getWidth(), img.getHeight() + 1, BufferedImage.TYPE_INT_ARGB);
			g2d = newImg.createGraphics();			
			clearImage(newImg);
			g2d.drawImage(img, null, 0, 1);
			break;
		case BOTTOM:
			newImg = new BufferedImage(img.getWidth(), img.getHeight() + 1, BufferedImage.TYPE_INT_ARGB);
			g2d = newImg.createGraphics();
			clearImage(newImg);
			g2d.drawImage(img, null, 0, 0);		
			break;
		}
		return newImg;
	}
	
	public static void clearImage(BufferedImage img)
	{
		for (int x = 0; x < img.getWidth(); x++) {
		    for (int y = 0; y < img.getHeight(); y++) {
		    	img.setRGB(x, y, Transparency.TRANSLUCENT);
		    }
		}
	}
	public static void cleanImageBackground(BufferedImage img)
	{
		for (int x = 0; x < img.getWidth(); x++) {
		    for (int y = 0; y < img.getHeight(); y++) {
		    	Color pixelColor = new Color(img.getRGB(x, y), true);
		    	if (pixelColor.getAlpha() == 0)
		    	{
		    		img.setRGB(x, y, Transparency.TRANSLUCENT);
		    	}
		    }
		}
	}
}
