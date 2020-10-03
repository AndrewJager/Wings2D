package com.wings2d.framework;


import static org.junit.jupiter.api.Assertions.*;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShapeComparatorTest {
	@BeforeEach
	void setUp() throws Exception {
	}
	@Test
	void testSimilarSquares() {
		Rectangle2D one = new Rectangle2D.Double(0, 0, 10, 10);
		Rectangle2D two = new Rectangle2D.Double(0, 0, 10, 10);
		assertTrue(ShapeComparator.similarShapes(one, two));
	}
	@Test
	void testDifferentSquares() {
		Rectangle2D one = new Rectangle2D.Double(0, 0, 20, 20);
		Rectangle2D two = new Rectangle2D.Double(0, 0, 10, 10);
		assertFalse(ShapeComparator.similarShapes(one, two));
	}
	@Test
	void testTranslatedSquares() {
		Rectangle2D one = new Rectangle2D.Double(10, 5, 12, 12);
		Rectangle2D two = new Rectangle2D.Double(3, 45, 12, 12);
		assertTrue(ShapeComparator.similarShapes(one, two));
	}	
	@Test
	void testNegativeTranslatedSquares() {
		Rectangle2D one = new Rectangle2D.Double(-10, 5, 10, 10);
		Rectangle2D two = new Rectangle2D.Double(3, -45, 10, 10);
		assertTrue(ShapeComparator.similarShapes(one, two));
	}
	@Test
	void testRotatedSquares() {
		Shape one = new Rectangle2D.Double(0, 0, 10, 10);
		Shape two = new Rectangle2D.Double(0, 0, 10, 10);
		AffineTransform transform = new AffineTransform();
		transform.rotate(Math.toRadians(45));
		one = transform.createTransformedShape(one);
		assertTrue(ShapeComparator.similarShapes(one, two));
	}
	@Test
	void testRotatedAndTranslatedSquares() {
		Shape one = new Rectangle2D.Double(0, 0, 10, 10);
		Shape two = new Rectangle2D.Double(0, 0, 10, 10);
		AffineTransform transform = new AffineTransform();
		transform.rotate(Math.toRadians(45));
		transform.translate(10, -50);
		one = transform.createTransformedShape(one);
		transform = new AffineTransform();
		transform.rotate(Math.toRadians(-162));
		transform.translate(-481, 2);
		two = transform.createTransformedShape(two);
		assertTrue(ShapeComparator.similarShapes(one, two));
	}
	@Test
	void testDifferentAmountOfPoints() {
		Shape one = new Rectangle2D.Double(0, 0, 10, 10);
		Path2D two = new Path2D.Double();
		two.moveTo(23, 3);
		two.lineTo(10, 4);
		two.lineTo(63, 2);
		assertFalse(ShapeComparator.similarShapes(one, two));
	}
	@Test
	void testDifferentAmountOfPointsTwo() {
		Path2D one = new Path2D.Double();
		one.moveTo(23, 3);
		one.lineTo(10, 4);
		one.lineTo(63, 2);
		Shape two = new Rectangle2D.Double(0, 0, 10, 10);
		assertFalse(ShapeComparator.similarShapes(one, two));
	}
	@Test
	void testComplexPath() {
		Path2D one = new Path2D.Double();
		one.moveTo(36, 0);
		one.lineTo(45, 23);
		one.lineTo(73, -100);
		one.lineTo(-1000, -14140);
		one.lineTo(44455, 122324);
		one.lineTo(0, 0);
		one.lineTo(0, 0);
		one.lineTo(34, 6);
		Path2D two = new Path2D.Double();
		two.moveTo(36, 0);
		two.lineTo(45, 23);
		two.lineTo(73, -100);
		two.lineTo(-1000, -14140);
		two.lineTo(44455, 122324);
		two.lineTo(0, 0);
		two.lineTo(0, 0);
		two.lineTo(34, 6);
		assertTrue(ShapeComparator.similarShapes(one, two));
	}
	@Test
	void testComplexPathRotateAndTranslate() {
		Path2D one = new Path2D.Double();
		one.moveTo(36, 0);
		one.lineTo(45, 23);
		one.lineTo(73, -100);
		one.lineTo(-1000, -14140);
		one.lineTo(44455, 122324);
		one.lineTo(0, 0);
		one.lineTo(0, 0);
		one.lineTo(34, 6);
		Path2D two = new Path2D.Double();
		two.moveTo(36, 0);
		two.lineTo(45, 23);
		two.lineTo(73, -100);
		two.lineTo(-1000, -14140);
		two.lineTo(44455, 122324);
		two.lineTo(0, 0);
		two.lineTo(0, 0);
		two.lineTo(34, 6);
		AffineTransform transform = new AffineTransform();
		transform.rotate(Math.toRadians(45));
		transform.translate(-15, 555555);
		transform.rotate(71);
		transform.translate(45, -.001);
		one = (Path2D)transform.createTransformedShape(one);
		transform = new AffineTransform();
		transform.rotate(1000000000); // SPIN!
		transform.translate(34, -1);
		two = (Path2D)transform.createTransformedShape(two);
		assertTrue(ShapeComparator.similarShapes(one, two));
	}
}
