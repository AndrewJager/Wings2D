package framework;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
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
}
