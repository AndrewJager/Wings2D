package framework;

import java.awt.Graphics2D;

/**
 * Abstract base class for all game entities
 */
public abstract class GameObject {
	/**
	 * Update the GameObject
	 * @param keys {@link framework.KeyState KeyState} object containing the current keyboard state
	 */
	public abstract void update(KeyState keys);
	public abstract void render(Graphics2D g2d, boolean debug);
	/** Change the object's scale */
	public abstract void rescale();
	/** Check what the object should do in physics processing */
	public abstract PhysicsType getPhysicsType();
}
