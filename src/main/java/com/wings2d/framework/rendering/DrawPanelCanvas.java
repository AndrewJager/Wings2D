package com.wings2d.framework.rendering;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import com.wings2d.framework.Game;

public class DrawPanelCanvas extends DrawPanel{
	/** {@link java.awt.Canvas Canvas} to draw with */
	private Canvas canvas;
	private BufferStrategy strat;
	/** Used internally by buffer */
	private Graphics2D graphics;
	
	/**
	 *	Initialize the canvas. Background color is set to DARK_GRAY. 
	 */
	public DrawPanelCanvas(final Game game)
	{
		super(game);
        canvas = new Canvas();
        canvas.setBackground(Color.DARK_GRAY);
	}
	
	/**
	 * Get the canvas
	 * @return The {@link java.awt.Canvas Canvas} object used by this object
	 */
	public Canvas getDrawComponent()
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

	@Override
	public Graphics2D getGraphics() {
		if (graphics == null) {
			try {
				graphics = (Graphics2D) strat.getDrawGraphics();
			} catch (IllegalStateException e) {
				return null;
			}
		}
		return graphics;
	}

	@Override
	public boolean afterRender() {
		graphics.dispose();
		graphics = null;
		try {
			strat.show();
			return (!strat.contentsLost());

		} catch (NullPointerException e) {
			return true;

		} catch (IllegalStateException e) {
			return true;
		}
	}

	@Override
	public void initGraphics() {
		canvas.createBufferStrategy(3);
		do {
			strat = canvas.getBufferStrategy();
		} while (strat == null);
	}

	@Override
	public void render() {
		game.render(getGraphics());
	}
}
