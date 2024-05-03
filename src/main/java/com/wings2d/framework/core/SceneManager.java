package com.wings2d.framework.core;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.HashMap;


@SuppressWarnings("serial")
public class SceneManager extends HashMap<String, Scene>{
	private Scene activeScene;
	private double scale;
	private Game game;
	private AffineTransform viewTransform;
	private AffineTransform uiTransform;
	
	public SceneManager(final Game game)
	{
		super();
		
		this.game = game;
		
		activeScene = null; 
		scale = 1;
		viewTransform = new AffineTransform();
		uiTransform = new AffineTransform();
	}
	
	public void rescale()
	{
		viewTransform.setToScale(scale, scale);
		uiTransform.setToScale(scale, scale);
	}
	
	public void setLevel(final String sceneId)
	{
		activeScene = this.get(sceneId);
	}
	
	public void addLevel(Scene newScene)
	{
		this.put(newScene.getIdentifer(), newScene);
		newScene.setManager(this);
		
		// By default, set the first scene added to the active scene
		if (activeScene == null) {
			activeScene = newScene;
		}
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
		activeScene.update(dt);
	}

	public void render(Graphics2D g2d, boolean debug)
	{
		g2d.setTransform(viewTransform);
		activeScene.render(g2d);
		
		g2d.setTransform(uiTransform);
		activeScene.renderUI(g2d);
	}
	
	public Game getGame() {
		return game;
	}
}
