package com.wings2d.framework;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BaseMultiResolutionImage;
import java.awt.image.BufferedImage;


/**
 * Creates a {@link java.awt.image.BufferedImage BufferedImage} of a single char
 */
public class CharImageCreator {
	/**
	 * Contains options used by {@link CharImageCreator} to control its behavior.
	 */
	public static class CharImageOptions {
		/** 
		 * Values used to determine image sizes when creating the MultiResolutionImage. <br> <br> 
		 * Defaults to {{@value #DEFAULT_SCALE_1}, {@value #DEFAULT_SCALE_2}, {@value #DEFAULT_SCALE_3}}.
		 **/
		public double scales[] = {DEFAULT_SCALE_1, DEFAULT_SCALE_2, DEFAULT_SCALE_3};
		/**
		 * Size of the image, including padding. Will be scaled by a value from the {@link #scales} array. <br> <br> 
		 * Defaults to {@value #DEFAULT_BASE_SIZE}.
		 */
		public int baseSize = DEFAULT_BASE_SIZE;
		/**
		 * Minimum number of empty pixels pixels on all sides of the image. There may be more empty space than this for multiple possible reasons. <br> <br> 
		 * Defaults to {@value #DEFAULT_PADDING}. 
		 */
		public int padding = DEFAULT_PADDING;
		/**
		 * {@link java.awt.color Color} to draw the character with. <br> <br> 
		 * Defaults to black.
		 */
		public Color color = DEFAULT_COLOR;
		/**
		 * {@link java.awt.color Color} to fill the background with. <br> <br> 
		 * Defaults to transparent.
		 */
		public Color backgroundColor = DEFAULT_BACKGROUND_COLOR;
		/**
		 * Value in degrees to rotate the character. <br> <br> 
		 * Defaults to {@value #DEFAULT_ROTATION}. 
		 */
		public double rotation = DEFAULT_ROTATION;
		/**
		 * Determines the alignment of the character along the x-axis of the image. Left if true, right if false. May have no effect if there is 
		 * no uncertain space inside the padding. <br> <br> 
		 * Defaults to {@value #DEFAULT_ALIGN_LEFT}.
		 */
		public boolean alignLeft = DEFAULT_ALIGN_LEFT;
		/**
		 * Determines the alignment of the character along the y-axis of the image. Top if true, bottom if false. May have no effect if there is 
		 * no uncertain space inside the padding. <br> <br> 
		 * Defaults to {@value #DEFAULT_ALIGN_TOP}.
		 */
		public boolean alignTop = DEFAULT_ALIGN_TOP;
		/**
		 * Can be used to prevent the character from becoming larger than the specified size. Useful if creating a series of images without the character
		 * changing size. May result in extra space around the character. The size is compared before the character is rotated.
		 * Defaults to null;
		 */
		public Dimension2D maxSize = null;
		
		private static final double DEFAULT_SCALE_1 = 1.0;
		private static final double DEFAULT_SCALE_2 = 1.25;
		private static final double DEFAULT_SCALE_3 = 1.50;
		private static final int DEFAULT_BASE_SIZE = 10;
		private static final int DEFAULT_PADDING = 2;
		private static final Color DEFAULT_COLOR = Color.BLACK;
		private static final Color DEFAULT_BACKGROUND_COLOR = new Color(0, 0, 0, 0); // Transparent
		private static final double DEFAULT_ROTATION = 0;
		private static final boolean DEFAULT_ALIGN_LEFT = true;
		private static final boolean DEFAULT_ALIGN_TOP = true;
		
		public CharImageOptions(){}	// Use all default values
		
		public CharImageOptions(final int baseSize, final int padding)
		{
			this.baseSize = baseSize;
			this.padding = padding;
		}	
		public CharImageOptions(final int baseSize, final double[] scales, final int padding)
		{
			this.baseSize = baseSize;
			this.scales = scales;
			this.padding = padding;
		}
		public CharImageOptions(final int baseSize, final double[] scales, final int padding, final Color color)
		{
			this.baseSize = baseSize;
			this.scales = scales;
			this.padding = padding;
			this.color = color;
		}
	}
	
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
			charShape = scaleToBounds(charShape, maxBounds);
			transform = new AffineTransform();
			transform.rotate(Math.toRadians(options.rotation), charShape.getBounds2D().getCenterX(), charShape.getBounds2D().getCenterY());
			charShape = transform.createTransformedShape(charShape);
		}
		else
		{
			charShape = scaleToBounds(charShape, maxBounds);
		}
		
		int centeredXLoc = getCenteredX(options, imgSize, charShape.getBounds2D());
		int centeredYLoc = getCenteredY(options, imgSize, charShape.getBounds2D());
		
		setRenderingHints(g2d);
		
		if (options.backgroundColor.getAlpha() != 0)
		{
			g2d.setColor(options.backgroundColor);
			g2d.fillRect(0, 0, imgSize, imgSize);
		}
		g2d.setColor(options.color);
		g2d.translate(centeredXLoc, centeredYLoc);
		g2d.fill(charShape);

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
			maxBounds = new Rectangle2D.Double(options.padding, options.padding, options.maxSize.getWidth(),
				options.maxSize.getHeight());
		}
		else
		{
			int charSize = imgSize - (options.padding * 2);
			maxBounds = new Rectangle2D.Double(options.padding, options.padding, charSize,
					charSize);
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
	
	private static void setRenderingHints(final Graphics2D g2d)
	{
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	}
	
	private static Shape scaleToBounds(final Shape charShape, final Rectangle2D bounds)
	{
		Rectangle2D charBounds = charShape.getBounds2D();
		double scaleToBounds = 0;
		if (charBounds.getHeight() > charBounds.getWidth())
		{
			scaleToBounds = bounds.getHeight() / charBounds.getHeight();
		}
		else
		{
			scaleToBounds = bounds.getWidth() / charBounds.getWidth();
		}

		AffineTransform transform = new AffineTransform();
		transform.scale(scaleToBounds, scaleToBounds);
		Shape scaledShape = transform.createTransformedShape(charShape);
		return scaledShape;
	}
}