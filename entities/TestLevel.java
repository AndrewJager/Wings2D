package entities;

import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;

import javax.swing.JFrame;

import framework.CustomWindow;
import framework.Image;
import framework.Joint;
import framework.KeyMapping;
import framework.Level;
import framework.LevelManager;
import framework.Shape;
import framework.Sprite;
import framework.SpriteSheet;

public class TestLevel extends Level{
	private Player player;
	public TestLevel(GameLevels thisLevel, JFrame frame) {
		super(thisLevel, frame);

		player = new Player(this);
		
		this.addObject(player);
	}
	@Override
	public void update(KeyState keys)
	{
		KeyMapping mapping = this.getManager().getKeyMapping();
//		if (keys.contains(mapping.getKey("Esc")))
//		{
//			this.getManager().setLevel(GameLevels.MENU);
//		}
		
		
		super.update(keys);
	}
}
