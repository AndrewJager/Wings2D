package framework;

import java.awt.Graphics2D;

import entities.KeyState;

/**
 * Abstract base class for all game entities
 */
public abstract class GameObject {
	public abstract void update(KeyState keys);
	public abstract void render(Graphics2D g2d, boolean debug);
	public abstract void translate(double xVel, double yVel);
	public abstract double getX();
	public abstract double getY();
}
