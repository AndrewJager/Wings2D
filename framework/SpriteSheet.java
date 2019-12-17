package framework;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import game.KeyState;

/**
 * A list of Sprites played in sequence to make an animation
 */
public class SpriteSheet extends GameObject{
	private List<Sprite> sprites;
	private int frame = 0;
	private int length, counter = 0, delay = 30;
	private boolean translated = false; // Used to prevent sprite from being moved more than once per update
	/**
	 * SpriteSheet constructor with default delay
	 * @param frames Indeterminate amount of sprites to add to spritesheet
	 */
	public SpriteSheet(Sprite...frames)
	{
		sprites = new ArrayList<Sprite>();
		for(int i = 0; i < frames.length; i++)
		{
			this.sprites.add(frames[i].copy());
		}
		length = sprites.size();
	}
	/**
	 * SpriteSheet constructor with custom delay.
	 * @param delay Time to wait between frames. Overridden to any custom time in sprites
	 * @param frames Indeterminate amount of Sprites to add to SpriteSheet
	 */
	public SpriteSheet(int delay, Sprite...frames)
	{
		this(frames);
		this.delay = delay;
	}
	
	/**
	 * Set frame to 0
	 */
	public void reset()
	{
		frame = 0;
	}
	public void update(KeyState keys)
	{
		counter++;
		if (this.sprites.get(frame).getSpriteDelay() != 0)
		{
			if (counter >= this.sprites.get(frame).getSpriteDelay())
			{
				counter = 0;
				frame++;
			}
		}
		else
		{
			if (counter >= delay)
			{
				counter = 0;
				frame++;
			}
		}
		if (frame >= length)
		{
			frame = 0;
		}
	}
	
	public void translate(int x, int y)
	{
		for (int i = 0; i < sprites.size(); i++)
		{
			sprites.get(i).setTranslated(false);
		}
		for (int i = 0; i < sprites.size(); i++)
		{
			if (!sprites.get(i).getTranslated())
			{
				sprites.get(i).translate(x, y);
			}
		}
		translated = true;
	}
	public boolean getTranslated() {
		return translated;
	}

	public void setTranslated(boolean translated) {
		this.translated = translated;
	}
	public void render(Graphics2D g2d, boolean debug)
	{
		this.sprites.get(frame).render(g2d, debug);
	}
	
	// Functions to comply with GameObject requirements
	public double getX(){ return 0 ;}
	public double getY(){ return 0; }
	public void translate(double xVel, double yVel) {};
}