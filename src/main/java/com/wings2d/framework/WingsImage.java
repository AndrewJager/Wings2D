package com.wings2d.framework;

import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

public class WingsImage extends BufferedImage{
	/**
	 * Represents a side of an image
	 */
	public enum ImageSide {
		RIGHT,
		LEFT,
		TOP,
		BOTTOM,
	}

	public WingsImage(final int width, final int height, final int imageType) {
		super(width, height, imageType);
	}

	public void expandImageOnSide(ImageSide side)
	{
		BufferedImage newImg;
		Graphics2D g2d;
		switch(side)
		{
		case RIGHT:
			newImg = new BufferedImage(this.getWidth() + 1, this.getHeight(), BufferedImage.TYPE_INT_ARGB);
			g2d = newImg.createGraphics();
			clearImage(newImg);
			g2d.drawImage(this, null, 0, 0);
			this.setData(newImg.getData());
			break;
		case LEFT:
			newImg = new BufferedImage(this.getWidth() + 1, this.getHeight(), BufferedImage.TYPE_INT_ARGB);
			g2d = newImg.createGraphics();
			clearImage(newImg);
			g2d.drawImage(this, null, 1, 0);
			this.setData(newImg.getData());
			break;
		case TOP:
			newImg = new BufferedImage(this.getWidth(), this.getHeight() + 1, BufferedImage.TYPE_INT_ARGB);
			g2d = newImg.createGraphics();			
			clearImage(newImg);
			g2d.drawImage(this, null, 0, 1);
			this.setData(newImg.getData());
			break;
		case BOTTOM:
			newImg = new BufferedImage(this.getWidth(), this.getHeight() + 1, BufferedImage.TYPE_INT_ARGB);
			g2d = newImg.createGraphics();
			clearImage(newImg);
			g2d.drawImage(this, null, 0, 0);
			this.setData(newImg.getData());
			break;
		}
	}
	
	public static void clearImage(BufferedImage img)
	{
		for (int x = 0; x < img.getWidth(); x++) {
		    for (int y = 0; y < img.getHeight(); y++) {
		    	img.setRGB(x, y, Transparency.TRANSLUCENT);
		    }
		}
	}
}
