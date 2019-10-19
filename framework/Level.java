package framework;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import entities.GameLevels;
import entities.KeyState;

public class Level {
	private List<GameObject> objects;
	private List<Button> ui;
	private List<Wall> walls;
	private GameLevels thisLevel;
	private LevelManager manager;
	
	public Level(LevelManager manager, GameLevels thisLevel)
	{
		this.thisLevel = thisLevel;
		this.manager = manager;
		objects = new ArrayList<GameObject>();
		ui = new ArrayList<Button>();
		walls = new ArrayList<Wall>();
		
		manager.addLevel(this);
	}
	public LevelManager getManager()
	{
		return this.manager;
	}
	public GameLevels getLevel()
	{
		return this.thisLevel;
	}
	public List<Wall> getWalls()
	{
		return walls;
	}
	public void addUI(Button b)
	{
		this.ui.add(b);
	}
	public void addObject(GameObject newObject)
	{
		this.objects.add(newObject);
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
			Wall line = walls.get(i);
			g2d.setColor(Color.GREEN);
			g2d.drawLine((int)line.getLine().getX1(), (int)line.getLine().getY1(), (int)line.getLine().getX2(), (int)line.getLine().getY2());
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
