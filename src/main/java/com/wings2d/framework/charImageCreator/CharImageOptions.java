package com.wings2d.framework.charImageCreator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Dimension2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wings2d.framework.imageFilters.ImageFilter;

/**
 * Contains options used by {@link CharImageCreator} to control its behavior.
 */
public class CharImageOptions {
	/**
	 * Algorithm used to draw the character shape.
	 */
	public enum Algorithm {
		/** Call {@link java.awt.Graphics2D#fill Graphics2D.fill} with the character shape */
		GRAPHICS_FILL;
		
		public static void drawCharShape(final CharImageOptions options, final Shape charShape, final Graphics2D g2d)
		{
			switch (options.algorithm) {
				case GRAPHICS_FILL -> graphicsFill(charShape, g2d);
			}
		}
		
		private static void graphicsFill(final Shape charShape, final Graphics2D g2d)
		{
			g2d.fill(charShape);
		}
	}
	
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
	/**
	 * How to draw the character
	 */
	public Algorithm algorithm = DEFAULT_ALGORITHM;
	/**
	 * Can be set to use RenderingHints outside of the default
	 */
	public Map<?,?> hints = null;
	/**
	 * {@link ImageFilter ImageFilters} to run over the generated image
	 */
	public List<ImageFilter> filters;
	
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
	private static final Algorithm DEFAULT_ALGORITHM = Algorithm.GRAPHICS_FILL;
	
	/**
	 * Constructor with default values
	 */
	public CharImageOptions(){
		filters = new ArrayList<ImageFilter>();
	}	
	
	public CharImageOptions(final int baseSize, final int padding)
	{
		this();
		this.baseSize = baseSize;
		this.padding = padding;
	}	
	public CharImageOptions(final int baseSize, final double[] scales, final int padding)
	{
		this(baseSize, padding);
		this.scales = scales;
	}
	public
	CharImageOptions(final int baseSize, final double[] scales, final int padding, final Color color)
	{
		this(baseSize, scales, padding);
		this.color = color;
	}
}
