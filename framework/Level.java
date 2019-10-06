package framework;

import java.awt.Composite;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import entities.GameLevels;

public class Level {
	private List<GameObject> objects;
	private List<Button> ui;
	private GameLevels thisLevel;
	private LevelManager manager;
	
	public Level(GameLevels thisLevel)
	{
		this.thisLevel = thisLevel;
		objects = new ArrayList<GameObject>();
		ui = new ArrayList<Button>();
	}
	public LevelManager getManager()
	{
		return this.manager;
	}
	public void setManager(LevelManager manager)
	{
		this.manager = manager;
	}
	public GameLevels getLevel()
	{
		return this.thisLevel;
	}
	
	public void addObject(GameObject newObject)
	{
		this.objects.add(newObject);
	}
	public void addUI(Button newButton)
	{
		this.ui.add(newButton);
	}
	public void update(ArrayList<Integer> keys)
	{
		for (int i = 0; i < objects.size(); i++)
		{
			objects.get(i).update();
		}
		keys.clear();
	}
	public void updateUI(JFrame frame, boolean mouseDown)
	{
		for (int i = 0; i < ui.size(); i++)
		{
			ui.get(i).updateUI(frame, mouseDown);
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
