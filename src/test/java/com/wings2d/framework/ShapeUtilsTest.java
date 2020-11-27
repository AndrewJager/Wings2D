package com.wings2d.framework;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.wings2d.framework.shape.ShapeUtils;

class ShapeUtilsTest {

	@BeforeEach
	void setUp() throws Exception {
	}
	
	// ShapeUtils.scale tests
	@Test
	void testScaleRect() {
		Rectangle2D testRect = new Rectangle2D.Double(0, 0, 40, 20);
		Shape result = ShapeUtils.scale(testRect, 2.0);
		assertEquals(80, result.getBounds2D().getWidth());
	}
	@Test
	void testScaleTranslatedRect() {
		Rectangle2D testRect = new Rectangle2D.Double(10, -41, 40, 20);
		AffineTransform transform = new AffineTransform();
		transform.translate(-10.2, 3.376);
		Shape result = transform.createTransformedShape(testRect);
		result = ShapeUtils.scale(result, 0.5);
		assertEquals(20, result.getBounds2D().getWidth());
	}

	// getPointCount tests
	@Test
	void testRectangle() {
		Rectangle2D testRect = new Rectangle2D.Double(0, 0, 100, 50);
		assertEquals(4, ShapeUtils.getPointCount(testRect));
	}
	
	

}
