package com.wings2d.framework.imageFilters;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

import com.wings2d.framework.Image;
import com.wings2d.framework.ShapeUtils;
import com.wings2d.framework.Utils;

/**
 * NOT CURRENTLY WORKING
 * "Lights" an image with a color, using a line as the source of the light
 */
public class LightFrom implements ImageFilter{
	/** Used when saving to a file */
	public final static String fileTitle = "LightFrom";
	private Line2D lightLine;
	private double lightAmt;
	private Color lightColor;
	
	public LightFrom(Line2D line, double amt, Color color)
	{
		lightLine = line;
		lightAmt = amt;
		lightColor = color;
	}
	public LightFrom(double x1, double y1, double x2, double y2, double amt, Color color)
	{
		lightLine = new Line2D.Double(x1, y1, x2, y2);
		lightAmt = amt;
		lightColor = color;
	}
	
	public String getFilterName()
	{
		return "Light From";
	}
	public Line2D getLine()
	{
		return lightLine;
	}
	public double getLightAmt()
	{
		return lightAmt;
	}
	public Color getColor()
	{
		return lightColor;
	}
	public String getFileString()
	{
		return "NOT WORKING";
	}
	public String toString()
	{
		return "NOT WORKING";
	}
	public void filter(Image img)
	{
		BufferedImage image = img.getImage();
		
		double minDistance = Double.MAX_VALUE;
		double maxDistance = 0;
		double height = image.getHeight();
		for (int x = 0; x < image.getWidth(); x++)
		{
			for (int y = 0; y < image.getHeight(); y++)
			{
				double distance = ShapeUtils.distanceFromLineToPoint(x, y, lightLine);
				if (distance < minDistance)
				{
					minDistance = distance;
				}
				if (distance > maxDistance)
				{
					maxDistance = distance;
				}
			}	
		}
		double midDistance = (maxDistance + minDistance) / 2;
		for (int x = 0; x < image.getWidth(); x++)
		{
			for (int y = 0; y < image.getHeight(); y++)
			{
				double distance = ShapeUtils.distanceFromLineToPoint(x, y, lightLine);
				double amtFromMid = midDistance - distance;
				double invertedDistance = distance + (amtFromMid * 2);
				double distPercent = invertedDistance / height;
				
				Color highlight = Utils.modifyColorByPercent(lightColor, distPercent);	
				image.setRGB(x, y, Utils.overlayColor(new Color(image.getRGB(x, y), true), highlight).getRGB());
			}
		}
	}
}
