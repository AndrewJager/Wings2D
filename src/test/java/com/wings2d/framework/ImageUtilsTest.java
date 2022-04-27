package com.wings2d.framework;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.wings2d.framework.misc.CardinalDir;
import com.wings2d.framework.rendering.ImageUtils;

@DisplayName("ImageUtils Test")
class ImageUtilsTest{

	@BeforeEach
	void setUp() throws Exception {
	}
	
	private BufferedImage createTestImage(final int width, final int height) {
		return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	@ParameterizedTest(name = "{index} -> side=''{0}''")
	@DisplayName("Test expandImageOnSide")
	@EnumSource(CardinalDir.class)
	void testExpandImageOnSide(final CardinalDir side) {
		int size = 5;
		BufferedImage img = ImageUtils.expandImageOnSide(createTestImage(size, size), side);
		switch(side) {
			case NORTH, SOUTH -> assertEquals(size + 1, img.getHeight());
			case EAST, WEST -> assertEquals(size + 1, img.getWidth());
		}
	}
	
	@Test
	@DisplayName("Test expandOnAllSides - width")
	void testExpandOnAllSidesWidth() {
		int size = 5;
		BufferedImage img = ImageUtils.expandImageOnAllSides(createTestImage(size, size));
		assertEquals(size + 2, img.getWidth());
	}
	@Test
	@DisplayName("Test expandOnAllSides - height")
	void testExpandOnAllSidesHeight() {
		int size = 5;
		BufferedImage img = ImageUtils.expandImageOnAllSides(createTestImage(size, size));
		assertEquals(size + 2, img.getHeight());
	}
}
