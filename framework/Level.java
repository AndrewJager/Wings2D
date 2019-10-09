package framework;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import entities.GameLevels;
import entities.KeyState;

public class Level {
	private List<GameObject> objects;
	private List<Button> ui;
	private GameLevels thisLevel;
	private LevelManager manager;
	
	public Level(GameLevels thisLevel, JFrame frame)
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
	public void addUI(Button b)
	{
		this.ui.add(b);
	}
	public void addObject(GameObject newObject)
	{
		this.objects.add(newObject);
	}
	public void update(KeyState keys)
	{
		for (int i = 0; i < objects.size(); i++)
		{
			objects.get(i).update(keys);
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
