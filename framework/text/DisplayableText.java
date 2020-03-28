package framework.text;

import java.awt.Graphics2D;

public abstract class DisplayableText {
	private double x;
	private double y;
	public void setX(double x)
	{ 
		this.x = x;
	}
	public void setY(double y)
	{
		this.y = y;
	}
	public double getX()
	{
		return x;
	}
	public double getY()
	{
		return y;
	}
	
	public abstract void update(double dt);
	public abstract void render(Graphics2D g2d);
}
