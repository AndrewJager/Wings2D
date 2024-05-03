package com.wings2d.framework.core;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Scene {
	/** List of objects that descend from GameObject */
	private List<GameObject> objects;

	private String sceneId;
	
	private SceneManager manager;
	/**
	 * Class constructor
	 * @param thisLevel Identifier for this level
	 */
	public Scene(String sceneId)
	{
		this.sceneId = sceneId;
		objects = new ArrayList<GameObject>();
	}
	
	public void setManager(final SceneManager manager) {
		this.manager = manager;
	}
	
	public SceneManager getManager()
	{
		return this.manager;
	}
	
	public String getIdentifer()
	{
		return this.sceneId;
	}

	public void addObject(GameObject newObject)
	{
		this.objects.add(newObject);
	}
	public List<GameObject> getObjects()
	{
		return objects;
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
	
	/**
	 * Everything draw in this method will be drawn after render().
	 * Graphics transform is reset, and window scale is then applied.
	 */
	public void renderUI(final Graphics2D g2d) {
		
	}
}
