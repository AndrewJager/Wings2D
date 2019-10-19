package framework;

import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class Wall{
	private Line2D line;
	private WallTypes type;
	private Level level;
	
	public Wall(Level level, double x1, double y1, double x2, double y2, WallTypes type)
	{
		this.level = level;
		double scale = level.getManager().getScale();
		line = new Line2D.Double(x1 * scale, y1 * scale, x2 * scale, y2 * scale);
		this.type = type;
	}
	
	public WallTypes getType()
	{
		return this.type;
	}
	public Line2D getLine()
	{
		return this.line;
	}
	public double getX()
	{ return line.getX1(); }
	public double getY()
	{ return line.getY1(); }
	public void translate(double xVel, double yVel)
	{
		line.setLine(line.getX1() + xVel, line.getY1() + yVel, line.getX2() + xVel, line.getY2() + yVel);
	}
}
