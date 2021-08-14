package com.wings2d.framework.CharImageCreator;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.wings2d.framework.CharImageCreatorTestWatcher;
import com.wings2d.framework.charImageCreator.CharImageCreator;
import com.wings2d.framework.charImageCreator.CharImageOptions;
import com.wings2d.framework.shape.ShapeUtils;

@ExtendWith(CharImageCreatorTestWatcher.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CharImageCreatorTest {
	private List<ImgWithInfo> generatedImgs;
	
	private List<ImgWithInfo> errorImgs;
	private Font testFont;
	private Graphics2D g2d;
	
	public static char[] getTestChars() {
		return new char[] {'-', '|', '<', '>', '\u2B1B', 'C', 'A', 'T'};
	}
	public static Color[] getTestColors() {
		return new Color[] {Color.BLACK, Color.BLUE, Color.WHITE, Color.YELLOW, new Color(255, 255, 255, 25)};
	}
	
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
	
	private double calcPercentTargetFilled(final char testChar, final int imgSize, final CharImageOptions options)
	{
		Rectangle2D imgRect = new Rectangle2D.Double(0, 0, imgSize - (options.padding * 2), imgSize - (options.padding * 2));
		Shape charShape = testFont.createGlyphVector(g2d.getFontRenderContext(), Character.toString(testChar)).getOutline();
		charShape = ShapeUtils.scaleToBounds(charShape, imgRect);
		double translateToX = (imgRect.getWidth() / 2) - (charShape.getBounds2D().getWidth() / 2);
		double translateToY = (imgRect.getHeight() / 2) - (charShape.getBounds2D().getHeight() / 2);
		AffineTransform transform = new AffineTransform();
		transform.translate(-charShape.getBounds2D().getX(), -charShape.getBounds2D().getY());
		transform.translate(translateToX, translateToY);
		charShape = transform.createTransformedShape(charShape);
		
		double step = 0.1;
		double pointsChecked = 0;
		double pointsContained = 0;
		for (double x = 0; x < imgRect.getWidth(); x += 0.1)
		{
			for (double y = 0; y < imgRect.getHeight(); y += step)
			{
				if (charShape.contains(x, y))
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
		if (!outputFolder.exists()) {
			Files.createDirectories(Path.of(outputFolder.getPath()));
		}
		
		if (outputFolder.listFiles() != null) {
			for(File f: outputFolder.listFiles()) { 
				  f.delete(); 
			}
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
		int imgSize = 10;
		CharImageOptions options = new CharImageOptions();
		options.scales = new double[] {1.0};
		BufferedImage img = CharImageCreator.CreateImage(testChar, imgSize, options);
		ImgTestPointList testPoints = new ImgTestPointList();
		testPoints.addPaddingPixels(img, options.padding, new ImgTestColor(options.backgroundColor));
		
		logImg(new ImgWithInfo(img, new Throwable().getStackTrace()[0].getMethodName(), testChar));
		assertArrayEquals(testPoints.getPointsArray(), testPoints.getPointColors(img));
	}
	
	@ParameterizedTest(name = "[{index}] '\u2B1B'")
	@MethodSource("getTestColors")
	void testBaseColor(final Color color) {
		int imgSize = 10;
		CharImageOptions options = new CharImageOptions();
		options.padding = 0;
		options.scales = new double[] {1.0};
		options.color = color;
		char testChar = '\u2B1B'; 
		BufferedImage img = CharImageCreator.CreateImage(testChar, imgSize, options); // Square
		logImg(new ImgWithInfo(img, new Throwable().getStackTrace()[0].getMethodName(), testChar));
		
		Color imgColor = new Color(img.getRGB(1, 1), true);
		assertEquals(color.getRGB(), imgColor.getRGB());
	}
	
	@Disabled("Broken when anti-aliasing was added")
	@ParameterizedTest
	@MethodSource("getTestChars")
	void testFillPercent(final char testChar)
	{
		int imgSize = 10;
		CharImageOptions options = new CharImageOptions();
		options.padding = 0;
		options.scales = new double[] {1.0};
		BufferedImage img = CharImageCreator.CreateImage(testChar, imgSize, options);
		
		logImg(new ImgWithInfo(img, new Throwable().getStackTrace()[0].getMethodName(), testChar));
		double targetPercentFilled = calcPercentTargetFilled(testChar, imgSize, options);
		double imgPercentFilled = calcPercentImgFilled(img, options);
		double allowedError = 0.1;
		assertEquals(targetPercentFilled, imgPercentFilled, allowedError);
	}
}
