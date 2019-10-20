package entities;

import java.awt.Color;

import javax.swing.JFrame;

import framework.GodRay;
import framework.Level;
import framework.LevelManager;
import framework.WallTypes;

public class TestLevel extends Level{
	private Player player;
	public TestLevel(LevelManager manager, GameLevels thisLevel) {
		super(manager, thisLevel);

		player = new Player(this);
		this.addObject(player);
		
		GodRay ray = new GodRay(0, 200, 0, 210, (768 * 1.2), 300, (768 * 1.2), 350, this, 30, new Color(240, 232, 14, 55));
		this.addObject(ray);
		
		this.addLine(10, 400, 400, 400, WallTypes.FLOOR);
		this.addLine(400, 400, 500, 300, WallTypes.RAMP);
		this.addLine(500, 300, 500, 100, WallTypes.WALL);
		this.addLine(60, 400, 10, 350, WallTypes.RAMP);
		this.addLine(10, 350, 10, 100, WallTypes.WALL);
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
