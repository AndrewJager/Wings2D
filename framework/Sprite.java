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
	private boolean translated = false; // Used to prevent sprite from being moved more than once per update
	
	public Sprite(Joint...children)
	{
		joints = new ArrayList<Joint>();
		for (int i = 0; i < children.length; i++)
		{
			joints.add(children[i].copy());
		}
	}
	public Sprite(List<Joint> children)
	{
		this.joints = new ArrayList<Joint>();
		for (int i = 0; i < children.size(); i++)
		{
			this.joints.add(children.get(i).copy());
		}
	}
	public void translate(int x, int y)
	{
		for (int i = 0; i < joints.size(); i++)
		{
			joints.get(i).translate(x, y);
		}
		translated = true;
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

	public Sprite copy()
	{
		Sprite newSprite = new Sprite(joints);
		return newSprite;
	}
	public int getSpriteDelay() {
		return spriteDelay;
	}

	public void setSpriteDelay(int spriteDelay) {
		this.spriteDelay = spriteDelay;
	}
	
	public boolean getTranslated() {
		return translated;
	}

	public void setTranslated(boolean translated) {
		this.translated = translated;
	}
}
