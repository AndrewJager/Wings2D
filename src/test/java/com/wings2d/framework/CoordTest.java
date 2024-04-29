package com.wings2d.framework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.wings2d.framework.core.LevelManager;
import com.wings2d.framework.core.LevelManager.Coord;

@DisplayName("Coord Test")
public class CoordTest {
	private LevelManager manager;
	
	@BeforeEach
	public void setup() {
		manager = new LevelManager(null);
	}
	
	@Test
	public void testSetUnscaledReadUnscaled() {
		Coord c = manager.makeLocation().getX();
		c.setUnscaled(10);
		assertEquals(c.getUnscaled(), 10);
	}
	
	@Test
	public void testSetUnscaledReadScaled() {
		Coord c = manager.makeLocation().getX();
		c.setUnscaled(10);
		assertEquals(c.getScaled(), 10);
	}
	
	@Test
	public void testSetUnscaledReadUnscaledHalf() {
		manager.setScale(0.5);
		Coord c = manager.makeLocation().getX();
		c.setUnscaled(10);
		assertEquals(c.getUnscaled(), 10);
	}
	
	@Test
	public void testSetUnscaledReadScaledHalf() {
		manager.setScale(0.5);
		Coord c = manager.makeLocation().getX();
		c.setUnscaled(10);
		assertEquals(c.getScaled(), 5);
	}
	
	@Test
	public void testSetUnscaledReadUnscaledDouble() {
		manager.setScale(2);
		Coord c = manager.makeLocation().getX();
		c.setUnscaled(10);
		assertEquals(c.getUnscaled(), 10);
	}
	
	@Test
	public void testSetUnscaledReadScaledDouble() {
		manager.setScale(2);
		Coord c = manager.makeLocation().getX();
		c.setUnscaled(10);
		assertEquals(c.getScaled(), 20);
	}
	
	@Test
	public void testSetScaledReadUnscaled() {
		Coord c = manager.makeLocation().getX();
		c.setScaled(10);
		assertEquals(c.getUnscaled(), 10);
	}
	
	@Test
	public void testSetScaledReadScaled() {
		Coord c = manager.makeLocation().getX();
		c.setScaled(10);
		assertEquals(c.getScaled(), 10);
	}
	
	@Test
	public void testSetScaledReadUnscaledHalf() {
		manager.setScale(0.5);
		Coord c = manager.makeLocation().getX();
		c.setScaled(10);
		assertEquals(c.getUnscaled(), 20);
	}
	
	@Test
	public void testSetScaledReadScaledHalf() {
		manager.setScale(0.5);
		Coord c = manager.makeLocation().getX();
		c.setScaled(10);
		assertEquals(c.getScaled(), 10);
	}
	
	@Test
	public void testSetScaledReadUnscaledDouble() {
		manager.setScale(2);
		Coord c = manager.makeLocation().getX();
		c.setScaled(10);
		assertEquals(c.getUnscaled(), 5);
	}
	
	@Test
	public void testSetScaledReadScaledDouble() {
		manager.setScale(2);
		Coord c = manager.makeLocation().getX();
		c.setScaled(10);
		assertEquals(c.getScaled(), 10);
	}
	
	@Test
	public void testRescale1() {
		Coord c = manager.makeLocation().getX();
		c.setUnscaled(10);
		manager.setScale(2);
		assertEquals(c.getUnscaled(), 10);
	}
	
	@Test
	public void testRescale2() {
		Coord c = manager.makeLocation().getX();
		c.setUnscaled(10);
		manager.setScale(2);
		assertEquals(c.getScaled(), 20);
	}
	
	@Test
	public void testRescale3() {
		Coord c = manager.makeLocation().getX();
		c.setUnscaled(10);
		manager.setScale(0.5);
		assertEquals(c.getScaled(), 5);
	}
	
	
}
