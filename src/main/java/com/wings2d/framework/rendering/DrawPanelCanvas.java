package com.wings2d.framework.rendering;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;

public class DrawPanelCanvas extends DrawPanel{
	/** {@link java.awt.Canvas Canvas} to draw with */
	private Canvas canvas;
	
	/**
	 *	Initialize the canvas. Background color is set to DARK_GRAY. 
	 */
	public DrawPanelCanvas()
	{
        canvas = new Canvas();
        canvas.setBackground(Color.DARK_GRAY);
	}
	
	/**
	 * Get the canvas
	 * @return The {@link java.awt.Canvas Canvas} object used by this object
	 */
	public Canvas getCanvas()
	{
		return canvas;
	}
	
	@Override
	protected void setSize(final Dimension dim) {
		canvas.setSize(dim);
	}

	@Override
	public int getWidth() {
		return canvas.getWidth();
	}

	@Override
	public int getHeight() {
		return canvas.getHeight();
	}

	@Override
	protected void setLocation(final int xPos, final int yPos) {
		canvas.setLocation(xPos, yPos);
	}
}
