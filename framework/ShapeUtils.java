package framework;

import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class ShapeUtils {
	public static Shape scale(Shape shape, double amt)
	{
		AffineTransform transform = new AffineTransform();
		transform.scale(amt, amt);
		return transform.createTransformedShape(shape);
	}
	
	public static Shape translate(Shape shape, double x, double y)
	{
		AffineTransform transform = new AffineTransform();
		transform.translate(x, y);
		return transform.createTransformedShape(shape);
	}
}
