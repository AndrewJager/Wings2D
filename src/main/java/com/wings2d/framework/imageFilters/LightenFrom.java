package com.wings2d.framework.imageFilters;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.wings2d.framework.Utils;

/**
 * Lighten the RGB values of the pixels in the image, in an increasing amount from the given direction.
 * @see DarkenFrom
 */
public class LightenFrom implements ImageFilter, ShadeFrom {
	/** {@link com.wings2d.framework.imageFilters.ShadeDir ShadeDir} - Direction from which to shade the image. Lighter in the indicated direction **/
	private ShadeDir dir;
	/** Amount in which to lighten the pixels **/
	private double varAmount;
	
	/**
	 * @param dir {@link com.wings2d.framework.imageFilters.ShadeDir ShadeDir} - Direction to use when lightening the pixels
	 * @param varAmount Amount to lighten the pixels
	 */
	public LightenFrom(ShadeDir dir, double varAmount)
	{
		this.dir = dir;
		this.varAmount = varAmount;
	}
	
	public LightenFrom(final String fileString)
	{
		String[] tokens = fileString.split(ImageFilter.FILTER_TOKEN);
		this.dir = ShadeDir.createFromString(tokens[0]);
		this.varAmount = Double.parseDouble(tokens[1]);
	}
	
	public String getFilterName()
	{
		return "Lighten From";
	}
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
		return LightenFrom.class.getSimpleName() + FilterFactory.FILTER_NAME_TOKEN + dir + ImageFilter.FILTER_TOKEN + varAmount;
	}
	public String toString()
	{
		return ShadeDir.getAsString(dir) + " " + varAmount;
	}
	public void filter(BufferedImage image)
	{
		int colorIncrease = 0; 
		for (int x = 0; x < image.getWidth(); x++)
		{
			for (int y = 0; y < image.getHeight(); y++)
			{
				if (dir == ShadeDir.RIGHT)
				{
					colorIncrease = (int) (x * varAmount);
				}
				else if (dir == ShadeDir.LEFT)
				{
					colorIncrease = (int) ((image.getWidth() - x) * varAmount);
				}
				else if (dir == ShadeDir.TOP)
				{
					colorIncrease = (int) ((image.getHeight() - y) * varAmount);
				}
				else if (dir == ShadeDir.BOTTOM)
				{
					colorIncrease = (int) (y * varAmount);
				}
				
				Color color = new Color(image.getRGB(x, y), true); 
				int red = color.getRed() + colorIncrease;
				red = Utils.makeInRange(red, 0, 255);
				int blue = color.getBlue() + colorIncrease;
				blue = Utils.makeInRange(blue, 0, 255);
				int green = color.getGreen() + colorIncrease; 
				green = Utils.makeInRange(green, 0, 255);
				color = new Color(red, green, blue, color.getAlpha());
				image.setRGB(x, y, color.getRGB());
			}
		}
	}
}
