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
}