package framework;

import java.awt.Graphics2D;

import entities.KeyState;

/**
 * Base class for all game entities
 */
public abstract class GameObject {
	public abstract void update(KeyState keys);
	public abstract void render(Graphics2D g2d, boolean debug);
}
