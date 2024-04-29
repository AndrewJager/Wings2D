package com.wings2d.framework.imageFilters;

import java.awt.Color;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JSpinner;

import com.wings2d.framework.core.Wings2DUtils;

/**
 * Randomly modify the RGB values of the image pixels to create a distorted effect.
 */
public class BasicVariance implements ImageFilter{
	public static class BasicVarianceEdit extends FilterEdit<BasicVariance>{	
		private static final long serialVersionUID = 1L;
		private JSpinner spinner;
		
		public BasicVarianceEdit(final BasicVariance filter, final Frame owner) {
			super(filter, owner);
		}
		public BasicVarianceEdit(final Frame owner) {
			super(owner);
		}

		@Override
		public void setup() {
			spinner = new JSpinner();
			spinner.setValue(25);
			this.add(spinner);
		}
		@Override
		public void setValues(final BasicVariance filter) {
			spinner.setValue(filter.getVarAmt());
		}
		@Override
		public BasicVariance getFilter() {
			return new BasicVariance((int)spinner.getValue());
		}
	}
	
	/** Maximum amount to change the pixels (min is 0) **/
	private int varAmount;
	
	public BasicVariance()
	{
		this.varAmount = 25;
	}
	public BasicVariance(int varAmount)
	{
		this.varAmount = varAmount;
	}
	
	public BasicVariance(final String fileString)
	{
		this.varAmount = Integer.parseInt(fileString);
	}
	
	public String getFilterName()
	{
		return "Basic Variance";
	}
	public int getVarAmt()
	{
		return this.varAmount;
	}
	public String getFileString()
	{
		return BasicVariance.class.getSimpleName() + FilterFactory.FILTER_NAME_TOKEN + varAmount;
	}
	public String toString()
	{
		return "Amount: " + varAmount;
	}

	public static Class<? extends FilterEdit<? extends ImageFilter>> getEditClass() {
		return BasicVarianceEdit.class;
	}
	public void filter(BufferedImage img)
	{
		Random rand = new Random();
		for (int x = 0; x < img.getWidth(); x++)
		{
			for (int y = 0; y < img.getHeight(); y++)
			{
				Color color = new Color(img.getRGB(x, y), true);
				if (color.getRGB() != Color.TRANSLUCENT)
				{
					int red = color.getRed() + (rand.nextInt(varAmount - (varAmount / 2)));
					red = Wings2DUtils.makeInRange(red, 0, 255);
					int blue = color.getBlue() + (rand.nextInt(varAmount - (varAmount / 2)));
					blue = Wings2DUtils.makeInRange(blue, 0, 255);
					int green = color.getGreen() + (rand.nextInt(varAmount - (varAmount / 2))); 
					green = Wings2DUtils.makeInRange(green, 0, 255);
					color = new Color(red, green, blue, color.getAlpha());
					img.setRGB(x, y, color.getRGB());
				}
			}
		}
	}
}
