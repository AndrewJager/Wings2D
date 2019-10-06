package framework;

import java.awt.Polygon;
import java.awt.geom.Point2D;

/**
 * Child of java.awt.Polygon;
 */
public class Shape extends Polygon{
	private int xOffset, yOffset;
	
	public Shape()
	{
		xOffset = 0;
		yOffset = 0;
	}
	public Shape(int xOffset, int yOffset)
	{
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	public int getxOffset() {
		return xOffset;
	}
	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}
	public int getyOffset() {
		return yOffset;
	}
	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}
}
