package com.wings2d.framework.shape;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Dimension2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DoubleDimensionTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testConstructorWidth() {
		Dimension2D testDim = new DoubleDimension(15.003, 2);
		assertEquals(15.003, testDim.getWidth());
	}
	@Test
	void testConstructorHeight() {
		Dimension2D testDim = new DoubleDimension(0, 21.12);
		assertEquals(21.12, testDim.getHeight());
	}
	@Test
	void testSetSizeWidth() {
		Dimension2D testDim = new DoubleDimension(0, 0);
		testDim.setSize(43, 2);
		assertEquals(43, testDim.getWidth());
	}
	@Test
	void testSetSizeHeight() {
		Dimension2D testDim = new DoubleDimension(0, 0);
		testDim.setSize(43, 2);
		assertEquals(2, testDim.getHeight());
	}
	@Test
	void testSetWidth() {
		DoubleDimension testDim = new DoubleDimension(0, 0);
		testDim.setWidth(43);
		assertEquals(43, testDim.getWidth());
	}
	@Test
	void testSetHeight() {
		DoubleDimension testDim = new DoubleDimension(0, 0);
		testDim.setHeight(10.5);
		assertEquals(10.5, testDim.getHeight());
	}
}
