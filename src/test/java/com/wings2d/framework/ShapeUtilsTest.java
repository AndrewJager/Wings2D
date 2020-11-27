package com.wings2d.framework;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.wings2d.framework.shape.ShapeUtils;

class ShapeUtilsTest {

	@BeforeEach
	void setUp() throws Exception {
	}
	
	// ShapeUtils.scale 
	@Test
	void testScaleRect() {
		Rectangle2D testRect = new Rectangle2D.Double(0, 0, 40, 20);
		Shape result = ShapeUtils.scale(testRect, 2.0);
		assertEquals(80, result.getBounds2D().getWidth());
	}
	@Test
	void testScaleTranslatedRect() {
		Rectangle2D testRect = new Rectangle2D.Double(10, -41, 40, 20);
		Shape result = ShapeUtils.translate(testRect, -10.2, 3.376);
		result = ShapeUtils.scale(result, 0.5);
		assertEquals(20, result.getBounds2D().getWidth());
	}
	
	// ShapeUtils.translate 
	@Test
	void testTranslateRect() {
		Rectangle2D testRect = new Rectangle2D.Double(10, -41, 40, 20);
		Shape result = ShapeUtils.translate(testRect, -10, 41);
		assertEquals(0, result.getBounds2D().getX());
	}
	
	// ShapeUtils.rotate 
	@Test
	void testRotateRect() {
		Rectangle2D testRect = new Rectangle2D.Double(10, 10, 20, 40);
		Shape result = ShapeUtils.rotate(testRect, Math.toRadians(90));
		assertEquals(40, result.getBounds2D().getWidth());
	}
	@Test
	void testRotateTranslatedRect() {
		Rectangle2D testRect = new Rectangle2D.Double(10, 10, 20, 40);
		Shape result = ShapeUtils.translate(testRect, 10, -5000);
		result = ShapeUtils.rotate(result, Math.toRadians(90));
		assertEquals(40, result.getBounds2D().getWidth());
	}
	
	// ShapeUtils.rotateAround 
	@Test
	void testRotateAroundRect() {
		Rectangle2D testRect = new Rectangle2D.Double(0, 0, 20, 40);
		Shape result = ShapeUtils.rotateAround(testRect, Math.toRadians(90), 0, 0);
		assertEquals(-40, result.getBounds2D().getX()); 
	}
	@Test 
	void testRotateAroundRect180() {
		Rectangle2D testRect = new Rectangle2D.Double(0, 0, 20, 20);
		Shape result = ShapeUtils.rotateAround(testRect, Math.toRadians(180), 30, 10);
		assertEquals(50, result.getBounds2D().getCenterX());
	}
	
	// ShapeUtils.flipX
	@Test
	void testFlipXTri() {
		Path2D testPath = new Path2D.Double();
		testPath.moveTo(0, 0);
		testPath.lineTo(10, 20);
		testPath.lineTo(0, 20);
		testPath.closePath();
		Shape result = ShapeUtils.flipX(testPath);
		PathIterator iter = result.getPathIterator(null);
		double[] coords = new double[6];
		iter.currentSegment(coords);
		double xPos = coords[0];
		assertEquals(10, xPos);
	}
	
	// ShapeUtils.flipX
	@Test
	void testFlipYTri() {
		Path2D testPath = new Path2D.Double();
		testPath.moveTo(0, 0);
		testPath.lineTo(10, 20);
		testPath.lineTo(0, 20);
		testPath.closePath();
		Shape result = ShapeUtils.flipY(testPath);
		PathIterator iter = result.getPathIterator(null);
		double[] coords = new double[6];
		iter.currentSegment(coords);
		double yPos = coords[1];
		assertEquals(20, yPos);
	}
	
	// ShapeUtils.distanceFromLineToPoint 
	@Test
	void testDistanceFromLineToPointVerticalLine()
	{
		Line2D line = new Line2D.Double(0, 0, 0, 25.4);
		double dist = ShapeUtils.distanceFromLineToPoint(10, 5, line);
		assertEquals(10, dist);
	}
	@Test
	void testDistanceFromLineToPointHorizontalLine()
	{
		Line2D line = new Line2D.Double(2, 4, 15, 4);
		double dist = ShapeUtils.distanceFromLineToPoint(10, 14, line);
		assertEquals(10, dist);
	}
	@Test
	void testDistanceFromLineToPointAngledLine()
	{
		Line2D line = new Line2D.Double(0, 10, 10, 0);
		double dist = ShapeUtils.distanceFromLineToPoint(20, 0, line);
		assertEquals(10, dist);
	}
	@Test
	void testDistanceFromLineToPointOverlappingLineAndPoint()
	{
		Line2D line = new Line2D.Double(0, 0, 0, 25);
		double dist = ShapeUtils.distanceFromLineToPoint(0, 5, line);
		assertEquals(0, dist);
	}
	@Test
	void testDistanceFromLineToPointPreciseDistance()
	{
		Line2D line = new Line2D.Double(0, 0, 0, 25);
		double dist = ShapeUtils.distanceFromLineToPoint(0.002, 5, line);
		assertEquals(0.002, dist);
	}

	// ShapeUtils.getPointCount 
	@Test
	void testGetPointCountRectangle() {
		Rectangle2D testRect = new Rectangle2D.Double(0, 0, 100, 50);
		assertEquals(4, ShapeUtils.getPointCount(testRect));
	}
	
	

}
