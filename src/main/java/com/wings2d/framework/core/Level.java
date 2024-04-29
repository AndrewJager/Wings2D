package com.wings2d.framework.core;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class Level {
	/** List of objects that descend from GameObject */
	private List<GameObject> objects;

	/** Identifier for this level */
	private int thisLevel;
	private LevelManager manager;
	/**
	 * Class constructor
	 * @param manager Level Manager object
	 * @param thisLevel Identifier for this level
	 */
	public Level(LevelManager manager, int thisLevel)
	{
		this.thisLevel = thisLevel;
		this.manager = manager;
		manager.addLevel(this);
		objects = new ArrayList<GameObject>();
		
		manager.addLevel(this);
	}
	public LevelManager getManager()
	{
		return this.manager;
	}
	public int getIdentifer()
	{
		return this.thisLevel;
	}

	public void addObject(GameObject newObject)
	{
		this.objects.add(newObject);
	}
	public List<GameObject> getObjects()
	{
		return objects;
	}
	public void rescale()
	{
		for (int i = 0; i < objects.size(); i++)
		{
			objects.get(i).rescale();
		}
	}
	public void update(double dt)
	{
		for (int i = 0; i < objects.size(); i++)
		{
			objects.get(i).update(dt);
		}
	}

	public void render(final Graphics2D g2d)
	{
		for (int i = 0; i < objects.size(); i++)
		{
			objects.get(i).render(g2d, false);
		}
	}
	
	public void afterRescale() {
		
	}
}
