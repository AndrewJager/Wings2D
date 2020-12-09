package com.wings2d.framework;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.wings2d.framework.CharImageCreator.CharImageOptions;

@ExtendWith(CharImageCreatorTestWatcher.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CharImageCreatorTest {
	private List<ImgWithInfo> generatedImgs;
	private List<ImgWithInfo> errorImgs;
	private Font testFont;
	private Graphics2D g2d;
	
	public CharImageCreatorTest()
	{
		generatedImgs = new ArrayList<ImgWithInfo>();
		errorImgs = new ArrayList<ImgWithInfo>();
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		g2d = env.createGraphics(new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB));
		testFont = g2d.getFont();
	}

	public List<ImgWithInfo> getGeneratedImages() {
		return generatedImgs;
	}
	public List<ImgWithInfo> getErrorImages() {
		return errorImgs;
	}
	private void logImg(final ImgWithInfo imgInfo) {
		generatedImgs.add(imgInfo);
	}
	public ImgWithInfo getImgInfoByInfo(final String methodName, final char character)
	{
		for (int i = 0; i < generatedImgs.size(); i++)
		{
			if (generatedImgs.get(i).getMethodName().equals(methodName)
					&& generatedImgs.get(i).getCharacter() == character)
			{
				return generatedImgs.get(i);
			}
		}
		return null;
	}
	public static char[] getTestChars() {
		return new char[] {'-', '|', '<', '>', '\u2B1B', 'M'};
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
		
		public TestPoint[] getPointColors(final BufferedImage img)
		{
			TestPoint[] imgPoints = new TestPoint[this.getPointCount()];
			for (int i = 0; i < this.getPointCount(); i++)
			{
				TestPoint point = this.getPoints().get(i);
				TestColor pixelColor = new TestColor(img.getRGB(point.getX(), point.getY()), true);
				imgPoints[i] = new TestPoint(point.getX(), point.getY(), pixelColor);
			}
			
			return imgPoints;
		}
		
		public void addPaddingPixels(final BufferedImage img, final int padding, final TestColor backgroundColor)
		{
			for (int x = 0; x < img.getWidth(); x++)
			{
				for (int y = 0; y < img.getHeight(); y++)
				{
					if ((x < padding || x > (img.getWidth() - padding - 1))
							|| (y < padding || y > (img.getHeight() - padding - 1)))
					{
						this.getPoints().add(new TestPoint(x, y, backgroundColor));
					}
				}
			}
		}
	}
	
	public static class ImgWithInfo
	{
		private BufferedImage img;
		private String methodName;
		private char character;
		
		public ImgWithInfo(final BufferedImage img, final String methodName, final char character)
		{
			this.img = img;
			this.methodName = methodName;
			this.character = character;
		}
		
		public BufferedImage getImage() {
			return img;
		}
		public String getMethodName() {
			return methodName;
		}
		public char getCharacter() {
			return character;
		}
	}
	
	private double calcPercentTargetFilled(final char testChar, final int imgSize, final CharImageOptions options)
	{
		Rectangle2D imgRect = new Rectangle2D.Double(0, 0, imgSize - (options.padding * 2), imgSize - (options.padding * 2));
		Shape charShape = testFont.createGlyphVector(g2d.getFontRenderContext(), Character.toString(testChar)).getOutline();
		charShape = CharImageCreator.scaleToBounds(charShape, imgRect);
		double translateToX = (imgRect.getWidth() / 2) - (charShape.getBounds2D().getWidth() / 2);
		double translateToY = (imgRect.getHeight() / 2) - (charShape.getBounds2D().getHeight() / 2);
		AffineTransform transform = new AffineTransform();
		transform.translate(-charShape.getBounds2D().getX(), -charShape.getBounds2D().getY());
		transform.translate(translateToX, translateToY);
		charShape = transform.createTransformedShape(charShape);
		
		Area area = new Area(charShape);
		double step = 0.1;
		double pointsChecked = 0;
		double pointsContained = 0;
		for (double x = 0; x < imgRect.getWidth(); x += 0.1)
		{
			for (double y = 0; y < imgRect.getHeight(); y += step)
			{
				if (area.contains(x, y))
				{
					pointsContained++;
				}
				pointsChecked++;
			}
		}
		return pointsContained / pointsChecked;
	}
	private double calcPercentImgFilled(final BufferedImage img, final CharImageOptions options)
	{
		double pointsChecked = 0;
		double pointsContained = 0;
		for (int x = options.padding; x < img.getWidth() - options.padding; x++)
		{
			for (int y = options.padding; y < img.getHeight() - options.padding; y++)
			{
				Color imgColor =  new Color(img.getRGB(x, y), true);
				if (imgColor.equals(options.color))
				{
					pointsContained++;
				}
				pointsChecked++;
			}
		}
		return pointsContained / pointsChecked;
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
		for (int i = 0; i < errorImgs.size(); i++)
		{
		    ImgWithInfo imgInfo = errorImgs.get(i);
			File outputFile = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\CharImageCreator\\" + imgInfo.getMethodName() 
					+ "-" + Character.getName(imgInfo.getCharacter()) + ".png");
			try {
				outputFile.createNewFile();
				ImageIO.write(imgInfo.getImage(), "png", outputFile); 
			} catch (IOException e) {}
		}
	}

	@ParameterizedTest
	@MethodSource("getTestChars")
	void testPadding(final char testChar) {
		int imgSize = 40;
		CharImageOptions options = new CharImageOptions();
		options.scales = new double[] {1.0};
		BufferedImage img = CharImageCreator.CreateImage(testChar, imgSize, options);
		TestPointList testPoints = new TestPointList();
		testPoints.addPaddingPixels(img, options.padding, new TestColor(options.backgroundColor));
		
		logImg(new ImgWithInfo(img, new Throwable().getStackTrace()[0].getMethodName(), testChar));
		assertArrayEquals(testPoints.getPointsArray(), testPoints.getPointColors(img));
	}
	
	@ParameterizedTest
	@MethodSource("getTestChars")
	void testFillPercent(final char testChar)
	{
		int imgSize = 40;
		CharImageOptions options = new CharImageOptions();
		options.scales = new double[] {1.0};
		BufferedImage img = CharImageCreator.CreateImage(testChar, imgSize, options);
		
		logImg(new ImgWithInfo(img, new Throwable().getStackTrace()[0].getMethodName(), testChar));
		double targetPercentFilled = calcPercentTargetFilled(testChar, imgSize, options);
		double imgPercentFilled = calcPercentImgFilled(img, options);
		double allowedError = 0.1;
		assertEquals(targetPercentFilled, imgPercentFilled, allowedError);
	}
}
