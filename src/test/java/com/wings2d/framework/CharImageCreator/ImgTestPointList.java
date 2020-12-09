package com.wings2d.framework.CharImageCreator;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ImgTestPointList {
	private List<ImgTestPoint> points;
	
	public ImgTestPointList(final ImgTestPoint...testPoints)
	{
		points = new ArrayList<ImgTestPoint>();
		for(int i = 0; i < testPoints.length; i++)
		{
			points.add(testPoints[i]);
		}
	}
	public List<ImgTestPoint> getPoints()
	{
		return points;
	}
	public ImgTestPoint[] getPointsArray()
	{
		ImgTestPoint[] arr = new ImgTestPoint[points.size()];
		for (int i = 0; i < points.size(); i++)
		{
			arr[i] = points.get(i);
		}
		return arr;
	}
	public int getPointCount()
	{
		return points.size();
	}
	
	public ImgTestPoint[] getPointColors(final BufferedImage img)
	{
		ImgTestPoint[] imgPoints = new ImgTestPoint[this.getPointCount()];
		for (int i = 0; i < this.getPointCount(); i++)
		{
			ImgTestPoint point = this.getPoints().get(i);
			ImgTestColor pixelColor = new ImgTestColor(img.getRGB(point.getX(), point.getY()), true);
			imgPoints[i] = new ImgTestPoint(point.getX(), point.getY(), pixelColor);
		}
		
		return imgPoints;
	}
	
	public void addPaddingPixels(final BufferedImage img, final int padding, final ImgTestColor backgroundColor)
	{
		for (int x = 0; x < img.getWidth(); x++)
		{
			for (int y = 0; y < img.getHeight(); y++)
			{
				if ((x < padding || x > (img.getWidth() - padding - 1))
						|| (y < padding || y > (img.getHeight() - padding - 1)))
				{
					this.getPoints().add(new ImgTestPoint(x, y, backgroundColor));
				}
			}
		}
	}
}
