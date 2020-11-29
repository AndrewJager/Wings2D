package com.wings2d.framework;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.awt.image.BaseMultiResolutionImage;
import java.awt.image.BufferedImage;

/**
 * Creates a {@link java.awt.image.BufferedImage BufferedImage} of a single char
 */
public class CharImageCreator {
	public static class ImageOptions {
		public double scales[];
		public int baseSize;
		public int padding;
		public Color color;
		
		public ImageOptions()
		{
			scales = new double[] {1.0, 1.25, 1.5};
			baseSize = 10;
			padding = 2;
			color = Color.BLACK;
		}
		
		public ImageOptions(final int baseSize, final int padding)
		{
			this();
			this.baseSize = baseSize;
			this.padding = padding;
		}
		
		public ImageOptions(final int baseSize, final double[] scales, final int padding)
		{
			this();
			this.baseSize = baseSize;
			this.scales = scales;
			this.padding = padding;
		}
	}
	
	public static BaseMultiResolutionImage CreateMultiImage(final char character, final ImageOptions options)
	{
		BufferedImage[] imgs = new BufferedImage[options.scales.length];
		for (int i = 0; i < options.scales.length; i++)
		{
			imgs[i] = CharImageCreator.CreateImage(character, (int)Math.ceil(options.baseSize * options.scales[i]), options);
		}
		BaseMultiResolutionImage multiImg = new BaseMultiResolutionImage(imgs);
		return multiImg;
	}
	public static BufferedImage CreateImage(final char character, final int imgSize, final ImageOptions options)
	{
		BufferedImage img = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D)img.getGraphics();


		int charSize = imgSize - options.padding;
		Rectangle2D finalBounds = null;
		
		boolean sizeExceeded = false;
		int fontSize = 1;
		while (!sizeExceeded)
		{
			GlyphVector v = g2d.getFont().createGlyphVector(g2d.getFontRenderContext(), String.valueOf(character));
			Rectangle2D realBounds = v.getOutline().getBounds2D();
			double height = realBounds.getHeight();
			double width = realBounds.getWidth();
			
			g2d.getFontMetrics().stringWidth(String.valueOf(character));
			if (height > charSize || width > charSize)
			{
				sizeExceeded = true;
				finalBounds = realBounds;
			}
			else
			{
				fontSize++;
				g2d.setFont(g2d.getFont().deriveFont(Float.parseFloat(String.valueOf(fontSize))));
			}
		}
		
		double xLoc = -finalBounds.getX();
		double yLoc = -finalBounds.getY();
		int centeredXLoc = (int)Math.floor(xLoc + (imgSize / 2) - (finalBounds.getWidth() / 2));
		int centeredYLoc = (int)Math.floor(yLoc + (imgSize / 2) - (finalBounds.getHeight() / 2));

		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		g2d.setColor(options.color);
		g2d.drawString(String.valueOf(character), centeredXLoc, centeredYLoc);
		
		return img;
	}
}