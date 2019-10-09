package framework;

import java.awt.Graphics2D;

import entities.KeyState;

/**
 * Base class for all game entities
 */
public abstract class GameObject {
	protected int x, y;
	
	public abstract void update(KeyState keys);
	public abstract void render(Graphics2D g2d, boolean debug);
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
