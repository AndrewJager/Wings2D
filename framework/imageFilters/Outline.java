package framework.imageFilters;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import framework.Image;
import framework.Utils;

/**
 * Create an solid-color outline around the shape inside an Image. 
 */
public class Outline implements ImageFilter{
	/** Used when saving to a file */
	public final static String fileTitle = "Outline";
	/** Color of the outline **/
	private Color color;
	/**
	 * 
	 * @param color Color to outline the shape with
	 */
	public Outline (Color color)
	{
		this.color = color;
	}
	
	public String getFilterName()
	{
		return "Outline";
	}
	public Color getColor()
	{
		return color;
	}
	public String toString()
	{
		return fileTitle + ImageFilter.delimiter + Utils.colorToString(color, ",");
	}
	public String getFilterInfoString()
	{
		return color.toString();
	}
	public void filter(Image img)
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
}
