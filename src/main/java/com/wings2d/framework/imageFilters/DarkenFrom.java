package com.wings2d.framework.imageFilters;

import java.awt.Color;
import java.awt.Frame;
import java.awt.image.BufferedImage;

import com.wings2d.framework.core.Utils;
import com.wings2d.framework.misc.CardinalDir;

/**
 * Darken the RGB values of the pixels in the image, in an increasing amount from the given direction.
 * @see LightenFrom
 */
public class DarkenFrom implements ImageFilter, ShadeFrom {
	public static class DarkenFromEdit extends ShadeFromEdit<DarkenFrom>{
		private static final long serialVersionUID = 1L;
	
		public DarkenFromEdit(Frame owner) {
			super(owner, ShadeType.DARKEN);
		}
		
		public DarkenFromEdit(final DarkenFrom filter, final Frame owner) {
			super(filter, owner, ShadeType.DARKEN);
		}
	}


	/** {@link com.wings2d.framework.misc.CardinalDir CardinalDir} - Direction from which to shade the image. Darker in the indicated direction **/
	private CardinalDir dir;
	/** Amount in which to darken the pixels **/
	private double varAmount;
	
	/**
	 * @param dir {@link com.wings2d.framework.misc.CardinalDir CardinalDir} - Direction to use when darkening the pixels
	 * @param varAmount Amount to darken the pixels
	 */
	public DarkenFrom(CardinalDir dir, double varAmount)
	{
		this.dir = dir;
		this.varAmount = varAmount;
	}
	
	public DarkenFrom(final String fileString)
	{
		String[] tokens = fileString.split(ImageFilter.FILTER_TOKEN);
		this.dir = CardinalDir.createFromString(tokens[0]);
		this.varAmount = Double.parseDouble(tokens[1]);
	}
	public String getFilterName()
	{
		return "Darken From";
	}
	/**
	 * Get the shade direction
	 * @return {@link com.wings2d.framework.misc.CardinalDir ShadeDir} to shade from
	 */
	public CardinalDir getDirection()
	{
		return dir;
	}
	public double getAmt()
	{
		return varAmount;
	}
	public String getFileString()
	{
		return DarkenFrom.class.getSimpleName() + FilterFactory.FILTER_NAME_TOKEN + dir + ImageFilter.FILTER_TOKEN + varAmount;
	}
	public String toString()
	{
		return CardinalDir.getAsString(dir) + " - " + varAmount;
	}

	public static Class<? extends FilterEdit<? extends ImageFilter>> getEditClass() {
		return DarkenFromEdit.class;
	}
	public void filter(BufferedImage img)
	{
		int colorIncrease = 0; 
		for (int x = 0; x < img.getWidth(); x++)
		{
			for (int y = 0; y < img.getHeight(); y++)
			{
				if (dir == CardinalDir.EAST)
				{
					colorIncrease = (int) (x * -varAmount);
				}
				else if (dir == CardinalDir.WEST)
				{
					colorIncrease = (int) ((img.getWidth() - x) * -varAmount);
				}
				else if (dir == CardinalDir.NORTH)
				{
					colorIncrease = (int) ((img.getHeight() - y) * -varAmount);
				}
				else if (dir == CardinalDir.SOUTH)
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
