package framework;

import java.util.List;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * A collection of joints
 */
public class Sprite {
	private List<Joint> joints;
	private int spriteDelay = 0; // If not zero, the time to show this frame before changing to the next
	
	public Sprite(Joint...children)
	{
		joints = new ArrayList<Joint>();
		for (int i = 0; i < children.length; i++)
		{
			joints.add(children[i].copy());
		}
	}
	
	public void translate(int x, int y)
	{
		for (int i = 0; i < joints.size(); i++)
		{
			joints.get(i).translate(x, y);
		}
	}
	
	public void render(Graphics2D g2d, boolean debug)
	{
		for (int i = 0; i < joints.size(); i++)
		{
			joints.get(i).render(g2d, debug);
		}
	}
	
	public void addJoint(Joint joint)
	{
		this.joints.add(joint);
	}

	public int getSpriteDelay() {
		return spriteDelay;
	}

	public void setSpriteDelay(int spriteDelay) {
		this.spriteDelay = spriteDelay;
	}
}
