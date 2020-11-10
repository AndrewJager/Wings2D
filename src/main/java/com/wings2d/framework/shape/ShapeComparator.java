package com.wings2d.framework.shape;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

public class ShapeComparator {
	/**
	 * Attempts to determine if two shapes are "similar", meaning that they are the same, ignoring position and rotation.
	 * This algorithm assumes that the points of the shapes are in the same order.
	 * @param a First Shape
	 * @param b Second Shape
	 * @return True if the Shapes are similar
	 */
	public static boolean similarShapes(final Shape a, final Shape b)
	{
		PathIterator iteratorA = a.getPathIterator(null);
		PathIterator iteratorB = b.getPathIterator(null);
		double[] coordsA = new double[6];
		double[] coordsB = new double[6];
		AffineTransform transform = new AffineTransform();
		
		// Calculate the distance required to align the rotations
		iteratorA.currentSegment(coordsA);
		iteratorB.currentSegment(coordsB);
		Point2D pointA1 = new Point2D.Double(coordsA[0], coordsA[1]);
		Point2D pointB1 = new Point2D.Double(coordsB[0], coordsB[1]);
		iteratorA.next();
		iteratorB.next();
		iteratorA.currentSegment(coordsA);
		iteratorB.currentSegment(coordsB);
		Point2D pointA2 = new Point2D.Double(coordsA[0], coordsA[1]);
		Point2D pointB2 = new Point2D.Double(coordsB[0], coordsB[1]);
		double angleA = Math.atan2(pointA2.getX() - pointA1.getX(), pointA2.getY() - pointA1.getY());
		double angleB = Math.atan2(pointB2.getX() - pointB1.getX(), pointB2.getY() - pointB1.getY());
		
		// Rotate the shapes
		transform.rotate(angleA);
		Shape rotatedA = transform.createTransformedShape(a);
		transform = new AffineTransform();
		transform.rotate(angleB);
		Shape rotatedB = transform.createTransformedShape(b);
		
		// Translate the shapes to 0/0
		transform = new AffineTransform();
		transform.translate(-rotatedA.getBounds2D().getX(), -rotatedA.getBounds2D().getY());
		Shape translatedA = transform.createTransformedShape(rotatedA);
		transform = new AffineTransform();
		transform.translate(-rotatedB.getBounds2D().getX(), -rotatedB.getBounds2D().getY());
		Shape translatedB = transform.createTransformedShape(rotatedB);		
		
		boolean isSame = true;
		iteratorA = translatedA.getPathIterator(null);
		iteratorB = translatedB.getPathIterator(null);
		while(!iteratorA.isDone() && isSame)
		{
			iteratorA.currentSegment(coordsA);
			iteratorB.currentSegment(coordsB);
			if (!softCompare(coordsA[0], coordsB[0]) || !softCompare(coordsA[1], coordsB[1]) || !softCompare(coordsA[2], coordsB[2])
					|| !softCompare(coordsA[3], coordsB[3]) || !softCompare(coordsA[4], coordsB[4]) || !softCompare(coordsA[5], coordsB[5]))
			{
				isSame = false;
			}
			iteratorA.next();
			iteratorB.next();
		}
		return isSame;
	}
	
	/**
	 * Gets the angle in degrees that Shape b is rotated (offset) from Shape a. Assumes that the shapes are similar if validateSameness = false.
	 * Rounds to 4 decimal places.
	 * Unexpected behavior may result if the shapes are not similar.
	 * @param a {@link java.awt.Shape Shape} to compare against.
	 * @param b {@link java.awt.Shape Shape} to get the angle of.
	 * @param validateSameness Calls {@link ShapeComparator#similarShapes similarShapes} before checking Rotation. 
	 * Throws {@link java.lang.IllegalArgumentException IllegalArgumentException} if this fails.
	 * @return
	 */
	public static double getRotationFrom(final Shape a, final Shape b, final boolean validateSameness)
	{
		if (validateSameness)
		{
			if (!similarShapes(a, b))
			{
				throw new IllegalArgumentException("Shapes are not similar!");
			}
		}
		PathIterator iteratorA = a.getPathIterator(null);
		PathIterator iteratorB = b.getPathIterator(null);
		double[] coordsA = new double[6];
		double[] coordsB = new double[6];
		iteratorA.currentSegment(coordsA);
		iteratorB.currentSegment(coordsB);
		Point2D pointA1 = new Point2D.Double(coordsA[0], coordsA[1]);
		Point2D pointB1 = new Point2D.Double(coordsB[0], coordsB[1]);
		iteratorA.next();
		iteratorB.next();
		iteratorA.currentSegment(coordsA);
		iteratorB.currentSegment(coordsB);
		Point2D pointA2 = new Point2D.Double(coordsA[0], coordsA[1]);
		Point2D pointB2 = new Point2D.Double(coordsB[0], coordsB[1]);
		double angleA = Math.atan2(pointA2.getX() - pointA1.getX(), pointA2.getY() - pointA1.getY());
		double angleB = Math.atan2(pointB2.getX() - pointB1.getX(), pointB2.getY() - pointB1.getY());
		double angleDeg = Math.toDegrees(angleA - angleB);
		return (double)Math.round((angleDeg) * 10000d) / 10000d;
	}
	
	private static boolean softCompare(final double a, final double b)
	{
		return Math.abs(a - b) < 0.001;
	}
}
