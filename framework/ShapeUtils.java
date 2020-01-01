package framework;

import java.awt.Shape;
import java.awt.geom.AffineTransform;

/**
 * Static functions to perform operations on Shapes.
 */
public class ShapeUtils {
	/**
	 * Scale a shape uniformly on both axis
	 * @param shape Shape to be scaled, does not modify the inputed Shape. 
	 * @param amt Amount to scale the shape by. 1 = same size as current.
	 * @return A new Shape, use this instead of the Shape passed to this function.
	 */
	public static Shape scale(Shape shape, double amt)
	{
		AffineTransform transform = new AffineTransform();
		transform.scale(amt, amt);
		return transform.createTransformedShape(shape);
	}
	/**
	 * Translates a shape
	 * @param shape Shape to be translated, does not modify the inputed Shape.
	 * @param x Amount to translate by on the X axis.
	 * @param y Amount to translate by on the Y axis.
	 * @return A new Shape, use this instead of the Shape passed to this function.
	 */
	public static Shape translate(Shape shape, double x, double y)
	{
		AffineTransform transform = new AffineTransform();
		transform.translate(x, y);
		return transform.createTransformedShape(shape);
	}
	/**
	 * Rotate a shape around its center
	 * @param shape Shape to be rotated.
	 * @param angle Angle in radians to rotate the shape.
	 * @return A new Shape, use this instead of the Shape passed to this function.
	 */
	public static Shape rotate(Shape shape, double angle)
	{
		AffineTransform transform = new AffineTransform();
		transform.rotate(angle);
		return transform.createTransformedShape(shape);
	}
	/**
	 * Rotates a shape around the desired point
	 * @param shape Shape to rotate.
	 * @param angle Angle in radians to rotate the shape.
	 * @param anchorX X location to rotate around.
	 * @param anchorY Y location to rotate around.
	 * @return A new Shape, use this instead of the Shape passed to this function.
	 */
	public static Shape rotateAround(Shape shape, double angle, double anchorX, double anchorY)
	{
		AffineTransform transform = new AffineTransform();
		transform.rotate(angle, anchorX, 
				anchorY);
		return transform.createTransformedShape(shape);
	}
	/**
	 * Flip a Shape around the X-axis
	 * @param shape Shape to be flipped.
	 * @return A new Shape, use this instead of the Shape passed to this function.
	 */
	public static Shape flipX(Shape shape)
	{
		AffineTransform transform = new AffineTransform();
		transform.scale(-1, 1);
		Shape newShape =  transform.createTransformedShape(shape);
		return ShapeUtils.translate(newShape, -newShape.getBounds2D().getX(), 0);
	}
	/**
	 * Flip a Shape around the Y-axis
	 * @param shape Shape to be flipped.
	 * @return A new Shape, use this instead of the Shape passed to this function.
	 */
	public static Shape flipY(Shape shape)
	{
		AffineTransform transform = new AffineTransform();
		transform.scale(1, -1);
		Shape newShape = transform.createTransformedShape(shape);
		return ShapeUtils.translate(newShape, 0, -newShape.getBounds2D().getY());
	}
}
