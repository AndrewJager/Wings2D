package framework.text;

import java.awt.Font;
import java.awt.Graphics2D;

import framework.GameObject;
import framework.KeyState;
import framework.Level;
/**
 * Uses the DisplayableText interface to display an object of any class that implements this interface
 */
public class TextDisplay extends GameObject{
	private DisplayableText text;
	private Level level;
	
	public TextDisplay(DisplayableText text, double textSize, double x, double y, Level level)
	{
		this(text, new Font("TimesRoman", Font.PLAIN, (int)textSize), textSize, x, y, level);
	}
	
	public TextDisplay(DisplayableText text, Font font, double textSize, double x, double y, Level level)
	{
		this.text = text;
		this.text.setFont(font);
		this.text.setTextSize(textSize);
		this.text.setOgTextSize(textSize);
		this.text.setX(x);
		this.text.setY(y);
		this.text.setOgX(x);
		this.text.setOgY(y);
		this.level = level;
	}
	

	@Override
	public void update(double dt, KeyState keys)
	{
		text.update(dt);
	}
	@Override
	public void render(Graphics2D g2d, boolean debug)
	{
		text.render(g2d);
	}
	@Override
	public void rescale() {
		double scale = level.getManager().getScale();
		text.setTextSize(text.getOgTextSize() * scale);
		text.setX(text.getOgX() * scale);
		text.setY(text.getOgY() * scale);
		text.setFont(new Font(text.getFont().getName(), text.getFont().getStyle(), (int)text.getTextSize()));
	}
}
