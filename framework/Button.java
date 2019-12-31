package framework;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Custom GUI button
 */
public class Button extends UIElement{
	private double x, y, width, height;
	private Color backgroundColor;
	private Image img;
	private boolean isClicked = false;
	
	/**
	 * Constructor for button class
	 * @param x X position of top-right corner of button
	 * @param y Y Position of top-right corner of button
	 * @param width Width of button
	 * @param height Height of button
	 * @param backgroundColor Background color of button
	 * @param level Level the Button is assocated with
	 */
	public Button (int x, int y, int width, int height, Color backgroundColor, Level level)
	{
		double scale = level.getManager().getScale();
		this.x = x * scale;
		this.y = y * scale; 
		this.width = width * scale;
		this.height = height * scale;
		this.backgroundColor = backgroundColor;
	}
	public boolean getClicked()
	{
		return isClicked;
	}
	/**
	 * Add an image to the center of the button
	 * @param img Image to place in center of button
	 */
	public void setImage(Image img)
	{
		this.img = img;
		this.img.setCenterX(x + (width / 2));
		this.img.setCenterY(y + (height / 2));
	}
	/**
	 * Update function for button
	 * @param mouseClick Location of mouse click, null if not clicked
	 */
	public void updateUI(Point mouseClick)
	{
		isClicked = false;
		if (mouseClick != null)
		{
			if (mouseClick.getX() > this.x && mouseClick.getX() < (this.x + this.width))
			{
				if (mouseClick.getY() > this.y && mouseClick.getY() < (this.y + this.height))
				{
					isClicked = true;
				}
			}
		}
	}
	public void renderUI(Graphics2D g2d, boolean debug) {
		g2d.setColor(backgroundColor);
		g2d.fillRect((int)x, (int)y, (int)width, (int)height);
		if (img != null)
		{
			img.render(g2d, debug);
		}
	}
	
	public double getX()
	{ return x; }
	public double getY()
	{ return y; }
}
