package com.wings2d.framework.core;

import java.awt.Graphics2D;

/**
 * Abstract base class for all game entities
 */
public interface GameObject {
	/**
	 * 
	 */
	public void update(double dt);
	
	/**
	 * Draw the object
	 * @param g2d {@link java.awt.Graphics2D Graphics2D} object to draw with
	 */
	public void render(Graphics2D g2d);
}
