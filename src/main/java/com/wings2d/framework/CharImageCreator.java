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
	public static BaseMultiResolutionImage CreateMultiImage(final char character, double[] scales, final int baseSize, final int padding)
	{
		BufferedImage[] imgs = new BufferedImage[scales.length];
		for (int i = 0; i < scales.length; i++)
		{
			imgs[i] = CharImageCreator.CreateImage(character, (int)Math.ceil(baseSize * scales[i]), padding, true);
		}
		BaseMultiResolutionImage multiImg = new BaseMultiResolutionImage(imgs);
		return multiImg;
	}
	public static BufferedImage CreateImage(final char character, final int imgSize, final int padding, final boolean antiAlias)
	{
		BufferedImage img = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D)img.getGraphics();


		int charSize = imgSize - padding;
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
		
		if (antiAlias)
		{
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		}
		
		g2d.setColor(Color.BLACK);
		g2d.drawString(String.valueOf(character), centeredXLoc, centeredYLoc);
		
		return img;
	}
	
	public static BufferedImage CreateImage(final char character, final int imgSize)
	{
		return CreateImage(character, imgSize, 0, true);
	}
}
