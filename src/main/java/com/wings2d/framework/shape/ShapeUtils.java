package com.wings2d.framework.shape;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

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

	public static double distanceFromLineToPoint(double pointX, double pointY, double x1, double y1, double x2, double y2)
	{
		double xDist = pointX - x1;
		double yDist = pointY - y1;
		double lineXDist = x2 - x1;
		double lineYDist = y2 - y1;

		double dot = (xDist * lineXDist) + (yDist * lineYDist);
		double len_sq = (lineXDist * lineXDist) + (lineYDist * lineYDist);
		double param = -1;
		if (len_sq != 0) 
		{
			param = dot / len_sq;
		}

		double xx, yy;

		if (param < 0) {
			xx = x1;
			yy = y1;
		}
		else if (param > 1) {
			xx = x2;
			yy = y2;
		}
		else {
			xx = x1 + param * lineXDist;
			yy = y1 + param * lineYDist;
		}

		double dx = pointX - xx;
		double dy = pointY - yy;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	public static double distanceFromLineToPoint(double pointX, double pointY, Line2D line)
	{
		return distanceFromLineToPoint(pointX, pointY, line.getX1(), line.getY1(), line.getX2(), line.getY2());
	}
}
