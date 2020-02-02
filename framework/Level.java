package framework;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import game.KeyState;

public class Level {
	/** List of objects that descend from GameObject */
	private List<GameObject> objects;
	/** List of UI elements */
	private List<UIElement> ui;
	/** List of static level components */
	private List<Wall> walls;
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
		walls = new ArrayList<Wall>();
		
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
	public List<Wall> getWalls()
	{
		return walls;
	}
	public void addUI(UIElement ui)
	{
		this.ui.add(ui);
	}
	public void addObject(GameObject newObject)
	{
		this.objects.add(newObject);
	}
	public void addWall(Wall wall)
	{
		walls.add(wall);
	}
	public void addLine(double x1, double y1, double x2, double y2, WallTypes type)
	{
		Wall wall = new Wall(this, x1, y1, x2, y2, type);
		walls.add(wall);
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
		for (int i = 0; i < walls.size(); i++)
		{
			Wall wall = walls.get(i);
			wall.render(g2d, debug);
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
