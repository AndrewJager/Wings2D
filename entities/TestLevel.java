package entities;

import java.awt.Color;

import framework.GodRay;
import framework.Level;
import framework.LevelManager;
import framework.Wall;
import framework.WallTypes;
import framework.imageFilters.BasicVariance;
import framework.imageFilters.ImageFilter;
import framework.imageFilters.LightenFrom;
import framework.imageFilters.Outline;
import framework.imageFilters.ShadeDir;

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
		floor.setBackground(10, Color.DARK_GRAY, false);
		floor.getBackground().addFilter(new BasicVariance(45));
		floor.getBackground().addFilter(new LightenFrom(ShadeDir.TOP, 3));
		this.addWall(floor);
		
		Wall rRamp = new Wall(this, 400, 400, 500, 300, WallTypes.RAMP);
		rRamp.setBackground(20, Color.BLUE, false);
		this.addWall(rRamp);
		
		Wall lRamp = new Wall(this, 10, 350, 60, 400, WallTypes.RAMP);
		lRamp.setBackground(10, Color.BLUE, false);
		lRamp.getBackground().addFilter(new BasicVariance(90));
		lRamp.getBackground().addFilter(new LightenFrom(ShadeDir.LEFT, 1));
		this.addWall(lRamp);
		
		Wall rWall = new Wall(this, 500, 100, 500, 300, WallTypes.WALL);
		rWall.setBackground(15, Color.DARK_GRAY, false);
		rWall.getBackground().addFilter(new BasicVariance(25));
		rWall.getBackground().addFilter(new LightenFrom(ShadeDir.TOP, 0.4));
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
