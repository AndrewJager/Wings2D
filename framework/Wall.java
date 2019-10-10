package framework;

import java.awt.geom.Line2D;

public class Wall{
	private Line2D line;
	private WallTypes type;
	
	public Wall(double x1, double y1, double x2, double y2, WallTypes type)
	{
		line = new Line2D.Double(x1, y1, x2, y2);
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
}
