package framework;

import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;

import entities.GameLevels;

public class LevelManager {
	ArrayList<Level> levels = new ArrayList<Level>();
	private GameLevels curLevel;
	private KeyMapping keys;
	
	public LevelManager()
	{
		curLevel = GameLevels.MENU;
		keys = new KeyMapping();
		keys.setKey("Enter", 10);
		keys.setKey("Left", 37);
		keys.setKey("Right", 39);
		keys.setKey("Esc", 27);
	}
	public KeyMapping getKeyMapping()
	{
		return this.keys;
	}
	public void setLevel(GameLevels newLevel)
	{
		curLevel = newLevel;
	}
	
	public void addLevel(Level newLevel)
	{
		levels.add(newLevel);
		levels.set(newLevel.getLevel().ordinal(), newLevel);
		newLevel.setManager(this);
	}
	public void update(ArrayList<Integer> keys, boolean mouseDown)
	{
		levels.get(curLevel.ordinal()).update(keys);
	}
	
	public void render(Graphics2D g2d, boolean debug)
	{
		this.levels.get(curLevel.ordinal()).render(g2d, debug);
	}
}
