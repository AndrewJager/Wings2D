package framework.animation;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import framework.Level;


public class Animation {
	private String name;
	/** SpriteSheet that this object belongs to */
	private SpriteSheet parent;
	/** Level that this Animation belongs to */
	private Level level;
	/** {@link framework.animRework.Frame Frames} for this Animation */
	private List<Frame> frames;
	
	public Animation(SpriteSheet parent, String name)
	{
		this.name = name;
		this.parent = parent;
		this.level = parent.getLevel();
		this.frames = new ArrayList<Frame>();
	}
	
	public void saveToFile(PrintWriter out)
	{
		out.write("ANIM:" + name + "\n");
		for (int i = 0; i < frames.size(); i++)
		{
			frames.get(i).saveToFile(out);
		}
		out.write("\n");
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
}
