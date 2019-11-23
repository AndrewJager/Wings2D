package entities;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;

import framework.GodRay;
import framework.Image;
import framework.Level;
import framework.LevelManager;
import framework.Wall;
import framework.WallTypes;

public class TestLevel extends Level{
	private Player player;
	public TestLevel(LevelManager manager, GameLevels thisLevel) {
		super(manager, thisLevel);

		player = new Player(this);
		this.addObject(player);
		
		GodRay ray = new GodRay(0, 200, 0, 210, (768 * 1.2), 300, (768 * 1.2), 350, this, 30, new Color(240, 232, 14, 25));
		this.addObject(ray);
		
		Wall floor = new Wall(this, 10, 400, 400, 400, WallTypes.FLOOR);
		floor.setBackground(10, Color.BLUE, false);
		this.addWall(floor);
		
		Wall rRamp = new Wall(this, 400, 400, 500, 300, WallTypes.RAMP);
		rRamp.setBackground(20, Color.BLUE, false);
		this.addWall(rRamp);
		
		Wall lRamp = new Wall(this, 10, 350, 60, 400, WallTypes.RAMP);
		lRamp.setBackground(10, Color.BLUE, false);
		this.addWall(lRamp);
		
		Wall rWall = new Wall(this, 500, 100, 500, 300, WallTypes.WALL);
		rWall.setBackground(15, Color.BLUE, false);
		this.addWall(rWall);
		Wall lWall = new Wall(this, 10, 100, 10, 350, WallTypes.WALL);
		lWall.setBackground(15, Color.BLUE, true);
		this.addWall(lWall);
	}
	@Override
	public void update(KeyState keys)
	{
		if (keys.esc_key)
		{
			this.getManager().setLevel(GameLevels.MENU);
		}
		
		super.update(keys);
	}
}
