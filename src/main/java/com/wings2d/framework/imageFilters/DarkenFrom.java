package com.wings2d.framework.imageFilters;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.wings2d.framework.Utils;

/**
 * Darken the RGB values of the pixels in the image, in an increasing amount from the given direction.
 * @see LightenFrom
 */
public class DarkenFrom implements ImageFilter, ShadeFrom {
	/** {@link com.wings2d.framework.imageFilters.ShadeDir ShadeDir} - Direction from which to shade the image. Darker in the indicated direction **/
	private ShadeDir dir;
	/** Amount in which to darken the pixels **/
	private double varAmount;
	
	/**
	 * @param dir {@link com.wings2d.framework.imageFilters.ShadeDir ShadeDir} - Direction to use when darkening the pixels
	 * @param varAmount Amount to darken the pixels
	 */
	public DarkenFrom(ShadeDir dir, double varAmount)
	{
		this.dir = dir;
		this.varAmount = varAmount;
	}
	
	public DarkenFrom(final String fileString)
	{
		String[] tokens = fileString.split(ImageFilter.DELIMITER);
		this.dir = ShadeDir.createFromString(tokens[0]);
		this.varAmount = Double.parseDouble(tokens[1]);
	}
	public String getFilterName()
	{
		return "Darken From";
	}
	/**
	 * Get the shade direction
	 * @return {@link com.wings2d.framework.imageFilters.ShadeDir ShadeDir} to shade from
	 */
	public ShadeDir getDirection()
	{
		return dir;
	}
	public double getAmt()
	{
		return varAmount;
	}
	public String getFileString()
	{
		return DarkenFrom.class.getSimpleName() + FilterFactory.FILTER_TOKEN + dir + ImageFilter.DELIMITER + varAmount;
	}
	public String toString()
	{
		return ShadeDir.getAsString(dir) + " - " + varAmount;
	}
	public void filter(BufferedImage img)
	{
		int colorIncrease = 0; 
		for (int x = 0; x < img.getWidth(); x++)
		{
			for (int y = 0; y < img.getHeight(); y++)
			{
				if (dir == ShadeDir.RIGHT)
				{
					colorIncrease = (int) (x * -varAmount);
				}
				else if (dir == ShadeDir.LEFT)
				{
					colorIncrease = (int) ((img.getWidth() - x) * -varAmount);
				}
				else if (dir == ShadeDir.TOP)
				{
					colorIncrease = (int) ((img.getHeight() - y) * -varAmount);
				}
				else if (dir == ShadeDir.BOTTOM)
				{
					colorIncrease = (int) (y * -varAmount);
				}
				
				Color color = new Color(img.getRGB(x, y), true); 
				int red = color.getRed() + colorIncrease;
				red = Utils.makeInRange(red, 0, 255);
				int blue = color.getBlue() + colorIncrease;
				blue = Utils.makeInRange(blue, 0, 255);
				int green = color.getGreen() + colorIncrease; 
				green = Utils.makeInRange(green, 0, 255);
				color = new Color(red, green, blue, color.getAlpha());
				img.setRGB(x, y, color.getRGB());
			}
		}
	}
}
