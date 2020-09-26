package com.wings2d.framework;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Level {
	/** List of objects that descend from GameObject */
	private List<GameObject> objects;
	/** List of UI elements */
	private List<UIElement> ui;
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
		objects = new ArrayList<GameObject>();
		ui = new ArrayList<UIElement>();
		
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
	public void addUI(UIElement ui)
	{
		this.ui.add(ui);
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
		for (int i = 0; i < ui.size(); i++)
		{
			ui.get(i).rescale();
		}
	}
	public void update(double dt, KeyState keys)
	{
		for (int i = 0; i < objects.size(); i++)
		{
			objects.get(i).update(dt, keys);
		}
	}
	public void updateUI(Point mouseClick)
	{
		for (int i = 0; i < ui.size(); i++)
		{
			ui.get(i).updateUI(mouseClick);
		}
	}
	public void render(Graphics2D g2d, boolean debug)
	{
		for (int i = 0; i < objects.size(); i++)
		{
			objects.get(i).render(g2d, debug);
		}
	}
	public void renderUI(Graphics2D g2d, boolean debug)
	{
		for (int i = 0; i < ui.size(); i++)
		{
			ui.get(i).renderUI(g2d, debug);
		}
	}
}
