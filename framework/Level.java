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
	private JFrame frame;
	
	public Level(GameLevels thisLevel, JFrame frame)
	{
		this.thisLevel = thisLevel;
		this.frame = frame;
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
	public void update(ArrayList<Integer> keys)
	{
		for (int i = 0; i < objects.size(); i++)
		{
			objects.get(i).update();
		}
		keys.clear();
	}
	public void render(Graphics2D g2d, boolean debug)
	{
		for (int i = 0; i < objects.size(); i++)
		{
			objects.get(i).render(g2d, debug);
		}
	}
}
