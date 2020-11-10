package com.wings2d.framework.animation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.wings2d.framework.GameObject;
import com.wings2d.framework.KeyState;
import com.wings2d.framework.Level;
import com.wings2d.framework.Utils;
import com.wings2d.framework.imageFilters.BasicVariance;
import com.wings2d.framework.imageFilters.BlurEdges;
import com.wings2d.framework.imageFilters.DarkenFrom;
import com.wings2d.framework.imageFilters.ImageFilter;
import com.wings2d.framework.imageFilters.LightenFrom;
import com.wings2d.framework.imageFilters.ShadeDir;


public class SpriteSheet extends GameObject{
	/** Level that this SpriteSheet belongs to */
	private Level level;
	private List<Animation> animations;
	private String name;
	private double x;
	private double y;
	private int curAnim = 0;
	
	public SpriteSheet(String name, Level level)
	{
		this.name = name;
		this.level = level;
		this.animations = new ArrayList<Animation>();
	}
	
	public SpriteSheet(Scanner in, Level level)
	{
		this.animations = new ArrayList<Animation>();
		this.level = level;
		x = 100;
		y = 100;
		
		in.useDelimiter(Pattern.compile("(\\n)")); // Regex. IDK
		
		// Temp objects
		Animation newAnim;
		Frame newFrame;
		Joint newJoint;
		List<Frame> frames;
		List<Joint> joints;
		
		while(in.hasNext())
		{
			String line = in.next();
			if (line.strip() != "")
			{
				String[] split = line.split(":");
				String token = split[0];
				String value = split[1];
				
				switch (token)
				{
					case "SPRITE":
						this.name = value;
						break;
					case "ANIM":
						newAnim = new Animation(this, value);
						animations.add(newAnim);
						break;
					case "FRAME":
						newFrame = new Frame(animations.get(animations.size() - 1), value);
						frames = animations.get(animations.size() - 1).getFrames();
						frames.add(newFrame);
						break;
					case "TIME":
						frames = animations.get(animations.size() - 1).getFrames();
						frames.get(frames.size() - 1).setFrameTime(Integer.parseInt(value));
						break;
					case "JOINT":
						frames = animations.get(animations.size() - 1).getFrames();
						newJoint = new Joint(frames.get(frames.size() - 1), value);
						frames.get(frames.size() - 1).addJoint(newJoint);
						break;
					case "POSITION":
						String[] loc = value.split(";");
						frames = animations.get(animations.size() - 1).getFrames();
						joints = frames.get(frames.size() - 1).getJoints();
						joints.get(joints.size() - 1).setX(Double.parseDouble(loc[0]));
						joints.get(joints.size() - 1).setY(Double.parseDouble(loc[1]));
						break;
					case "ORDER":
						frames = animations.get(animations.size() - 1).getFrames();
						joints = frames.get(frames.size() - 1).getJoints();
						joints.get(joints.size() - 1).setRenderOrder(Integer.parseInt(value));
						break;
					case "POINTS":
						String[] points = value.split(";");
						for (int i = 0; i < points.length; i++)
						{
							String[] coords = points[i].split(",");
							frames = animations.get(animations.size() - 1).getFrames();
							joints = frames.get(frames.size() - 1).getJoints();
							joints.get(joints.size() - 1).addPoint(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]));
						}
						break;
					case "COLOR":
						Color newColor = Utils.stringToColor(value, ";");
						frames = animations.get(animations.size() - 1).getFrames();
						joints = frames.get(frames.size() - 1).getJoints();
						joints.get(joints.size() - 1).setColor(newColor);
						break;
					case "FILTERS":
						String[] filters = value.split(";");
						frames = animations.get(animations.size() - 1).getFrames();
						joints = frames.get(frames.size() - 1).getJoints();
						Joint curItem = joints.get(joints.size() - 1);
						for (int i = 0; i < filters.length; i++)
						{
							String filter = filters[i];
							String[] filterParts = filter.split(ImageFilter.DELIMITER);
							switch (filterParts[0])
							{
								case BasicVariance.fileTitle:
									BasicVariance basicVar = new BasicVariance(Integer.parseInt(filterParts[1]));
									curItem.addFilter(basicVar);
									break;
								case BlurEdges.fileTitle:
									curItem.addFilter(new BlurEdges());
									break;
								case DarkenFrom.fileTitle:
									ShadeDir dir = ShadeDir.createFromString(filterParts[1]);
									DarkenFrom dark = new DarkenFrom(dir, Double.parseDouble(filterParts[2]));
									curItem.addFilter(dark);
									break;
								case LightenFrom.fileTitle:
									ShadeDir alsoDir = ShadeDir.createFromString(filterParts[1]);
									LightenFrom light = new LightenFrom(alsoDir, Double.parseDouble(filterParts[2]));
									curItem.addFilter(light);
									break;
							}
						}
						break;
				}
			}
		}
		
		for(int i = 0; i < animations.size(); i++)
		{
			animations.get(i).generateImages();
		}
	}
	
	public String getName()
	{
		return name;
	}
	public Level getLevel()
	{
		return level;
	}
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Animation getAnimation(int anim)
	{
		return animations.get(anim);
	}
	
	public List<Animation> getAnimations()
	{
		return animations;
	}
	public Animation getAnimByName(String name)
	{
		for (int i = 0; i < animations.size(); i++)
		{
			if (animations.get(i).getName().equals(name))
			{
				return animations.get(i);
			}
		}
		return null; // No object found
	}
	/** 
	 * Get an array of the animation names for use in the editor (requires data in array rather than list)
	 * @return String[] containing all Animation names
	 */
	public String[] getAnimNames()
	{
		String[] names = new String[animations.size()];
		for (int i = 0; i < animations.size(); i++)
		{
			names[i] = animations.get(i).getName();
		}
		return names;
	}
	
	public void addNewAnimation(String animName)
	{
		animations.add(new Animation(this, animName));
	}
	
	@Override
	public void render(Graphics2D g2d, boolean debug)
	{
		this.animations.get(curAnim).render(g2d, debug);
	}
	
	@Override
	public void update(double dt, KeyState keys)
	{
		this.animations.get(curAnim).update(dt, keys);
	}

	@Override
	public void rescale() {
		// TODO Auto-generated method stub
		
	}
}
