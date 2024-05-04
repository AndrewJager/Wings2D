package com.wings2d.framework.core;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class Scene {
	/** List of objects that descend from GameObject */
	private List<GameObject> objects;

	private String sceneId;
	
	private SceneManager manager;
	
	protected Consumer<MouseEvent> mouseClicked = null;
	protected Consumer<MouseEvent> mousePressed = null;
	protected Consumer<MouseEvent> mouseReleased = null;
	protected Consumer<MouseEvent> mouseEntered = null;
	protected Consumer<MouseEvent> mouseExited = null;
	protected Consumer<MouseEvent> mouseDragged = null;
	protected Consumer<MouseEvent> mouseMoved = null;
	protected Consumer<MouseWheelEvent> mouseWheelMoved = null;
	
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
			objects.get(i).render(g2d);
		}
	}
	
	/**
	 * Everything draw in this method will be drawn after render().
	 * Graphics transform is reset, and window scale is then applied.
	 */
	public void renderUI(final Graphics2D g2d) {
		
	}
	
	
	// Mouse events
	public Consumer<MouseEvent> getMouseClicked() {return mouseClicked;}
	public Consumer<MouseEvent> getMousePressed() {return mousePressed;}
	public Consumer<MouseEvent> getMouseReleased() {return mouseReleased;}
	public Consumer<MouseEvent> getMouseEntered() {return mouseEntered;}
	public Consumer<MouseEvent> getMouseExited() {return mouseExited;}
	public Consumer<MouseEvent> getMouseDragged() {return mouseDragged;}
	public Consumer<MouseEvent> getMouseMoved() {return mouseMoved;}
	public Consumer<MouseWheelEvent> getMouseWheelMoved() {return mouseWheelMoved;}
}
