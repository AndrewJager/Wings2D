package framework;

import java.awt.Graphics2D;
import java.awt.Point;

/** Abstract base class for UI elements */
public abstract class UIElement {
	public abstract void updateUI(Point mouseClick);
	public abstract void renderUI(Graphics2D g2d, boolean debug);
}
