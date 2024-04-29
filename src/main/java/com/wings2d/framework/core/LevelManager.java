package com.wings2d.framework.core;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.wings2d.framework.input.KeyMapping;
import com.wings2d.framework.input.TextBox;


public class LevelManager {

	public enum Compare {
		EQUAL,
		EQUAL_LESS_THAN,
		EQUAL_GREATER_THAN,
		LESS_THAN,
		GREATER_THAN
	}
	public class Coord {
		private double unscaled, scaled;
		private LevelManager manager;
		
		private Coord(LevelManager manager) {
			this.manager = manager;
			unscaled = 0;
			scaled = 0;
		}
		
		public void setUnscaled(final double coord) {
			unscaled = coord;
			scaled = coord * manager.getScale();
		}
		public void setScaled(final double coord) {
			scaled = coord;
			unscaled = (1 / manager.getScale()) * coord;
		}
		
		public double getUnscaled() {
			return unscaled;
		}
		public double getScaled() {
			return scaled;
		}
		
		public boolean compare(final Coord other, final Compare action) {
			return switch(action) {
				case EQUAL -> {yield this.unscaled == other.unscaled;}
				case EQUAL_LESS_THAN -> {yield this.unscaled <= other.unscaled;}
				case EQUAL_GREATER_THAN -> {yield this.unscaled >= other.unscaled;}
				case LESS_THAN -> {yield this.unscaled < other.unscaled;}
				case GREATER_THAN -> {yield this.unscaled > other.unscaled;}
			};
		}
		
		private void rescale() {
			scaled = unscaled * manager.scale;
		}
	}
	
	public class Location {
		private Coord x, y;
		private Node node;
		
		public Location(final LevelManager manager) {
			// These coords should not be directly managed by the LevelManager, so they should not be created with makeCoord()
			x = new Coord(manager);
			y = new Coord(manager);
		}
		
		public void setNode(final Node node) {
			this.node = node;
			rescale();
		}
		
		public Coord getX() {
			return x;
		}
		public Coord getY() {
			return y;
		}
		
		private void rescale() {
			if (node == null) {
				x.rescale();
				y.rescale();
			}
			else {
				x.setUnscaled(node.getGrid().getNodeX(node));
				y.setUnscaled(node.getGrid().getNodeY(node));
			}
		}
	}

	private List<Level> levels;
	private int curLevel;
	private KeyMapping keys;
	private double scale;
	private TextBox textBox;
	private List<Location> locs; // List of Locations so we can automatically scale them anytime the scale changes
	private Game game;
	
	public LevelManager(final Game game)
	{
		this.game = game;
		
		curLevel = 0; // First level, probably menu
		scale = 1;
		keys = new KeyMapping();
		keys.setKey("Enter", 10);
		keys.setKey("Left", 37);
		keys.setKey("Right", 39);
		keys.setKey("Esc", 27);
		keys.setKey("Jump", 38);
		
		levels = new ArrayList<Level>();
		locs = new ArrayList<Location>();
	}
	public void rescale()
	{
		for (int i = 0; i < levels.size(); i++) {
			levels.get(i).rescale();
		}
		for (int i = 0; i < locs.size(); i++) {
			locs.get(i).rescale();
		}
		
		for (int i = 0; i < levels.size(); i++) {
			levels.get(i).afterRescale();
		}
	}

	public KeyMapping getKeyMapping()
	{
		return this.keys;
	}
	public void setLevel(int newLevel)
	{
		curLevel = newLevel;
	}
	
	public void addLevel(Level newLevel)
	{
		levels.add(newLevel);
		levels.set(newLevel.getIdentifer(), newLevel);
	}
	public double getScale() {
		return scale;
	}
	public void setScale(double scale) {
		if (scale < 0)
		{
			throw new IllegalArgumentException("Scale of " + scale + " cannot be negative!");
		}
		if (scale == 0)
		{
			throw new IllegalArgumentException("Scale cannot be 0!");
		}
		if (this.scale != scale)
		{
			this.scale = scale;
			rescale();
		}
		this.scale = scale;
	}
	public void update(double dt)
	{
		levels.get(curLevel).update(dt);
	}
	public void updateUI(Point mouseClick)
	{
		this.levels.get(curLevel).updateUI(mouseClick);
	}
	public void render(Graphics2D g2d, boolean debug)
	{
		this.levels.get(curLevel).render(g2d, debug);
	}
	public void renderUI(Graphics2D g2d, boolean debug)
	{
		this.levels.get(curLevel).renderUI(g2d, debug);
	}


	public TextBox getTextBox() {
		return textBox;
	}
	public void setTextBox(TextBox textBox) {
		this.textBox = textBox;
	}
	
	public Location makeLocation() {
		Location l = new Location(this);
		locs.add(l);
		return l;
	}
	
	public Game getGame() {
		return game;
	}
}
