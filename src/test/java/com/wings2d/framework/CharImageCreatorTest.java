package com.wings2d.framework;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;

import com.wings2d.framework.CharImageCreator.CharImageOptions;

@ExtendWith(CharImageCreatorTestWatcher.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CharImageCreatorTest {
	private Map<String, BufferedImage> generatedImgs;
	private Map<String, BufferedImage> errorImgs;
	
	public CharImageCreatorTest()
	{
		generatedImgs = new HashMap<String, BufferedImage>();
		errorImgs = new HashMap<String, BufferedImage>();
	}

	public Map<String, BufferedImage> getGeneratedImages() {
		return generatedImgs;
	}
	public Map<String, BufferedImage> getErrorImages() {
		return errorImgs;
	}
	private void logImg(final String methodName, final BufferedImage img) {
		generatedImgs.put(methodName, img);
	}
	
	private static class TestColor extends Color
	{
		public TestColor(int r, int g, int b, int a) {
			super(r, g, b, a);
		}
		public TestColor(int rgba, boolean hasalpha) {
			super(rgba, hasalpha);
		}
		public TestColor(Color c){
			super(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
		}
		
		@Override
		public String toString()
		{
			return "{" + this.getRed() + ", " + this.getGreen() + ", " + this.getBlue() + ", " + this.getAlpha() +"}";
		}
	}
	private static class TestPoint
	{
		private int x;
		private int y;
		private TestColor color;
		
		TestPoint(final int x, final int y, final TestColor color)
		{
			this.x = x;
			this.y = y;
			this.color = color;
		}
		
		public int getX()
		{
			return x;
		}
		public int getY()
		{
			return y;
		}
		
		@Override
		public String toString()
		{
			return x + ", " + y + ", {" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ", " + color.getAlpha() + "}";
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((color == null) ? 0 : color.hashCode());
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TestPoint other = (TestPoint) obj;
			if (color == null) {
				if (other.color != null)
					return false;
			} else if (!color.equals(other.color))
				return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
	}
	
	private static class TestPointList
	{
		private List<TestPoint> points;
		
		public TestPointList(TestPoint...testPoints)
		{
			points = new ArrayList<TestPoint>();
			for(int i = 0; i < testPoints.length; i++)
			{
				points.add(testPoints[i]);
			}
		}
		public List<TestPoint> getPoints()
		{
			return points;
		}
		public TestPoint[] getPointsArray()
		{
			TestPoint[] arr = new TestPoint[points.size()];
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
	}
	
	private TestPoint[] getPointColors(final BufferedImage img, final TestPointList points)
	{
		TestPoint[] imgPoints = new TestPoint[points.getPointCount()];
		for (int i = 0; i < points.getPointCount(); i++)
		{
			TestPoint point = points.getPoints().get(i);
			TestColor pixelColor = new TestColor(img.getRGB(point.getX(), point.getY()), true);
			imgPoints[i] = new TestPoint(point.getX(), point.getY(), pixelColor);
		}
		
		return imgPoints;
	}
	
	private void addPaddingPixels(final BufferedImage img, final TestPointList points, final int padding, final TestColor backgroundColor)
	{
		for (int x = 0; x < img.getWidth(); x++)
		{
			for (int y = 0; y < img.getHeight(); y++)
			{
				if ((x < padding || x > (img.getWidth() - padding - 1))
						|| (y < padding || y > (img.getHeight() - padding - 1)))
				{
					points.getPoints().add(new TestPoint(x, y, backgroundColor));
				}
			}
		}
	}
	
	
	@BeforeAll
	static void setUp() throws Exception {
		// Delete all files in output folder
		File outputFolder = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\CharImageCreator");
		for(File f: outputFolder.listFiles()) { 
			  f.delete(); 
		}
	}
	@AfterAll
	void saveErrorImgs() {
		for (Map.Entry<String, BufferedImage> entry : errorImgs.entrySet()) {
		    String methodName = entry.getKey();
		    BufferedImage img = entry.getValue();
			File outputFile = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\CharImageCreator\\" + methodName + ".png");
			try {
				outputFile.createNewFile();
				ImageIO.write(img, "png", outputFile); 
			} catch (IOException e) {}
		}
	}

	@Test
	void test() {
		CharImageOptions options = new CharImageOptions();
		options.scales = new double[] {1.0};
		BufferedImage img = CharImageCreator.CreateImage('-', 40, options);
		TestPointList testPoints = new TestPointList(
				new TestPoint(0, 0, new TestColor(0, 0, 0, 0)),
				new TestPoint(39, 0, new TestColor(0, 0, 0, 0)),
				new TestPoint(39, 39, new TestColor(0, 0, 0, 0)),
				new TestPoint(0, 39, new TestColor(0, 0, 0, 0))
				);
		addPaddingPixels(img, testPoints, options.padding, new TestColor(options.backgroundColor));
		logImg(new Throwable().getStackTrace()[0].getMethodName(), img);
		assertArrayEquals(testPoints.getPointsArray(), getPointColors(img, testPoints));
	}
}
