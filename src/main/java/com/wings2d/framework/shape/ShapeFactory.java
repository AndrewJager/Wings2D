package com.wings2d.framework.shape;

import java.awt.Shape;
import java.awt.geom.GeneralPath;

/**
 * Static functions that return new Shapes. All shapes must never have negative values in their points.
 */
public final class ShapeFactory {
	public static Shape triangle()
	{
		GeneralPath path = new GeneralPath();
		path.moveTo(5, 0);
		path.lineTo(10, 5);
		path.lineTo(0, 5);
		
		return path;
	}
	
	public static Shape solIconBar()
	{
		GeneralPath path = new GeneralPath();
		path.moveTo(0, 0);
		path.lineTo(5, 0);
		path.lineTo(5, 5);
		path.lineTo(8, 12);
		path.lineTo(0, 5);
		
		return path;
	}
	
	public static Shape arrowhead(double height, double thickness)
	{
		GeneralPath path = new GeneralPath();
		path.moveTo(0, height - thickness);
		path.lineTo(5, 0);
		path.lineTo(10, height - thickness);
		path.lineTo(9, height);
		path.lineTo(5, thickness + 1);
		path.lineTo(1, height);
		
		return path;
	}
	
	public static Shape squareCutCornors(double squareSide, double cornorSize)
	{
		GeneralPath path = new GeneralPath();
		path.moveTo(0, cornorSize);
		path.lineTo(cornorSize, 0);
		path.lineTo(cornorSize + squareSide, 0);
		path.lineTo(cornorSize + squareSide + cornorSize, cornorSize);
		path.lineTo(cornorSize + squareSide + cornorSize, cornorSize + squareSide);
		path.lineTo(cornorSize + squareSide, cornorSize + squareSide + cornorSize);
		path.lineTo(cornorSize, cornorSize + squareSide + cornorSize);
		path.lineTo(0, cornorSize + squareSide);

		
		return path;
	}
}
