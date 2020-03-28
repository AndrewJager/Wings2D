package framework.text;

import java.awt.Graphics2D;

import framework.GameObject;
import framework.KeyState;
/**
 * Uses the DisplayableText interface to display an object of any class that implements this interface
 */
public class TextDisplay extends GameObject{
	private DisplayableText text;
	
	public TextDisplay(DisplayableText text, double x, double y)
	{
		this.text = text;
		this.text.setX(x);
		this.text.setY(y);
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
		// TODO Auto-generated method stub
		
	}
}
