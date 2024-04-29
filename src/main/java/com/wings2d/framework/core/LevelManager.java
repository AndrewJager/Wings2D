package com.wings2d.framework.core;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;


public class LevelManager {
	private List<Level> levels;
	private int curLevel;
	private double scale;
	private Game game;
	private AffineTransform viewTransform;
	
	public LevelManager(final Game game)
	{
		this.game = game;
		
		curLevel = 0; // First level, probably menu
		scale = 1;
		viewTransform = new AffineTransform();
		
		levels = new ArrayList<Level>();
	}
	public void rescale()
	{
		viewTransform.setToScale(scale, scale);
		for (int i = 0; i < levels.size(); i++) {
			levels.get(i).rescale();
		}
		
		for (int i = 0; i < levels.size(); i++) {
			levels.get(i).afterRescale();
		}
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

	public void render(Graphics2D g2d, boolean debug)
	{
		g2d.setTransform(viewTransform);
		this.levels.get(curLevel).render(g2d);
	}
	
	public Game getGame() {
		return game;
	}
}
