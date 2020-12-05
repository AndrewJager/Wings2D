package com.wings2d.framework.animation;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.wings2d.framework.KeyState;
import com.wings2d.framework.Level;


public class Animation {
	private String name;
	/** SpriteSheet that this object belongs to */
	private SpriteSheet parent;
	/** Level that this Animation belongs to */
	private Level level;
	private List<Frame> frames;
	/** Keep track of the time from last frame change */
	private double timeCount;
	/** Current frame */
	private int curFrame;
	/** Default time to show frames */
	private double delay;
	
	public Animation(SpriteSheet parent, String name)
	{
		this.name = name;
		this.parent = parent;
		this.level = parent.getLevel();
		this.frames = new ArrayList<Frame>();
		this.timeCount = 0;
		this.curFrame = 0;
		this.delay = 25;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return name;
	}
	
	public SpriteSheet getSpriteSheet()
	{
		return parent;
	}
	public Level getLevel()
	{
		return level;
	}
	public void addNewFrame(String frameName)
	{
		frames.add(new Frame(this, frameName));
	}
	public void addNewFrame(String frameName, Frame editParent)
	{
		frames.add(new Frame(this, frameName, editParent));
	}
	public Frame getFrame(int frame)
	{
		return frames.get(frame);
	}
	public List<Frame> getFrames()
	{
		return frames;
	}

	public Frame getFrameByName(String name)
	{
		for (int i = 0; i < frames.size(); i++)
		{
			if (frames.get(i).getName().equals(name))
			{
				return frames.get(i);
			}
		}
		return null; // No object found
	}
	
	public String[] getFrameNames()
	{
		String[] names = new String[frames.size()];
		for (int i = 0; i < frames.size(); i++)
		{
			names[i] = frames.get(i).getName();
		}
		return names;
	}
	
	public int getCurrentFrame()
	{
		return curFrame;
	}
	
	public void generateImages()
	{
		for (int i = 0; i < frames.size(); i++)
		{
			frames.get(i).generateImages();
		}
	}
	
	public void update(double dt, KeyState keys)
	{
		timeCount += dt;
		if (this.frames.get(curFrame).getFrameTime() != 0)
		{
			if (timeCount >= this.frames.get(curFrame).getFrameTime())
			{
				timeCount = 0;
				curFrame++;
			}
		}
		else
		{
			if (timeCount >= delay)
			{
				timeCount = 0;
				curFrame++;
			}
		}
		if (curFrame >= frames.size())
		{
			curFrame = 0;
		}
	}
	
	public void render(Graphics2D g2d, boolean debug)
	{
		frames.get(curFrame).render(g2d, debug);
	}
}
