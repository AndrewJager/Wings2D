package entities;

import java.awt.Color;

import framework.GodRay;
import framework.ImageFilter;
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
		
		GodRay ray = new GodRay(40, 0, 55, 0,
				500, 230, 500, 280, this, 30, new Color(240, 232, 14, 25));
		this.addObject(ray);
		
		Wall floor = new Wall(this, 10, 400, 400, 400, WallTypes.FLOOR);
		floor.setBackground(10, Color.BLUE, false);
		this.addWall(floor);
		
		Wall rRamp = new Wall(this, 400, 400, 500, 300, WallTypes.RAMP);
		rRamp.setBackground(20, Color.BLUE, false);
		this.addWall(rRamp);
		
		Wall lRamp = new Wall(this, 10, 350, 60, 400, WallTypes.RAMP);
		lRamp.setBackground(10, Color.BLUE, false);
		ImageFilter.basicVariance(lRamp.getBackground(), 50);
		ImageFilter.lightenFrom(lRamp.getBackground(), ImageFilter.ShadeDir.LEFT, 0.8);
		ImageFilter.darkenFrom(lRamp.getBackground(), ImageFilter.ShadeDir.RIGHT, 0.8);
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
