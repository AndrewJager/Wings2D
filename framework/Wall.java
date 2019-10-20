package framework;

import java.awt.geom.Line2D;

/** 
 * A static component of the level, eg. wall/floor/ramp. Represented as a line.
 */
public class Wall{
	private Line2D line;
	private WallTypes type;
	/**
	 * Constructor for WallTypes
	 * @param level Level this wall belongs to
	 * @param x1 X location of first point
	 * @param y1 Y location of first point
	 * @param x2 X location of second point
	 * @param y2 Y location of second point
	 * @param type Type of wall 
	 */
	public Wall(Level level, double x1, double y1, double x2, double y2, WallTypes type)
	{
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
}
