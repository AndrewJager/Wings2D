package framework;

import java.awt.Graphics2D;
import java.awt.Point;

/** Abstract base class for UI elements */
public abstract class UIElement {
	/**
	 * Render the UI element
	 * @param mouseClick {@link java.awt.Point Point} where the mouse is click. Null if not clicked.
	 */
	public abstract void updateUI(Point mouseClick);
	public abstract void renderUI(Graphics2D g2d, boolean debug);
	public abstract void rescale();
}
