package framework;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Button{
	private int x, y, width, height;
	private Color backgroundColor;
	private Image img;
	private boolean isClicked = false;
	
	public Button (int x, int y, int width, int height, Color backgroundColor)
	{
		this.x = x;
		this.y = y; 
		this.width = width;
		this.height = height;
		this.backgroundColor = backgroundColor;
	}
	public boolean getClicked()
	{
		return isClicked;
	}
	public void setImage(Image img)
	{
		this.img = img;
		this.img.setX(x + (width / 2));
		this.img.setY(y + (height / 2));
	}
	
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
		g2d.fillRect(x, y, width, height);
		if (img != null)
		{
			img.render(g2d, debug);
		}
	}
	
	public double getX()
	{ return x; }
	public double getY()
	{ return y; }
	public void translate(double xVel, double yVel)
	{
		x += xVel;
		y += yVel;
	}
}
