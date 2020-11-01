package com.wings2d.framework.imageFilters;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.wings2d.framework.Image;
import com.wings2d.framework.Utils;
import com.wings2d.framework.Image.ImageSide;

/**
 * Create an solid-color outline around the shape inside an Image. 
 */
public class Outline implements ImageFilter{
	/** Used when saving to a file */
	public final static String fileTitle = "Outline";
	/** Color of the outline **/
	private Color color;
	/** How many pixels thick the outline will be **/
	private int thickness;

	/**
	 * Constructor with both color and thickness given
	 * @param color
	 * @param thickness
	 */
	public Outline(Color color, int thickness)
	{
		this.color = color;
		this.thickness = thickness;
	}
	/**
	 * Defaults thickness to 1
	 * @param color Color to outline the shape with
	 */
	public Outline (Color color)
	{
		this(color, 1);
	}

	public String getFilterName()
	{
		return "Outline";
	}
	public Color getColor()
	{
		return color;
	}
	public String getFileString()
	{
		return fileTitle + ImageFilter.DELIMITER + Utils.colorToString(color, ",");
	}
	public String toString()
	{
		return "Outline - " + color.toString();
	}
	public void filter(Image img)
	{
		for(int runs = 0; runs < thickness; runs++)
		{
			// Ensure that there is room on all sides for the outline
			boolean imgClear = false;
			while (!imgClear)
			{
				imgClear = true;
				for (int i = 0; i < img.getImage().getWidth(); i++) // Top
				{
					if (img.getImage().getRGB(i, 0) != Color.TRANSLUCENT)
					{
						img.expandImageOnSide(ImageSide.TOP);
						imgClear = false;
						break;
					}
				}
				if (imgClear)
				{
					for (int i = 0; i < img.getImage().getWidth(); i++) // Bottom
					{
						if (img.getImage().getRGB(i, img.getImage().getHeight() - 1) != Color.TRANSLUCENT)
						{
							img.expandImageOnSide(ImageSide.BOTTOM);
							imgClear = false;
							break;
						}
					}
				}
				if (imgClear)
				{
					for (int i = 0; i < img.getImage().getHeight(); i++) // Left
					{
						if (img.getImage().getRGB(0, i) != Color.TRANSLUCENT)
						{
							img.expandImageOnSide(ImageSide.LEFT);
							imgClear = false;
							break;
						}
					}
				}
				if (imgClear)
				{
					for (int i = 0; i < img.getImage().getHeight(); i++) // Right
					{
						if (img.getImage().getRGB(img.getImage().getWidth() - 1, i) != Color.TRANSLUCENT)
						{
							img.expandImageOnSide(ImageSide.RIGHT);
							imgClear = false;
							break;
						}
					}
				}
			}
			// Find the edges of the shape
			ArrayList<Point2D> edges = new ArrayList<Point2D>();
			for (int x = 0; x < img.getImage().getWidth(); x++)
			{
				for (int y = 0; y < img.getImage().getHeight(); y++)
				{
					if (img.getImage().getRGB(x, y) == Color.TRANSLUCENT)
					{
						boolean addPixel = false;
	
						if (y != 0 && img.getImage().getRGB(x, y - 1) != Color.TRANSLUCENT)
						{
							addPixel = true;
						}
						else if (y != (img.getImage().getHeight() - 1) && img.getImage().getRGB(x, y + 1) != Color.TRANSLUCENT)
						{
							addPixel = true;
						}
						else if (x != 0 && img.getImage().getRGB(x - 1, y) != Color.TRANSLUCENT)
						{
							addPixel = true;
						}
						else if (x != (img.getImage().getWidth() - 1) && img.getImage().getRGB(x + 1, y) != Color.TRANSLUCENT)
						{
							addPixel = true;
						}						
	
						if (addPixel)
						{
							edges.add(new Point2D.Double(x, y));
						}	
					}
				}
			}
			
			// Special cases for corner pixels
			if (edges.contains(new Point2D.Double(1, 0)) && edges.contains(new Point2D.Double(0, 1))) // Top left
			{
				edges.add(new Point2D.Double(0, 0));
			}
			if (edges.contains(new Point2D.Double(img.getImage().getWidth() - 2, 0)) 
					&& edges.contains(new Point2D.Double(img.getImage().getWidth() - 1, 1))) // Top right
			{
				edges.add(new Point2D.Double(img.getImage().getWidth() - 1, 0));
			}
			if (edges.contains(new Point2D.Double(0, img.getImage().getHeight() - 2)) 
					&& edges.contains(new Point2D.Double(1, img.getImage().getHeight() - 1))) // Bottom left
			{
				edges.add(new Point2D.Double(0, img.getImage().getHeight() - 1));
			}
			if (edges.contains(new Point2D.Double(img.getImage().getWidth() - 1, img.getImage().getHeight() - 2)) 
					&& edges.contains(new Point2D.Double(img.getImage().getWidth() - 2, img.getImage().getHeight() - 1))) // Bottom right
			{
				edges.add(new Point2D.Double(img.getImage().getWidth() - 1, img.getImage().getHeight() - 1));
			}
	
			// Draw the color to the edges
			for (int i = 0; i < edges.size(); i++)
			{
				img.getImage().setRGB((int)edges.get(i).getX(), (int)edges.get(i).getY(), color.getRGB());
			}
		}
	}
}