package framework;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * Static class to modify Images with various effects
 */
public final class ImageFilter {
	/**
	 * Adds an outline of pixels around any shapes in the image
	 * @param img Image to filter
	 * @param color Color of outline
	 */
	public static void outLine(Image img, Color color)
	{
		ArrayList<Point2D> edges = new ArrayList<Point2D>();
		BufferedImage image = img.getImage();
		for (int i = 0; i < image.getWidth(); i++)
		{
			for (int j = 0; j < image.getHeight(); j++)
			{
				if (image.getRGB(i, j) == Color.TRANSLUCENT)
				{
					if (i > 0 && i < image.getWidth() - 1 && j > 0 && j < image.getHeight() - 1) // Don't check this on the edges
					{
						if (image.getRGB(i - 1, j) != Color.TRANSLUCENT
								|| image.getRGB(i + 1, j) != Color.TRANSLUCENT)
						{
							edges.add(new Point2D.Double(i, j));
						}
					}
				}
			}
		}
		for (int i = 0; i < edges.size(); i++)
		{
			image.setRGB((int)edges.get(i).getX(), (int)edges.get(i).getY(), color.getRGB());
		}
	}
	
	/**
	 * Adds pixels around the edges of the shape in the image to create a blur effect
	 * @param img Image to blur
	 */
	public static void blurEdges(Image img)
	{
		ArrayList<Point2D> edges = new ArrayList<Point2D>();
		BufferedImage image = img.getImage();
		for (int i = 0; i < image.getWidth(); i++)
		{
			for (int j = 0; j < image.getHeight(); j++)
			{
				if (image.getRGB(i, j) == Color.TRANSLUCENT)
				{
					if (i > 0 && i < image.getWidth() - 1 && j > 0 && j < image.getHeight() - 1) // Don't check this on the edges
					{
						if (image.getRGB(i - 1, j) != Color.TRANSLUCENT
								|| image.getRGB(i + 1, j) != Color.TRANSLUCENT)
						{
							edges.add(new Point2D.Double(i, j));
						}
					}
				}
			}
		}
		
		for (int i = 0; i < edges.size(); i++)
		{
			long avgR, totR = 0, avgB, totB = 0, avgG, totG = 0, avgA, totA = 0;
			int count = 0;
			Point2D point = edges.get(i);
			Color color = new Color(image.getRGB((int)point.getX() - 1, (int)point.getY()), true);
			if (color.getAlpha() > 0)
			{
				totR += color.getRed();
				totG += color.getGreen();
				totB += color.getBlue();
				totA += color.getAlpha();
				count++;
			}
			color = new Color(image.getRGB((int)point.getX() + 1, (int)point.getY()), true);
			if (color.getAlpha() > 0)
			{
				totR += color.getRed();
				totG += color.getGreen();
				totB += color.getBlue();
				totA += color.getAlpha();
				count++;
			}
			color = new Color(image.getRGB((int)point.getX(), (int)point.getY() - 1), true);
			if (color.getAlpha() > 0)
			{
				totR += color.getRed();
				totG += color.getGreen();
				totB += color.getBlue();
				totA += color.getAlpha();
				count++;
			}
			color = new Color(image.getRGB((int)point.getX(), (int)point.getY() + 1), true);
			if (color.getAlpha() > 0)
			{
				totR += color.getRed();
				totG += color.getGreen();
				totB += color.getBlue();
				totA += color.getAlpha();
				count++;
			}
			avgR = totR / count;
			avgG = totG / count;
			avgB = totB / count;
			avgA = totA / count;
			image.setRGB((int)point.getX(), (int)point.getY(), new Color((int)avgR / 2, (int)avgG / 2, (int)avgB / 2, (int)avgA / 2).getRGB());
		}
	}

	public static void hair(Image img, Color color, double freq, double length, double sway, int lengthVar)
	{
		BufferedImage image = img.getImage();
		ArrayList<Point2D> hairPoints = new ArrayList<Point2D>();
		Random rand = new Random();
		Graphics2D g2d = (Graphics2D)image.getGraphics();
		g2d.setColor(color);
		for (int i = 0; i < image.getWidth(); i++)
		{
			for (int j = 0; j < image.getHeight(); j++)
			{
				if (image.getRGB(i, j) != Color.TRANSLUCENT)
				{
					float randNum = rand.nextFloat();
					if (randNum < freq) 
					{
						hairPoints.add(new Point2D.Float(i, j));
					}
				}
			}
		}
		for (int i = 0; i < hairPoints.size(); i++)
		{
			Point2D point = hairPoints.get(i); 
			double randLength = length + rand.nextInt(lengthVar);
			QuadCurve2D curve = new QuadCurve2D.Double(point.getX(), point.getY(), point.getX() + (sway / 4), point.getY() + (randLength / 2), 
					point.getX() + sway, point.getY() + randLength);
			g2d.draw(curve);
		}
	}
	public static void hair(Image img, Color color, double freq, double length, double sway)
	{
		hair(img, color, freq, length, sway, 1);
	}
			
	/**
	 * 
	 * @param img
	 * @param count
	 */
	public static void basicVariance(Image img, int varAmount)
	{
		BufferedImage image = img.getImage();
		Random rand = new Random();
		for (int x = 0; x < image.getWidth(); x++)
		{
			for (int y = 0; y < image.getHeight(); y++)
			{
				Color color = new Color(image.getRGB(x, y), true); 
				int red = color.getRed() + (rand.nextInt(varAmount - (varAmount / 2)));
				red = Utils.makeInRange(red, 0, 255);
				int blue = color.getBlue() + (rand.nextInt(varAmount - (varAmount / 2)));
				blue = Utils.makeInRange(blue, 0, 255);
				int green = color.getGreen() + (rand.nextInt(varAmount - (varAmount / 2))); 
				green = Utils.makeInRange(green, 0, 255);
				color = new Color(red, green, blue, color.getAlpha());
				image.setRGB(x, y, color.getRGB());
			}
		}
	}
	
	public enum ShadeDir {
		RIGHT,
		LEFT,
		TOP,
		BOTTOM,
	}
	public static void lightenFrom(Image img, ShadeDir dir, double varAmount)
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
	
	public static void darkenFrom(Image img, ShadeDir dir, double varAmount)
	{
		BufferedImage image = img.getImage();
		int colorIncrease = 0; 
		for (int x = 0; x < image.getWidth(); x++)
		{
			for (int y = 0; y < image.getHeight(); y++)
			{
				if (dir == ShadeDir.RIGHT)
				{
					colorIncrease = (int) (x * -varAmount);
				}
				else if (dir == ShadeDir.LEFT)
				{
					colorIncrease = (int) ((image.getWidth() - x) * -varAmount);
				}
				else if (dir == ShadeDir.TOP)
				{
					colorIncrease = (int) ((image.getHeight() - y) * -varAmount);
				}
				else if (dir == ShadeDir.BOTTOM)
				{
					colorIncrease = (int) (y * -varAmount);
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