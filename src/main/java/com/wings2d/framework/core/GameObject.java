package com.wings2d.framework.core;

import java.awt.Graphics2D;

/**
 * Abstract base class for all game entities
 */
public abstract class GameObject {
	/**
	 * Update the GameObject. Does nothing unless overridden
	 * @param keys {@link com.wings2d.framework.input.KeyState KeyState} object containing the current keyboard state
	 */
	public void update(double dt)
	{ /* Do nothing by default */ }
	
	/**
	 * For drawing the object. Does nothing unless overridden
	 * @param g2d {@link java.awt.Graphics2D Graphics2D} object to draw with
	 * @param debug Control debug prints
	 */
	public void render(Graphics2D g2d, boolean debug)
	{ /* Do nothing by default */ }
	/** Change the object's scale */
	public abstract void rescale();
}
