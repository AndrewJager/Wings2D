package entities;

import javax.swing.JFrame;

import framework.Level;
import framework.WallTypes;

public class TestLevel extends Level{
	private Player player;
	public TestLevel(GameLevels thisLevel, JFrame frame) {
		super(thisLevel, frame);

		player = new Player(this);
		this.addObject(player);
		
		this.addLine(10, 400, 400, 400, WallTypes.FLOOR);
		this.addLine(400, 400, 500, 300, WallTypes.RAMP);
		this.addLine(500, 300, 500, 100, WallTypes.WALL);
		this.addLine(50, 400, 0, 350, WallTypes.RAMP);
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
