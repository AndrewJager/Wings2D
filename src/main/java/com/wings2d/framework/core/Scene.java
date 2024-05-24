package com.wings2d.framework.core;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class Scene {
	
	public enum BufferType {
		UPDATE_SAFE,
		UPDATING,
		RENDER_SAFE,
		RENDERING
	}
	public enum Buffer {
		A,
		B,
		C,
		D,
		UNASSIGNED
	}
	
	/** List of objects that descend from GameObject */
	private List<GameObject> objects;

	private String sceneId;
	protected
	Map<BufferType, Buffer> buffers;
	
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
		
		buffers = new HashMap<BufferType, Buffer>();
		buffers.put(BufferType.UPDATE_SAFE, Buffer.UNASSIGNED);
		buffers.put(BufferType.UPDATING, Buffer.A);
		buffers.put(BufferType.RENDER_SAFE, Buffer.UNASSIGNED);
		buffers.put(BufferType.RENDERING, Buffer.UNASSIGNED);
	}
	
	public abstract void update(double dt);
	public abstract void render(final Graphics2D g2d);
	public abstract void renderUI(final Graphics2D g2d);
	
	public void beforeUpdate() {
		
	}
	public void afterUpdate() {

	}
	public void beforeRender() {

	}
	public void afterRender() {
		
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
