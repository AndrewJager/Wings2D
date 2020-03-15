package framework;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;


public class LevelManager {
	ArrayList<Level> levels = new ArrayList<Level>();
	private int curLevel;
	private KeyMapping keys;
	private double scale;
	private TextBox textBox;
	
	public LevelManager()
	{
		curLevel = 0; // First level, probably menu
		keys = new KeyMapping();
		keys.setKey("Enter", 10);
		keys.setKey("Left", 37);
		keys.setKey("Right", 39);
		keys.setKey("Esc", 27);
		keys.setKey("Jump", 38);
	}
	public void rescale()
	{
		for (int i = 0; i < levels.size(); i++)
		{
			levels.get(i).rescale();
		}
	}
	public KeyMapping getKeyMapping()
	{
		return this.keys;
	}
	public void setLevel(int newLevel)
	{
		curLevel = newLevel;
	}
	
	public void addLevel(Level newLevel)
	{
		levels.add(newLevel);
		levels.set(newLevel.getIdentifer(), newLevel);
	}
	public double getScale() {
		return scale;
	}
	public void setScale(double scale) {
		if (this.scale != scale)
		{
			this.scale = scale;
			rescale();
		}
		this.scale = scale;
	}
	public void update(KeyState keys, boolean mouseDown)
	{
		levels.get(curLevel).update(keys);
	}
	public void updateUI(Point mouseClick)
	{
		this.levels.get(curLevel).updateUI(mouseClick);
	}
	public void render(Graphics2D g2d, boolean debug)
	{
		this.levels.get(curLevel).render(g2d, debug);
	}
	public void renderUI(Graphics2D g2d, boolean debug)
	{
		this.levels.get(curLevel).renderUI(g2d, debug);
	}
	public TextBox getTextBox() {
		return textBox;
	}
	public void setTextBox(TextBox textBox) {
		this.textBox = textBox;
	}
}
