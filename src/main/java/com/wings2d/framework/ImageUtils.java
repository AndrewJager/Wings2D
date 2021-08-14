package com.wings2d.framework;

import java.awt.Color;
import java.awt.Graphics2D;
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

	/**
	 * Add a row of pixels to an image
	 * @param img {@link java.awt.BufferedImage BufferedImage} to add pixels to
	 * @param side Add pixels to Top/Bottom/Left/Right of image
	 * @return Image with pixels added to side
	 */
	public static BufferedImage expandImageOnSide(final BufferedImage img, final ImageSide side)
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
	
	/**
	 * Calls expandImageOnSide for all four sides
	 * @param img {@link java.awt.BufferedImage BufferedImage} to add pixels to
	 * @return The image with pixels added to all sides
	 */
	public static BufferedImage expandImageOnAllSides(final BufferedImage img) {
		BufferedImage newImg = img;
		newImg = ImageUtils.expandImageOnSide(newImg, ImageSide.TOP);
		newImg = ImageUtils.expandImageOnSide(newImg, ImageSide.BOTTOM);
		newImg = ImageUtils.expandImageOnSide(newImg, ImageSide.LEFT);
		newImg = ImageUtils.expandImageOnSide(newImg, ImageSide.RIGHT);
		return newImg;
	}
	
	/**
	 * Set all pixels of the image to an {0, 0, 0, 0}
	 * @param img Image to clear
	 */
	public static void clearImage(final BufferedImage img)
	{
		for (int x = 0; x < img.getWidth(); x++) {
		    for (int y = 0; y < img.getHeight(); y++) {
		    	img.setRGB(x, y, new Color(0, 0, 0, 0).getRGB());
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
		    		img.setRGB(x, y, new Color(0, 0, 0, 0).getRGB());
		    	}
		    }
		}
	}
}
