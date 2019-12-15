package imageFilters;

import java.awt.Color;
import java.awt.image.BufferedImage;

import framework.Image;
import framework.Utils;

public class LightenFrom implements ImageFilter{
	private ShadeDir dir;
	private double varAmount;
	
	public LightenFrom(ShadeDir dir, double varAmount)
	{
		this.dir = dir;
		this.varAmount = varAmount;
	}
	public void filter(Image img)
	{
		BufferedImage image = img.getImage();
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
