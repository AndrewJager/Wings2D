package framework;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class SpriteSheet extends GameObject{
	private List<Sprite> sprites;
	private int frame = 0;
	private int length, counter = 0, delay = 30;
	
	public SpriteSheet(Sprite...frames)
	{
		sprites = new ArrayList<Sprite>();
		for(int i = 0; i < frames.length; i++)
		{
			this.sprites.add(frames[i]);
		}
		length = sprites.size();
	}
	
	public SpriteSheet(int delay, Sprite...frames)
	{
		this(frames);
		this.delay = delay;
	}
	
	public void update()
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
	
	public void render(Graphics2D g2d, boolean debug)
	{
		this.sprites.get(frame).render(g2d, debug);
	}

}
