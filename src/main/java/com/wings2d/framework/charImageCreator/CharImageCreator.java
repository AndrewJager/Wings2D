package com.wings2d.framework.charImageCreator;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BaseMultiResolutionImage;
import java.awt.image.BufferedImage;

import com.wings2d.framework.ImageUtils;
import com.wings2d.framework.charImageCreator.CharImageOptions.Algorithm;
import com.wings2d.framework.shape.ShapeUtils;

/**
 * Creates a {@link java.awt.image.BufferedImage BufferedImage} of a single char
 */
public class CharImageCreator {
	public static BaseMultiResolutionImage CreateMultiImage(final char character, final CharImageOptions options)
	{
		BufferedImage[] imgs = new BufferedImage[options.scales.length];
		for (int i = 0; i < options.scales.length; i++)
		{
			imgs[i] = CharImageCreator.CreateImage(character, (int)Math.ceil(options.baseSize * options.scales[i]), options);
		}
		BaseMultiResolutionImage multiImg = new BaseMultiResolutionImage(imgs);
		return multiImg;
	}
	public static BufferedImage CreateImage(final char character, final int imgSize, final CharImageOptions options)
	{
		validateOptions(options);
		BufferedImage img = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D)img.getGraphics();
		
		Rectangle2D maxBounds = getMaxBounds(options, imgSize);	

		Shape charShape = g2d.getFont().deriveFont(1.0f).createGlyphVector(g2d.getFontRenderContext(), String.valueOf(character)).getOutline();
		if (options.rotation != 0)
		{
			AffineTransform transform = new AffineTransform();
			transform.rotate(Math.toRadians(options.rotation), charShape.getBounds2D().getCenterX(), charShape.getBounds2D().getCenterY());
			charShape = transform.createTransformedShape(charShape);
		}
		
		if (options.maxSize != null)
		{
			AffineTransform transform = new AffineTransform();
			transform.rotate(Math.toRadians(-options.rotation), charShape.getBounds2D().getCenterX(), charShape.getBounds2D().getCenterY());
			charShape = transform.createTransformedShape(charShape);
			charShape = ShapeUtils.scaleToBounds(charShape, maxBounds);
			transform = new AffineTransform();
			transform.rotate(Math.toRadians(options.rotation), charShape.getBounds2D().getCenterX(), charShape.getBounds2D().getCenterY());
			charShape = transform.createTransformedShape(charShape);
		}
		else
		{
			charShape = ShapeUtils.scaleToBounds(charShape, maxBounds);
		}
		
		int centeredXLoc = getCenteredX(options, imgSize, charShape.getBounds2D());
		int centeredYLoc = getCenteredY(options, imgSize, charShape.getBounds2D());
		
		if (options.hints != null) {
			g2d.setRenderingHints(options.hints);
		}
		else {
			setDefaultRenderingHints(g2d);
		}
		
		if (options.backgroundColor.getAlpha() != 0)
		{
			g2d.setColor(options.backgroundColor);
			g2d.fillRect(0, 0, imgSize, imgSize);
		}
		g2d.setColor(options.color);
		g2d.translate(centeredXLoc, centeredYLoc);
		Algorithm.drawCharShape(options, charShape, g2d, img);
		
		// Add padding
		for (int i = 0; i < options.padding; i++) {
			img = ImageUtils.expandImageOnAllSides(img);
		}

		return img;
	}
	
	private static void validateOptions(final CharImageOptions options)
	{
		if (options.maxSize != null)
		{
			if ((options.maxSize.getWidth() > (options.baseSize))
					|| options.maxSize.getHeight() > (options.baseSize))
			{
				throw new IllegalArgumentException("Max Size option is larger than the base size!");
			}
		}
	}
	
	private static Rectangle2D getMaxBounds(final CharImageOptions options, final int imgSize)
	{
		Rectangle2D maxBounds = null;
		if (options.maxSize != null)
		{
			maxBounds = new Rectangle2D.Double(0, 0, options.maxSize.getWidth(),
				options.maxSize.getHeight());
		}
		else
		{
			maxBounds = new Rectangle2D.Double(0, 0, imgSize,
					imgSize);
		}
		return maxBounds;
	}
	
	private static int getCenteredX(final CharImageOptions options, final int imgSize, final Rectangle2D finalBounds)
	{
		int centeredXLoc = 0;
		double xLoc = -finalBounds.getX();
		if (options.alignLeft)
		{
			centeredXLoc = (int)Math.floor(xLoc + (imgSize / 2) - (finalBounds.getWidth() / 2));
		}
		else
		{
			centeredXLoc = (int)Math.ceil(xLoc + (imgSize / 2) - (finalBounds.getWidth() / 2));
		}
		return centeredXLoc;
	}
	
	private static int getCenteredY(final CharImageOptions options, final int imgSize, final Rectangle2D finalBounds)
	{
		int centeredYLoc = 0;
		double yLoc = -finalBounds.getY();
		if (options.alignTop)
		{
			centeredYLoc = (int)Math.floor(yLoc + (imgSize / 2) - (finalBounds.getHeight() / 2));
		}
		else
		{
			centeredYLoc = (int)Math.ceil(yLoc + (imgSize / 2) - (finalBounds.getHeight() / 2));
		}
		return centeredYLoc;
	}
	
	private static void setDefaultRenderingHints(final Graphics2D g2d)
	{
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
}