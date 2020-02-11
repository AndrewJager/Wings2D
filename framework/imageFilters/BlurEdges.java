package framework.imageFilters;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import framework.Image;

/**
 * Add some pixels around the edges of the shape to create a "blurred" effect.
 */
public class BlurEdges implements ImageFilter{
	/** Used when saving to a file */
	public final static String fileTitle = "BlurEdges";
	public String getFilterName()
	{
		return "Blur Edges";
	}
	public String toString()
	{
		return fileTitle;
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
			Point2D point = edges.get(i);
			Color color = new Color(image.getRGB((int)point.getX(), (int)point.getY()), true);
			color = new Color(color.getRed(), color.getBlue(), color.getGreen(), color.getAlpha() / 2);
			image.setRGB((int)point.getX(), (int)point.getY(), color.getRGB());
		}
	}
}
