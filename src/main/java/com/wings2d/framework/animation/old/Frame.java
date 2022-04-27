package com.wings2d.framework.animation.old;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.wings2d.framework.Level;



public class Frame {
	/** Name of this frame */
	private String name;
	/** Animation that this Frame belongs to */
	private Animation parent;
	/** Level that this Frame belongs to */
	private Level level;
	/** The joints for this frame */
	private List<Joint> joints;
	/** Time this frame will display for */
	private double frameTime;
	
	public Frame(Animation parent, String name)
	{
		this.name = name;
		this.parent = parent;
		this.level = parent.getLevel();
		this.joints = new ArrayList<Joint>();
		this.frameTime = 0; // Use the Animations delay time by default
	}
	
	public Frame (Animation parent, String name, Frame editParent)
	{
		this(parent, name);
		
		for (int i = 0; i < editParent.getJoints().size(); i++)
		{
			this.addJoint(editParent.getJoint(i).copy());
		}
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return name;
	}
	public Animation getAnimation()
	{
		return parent;
	}
	public Level getLevel()
	{
		return level;
	}
	public void addJoint(Joint joint)
	{
		joints.add(joint);
	}
	public void addNewJoint(String jointName)
	{
		joints.add(new Joint(this, jointName));
	}
	public List<Joint> getJoints()
	{
		return joints;
	}
	public Joint getJoint(int joint)
	{
		return joints.get(joint);
	}
	
	public Joint getJointByName(String name)
	{
		for (int i = 0; i < joints.size(); i++)
		{
			if (joints.get(i).getName().equals(name))
			{
				return joints.get(i);
			}
		}
		return null; // No object found
	}
	public double getFrameTime() {
		return frameTime;
	}

	public void setFrameTime(double frameTime) {
		this.frameTime = frameTime;
	}
	public void generateImages()
	{
		for (int i = 0; i < joints.size(); i++)
		{
			joints.get(i).makeImage();
		}
	}


	public void render(Graphics2D g2d, boolean debug)
	{
		for(int i = 0; i < joints.size(); i++)
		{
			joints.get(i).render(g2d, debug);
		}
	}
}