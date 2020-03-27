package framework;

import java.util.List;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A collection of joints
 */
public class Sprite {
	private List<Joint> joints;
	/**
	 * The time this sprite will display for before going onto the next sprite.
	 * If 0 (the default) it will use the default time in the spritesheet
	 */
	private int spriteDelay = 0; // If not zero, the time to show this frame before changing to the next
	private boolean translated = false; // Used to prevent sprite from being moved more than once per update
	
	/**
	 * Create a sprite from an indeterminate number of joints, and export those joints to the list parameter 
	 * @param jointList Pass in as an empty list. The constructor will export all joints added here to this list.
	 * @param joints Joints to copy into sprite
	 */
	public Sprite(List<Joint> jointList, Joint...joints)
	{
		this.joints = new ArrayList<Joint>();
		for (int i = 0; i < joints.length; i++)
		{
			this.joints.add(joints[i].copy());
			jointList.add(joints[i]); //Make list of all references to joints
		}
		Collections.sort(this.joints, new JointComparer());
		Collections.sort(jointList, new JointComparer());
	}
	/**
	 * Create a sprite from a list of joints
	 * @param joints Joints to copy into sprite
	 */
	public Sprite(List<Joint> joints)
	{
		this.joints = new ArrayList<Joint>();
		for (int i = 0; i < joints.size(); i++)
		{
			this.joints.add(joints.get(i).copy());
		}
	}
	/**
	 * Create a sprite with no joints
	 */
	public Sprite()
	{
		this.joints = new ArrayList<Joint>();
	}
	/**
	 * Returns a new copy of this sprite mirrored along the x-axis
	 * @param xPos the current x location of the sprite
	 * @return A flipped version of this sprite
	 */
	public Sprite getFlipped(double xPos)
	{
		Sprite newSprite = new Sprite(joints);
		for (int i = 0; i < newSprite.getJoints().size(); i++)
		{
			newSprite.getJoints().get(i).flip(xPos);
		}
		Collections.sort(joints, new InvertJointComparer());
		return newSprite;
	}
	/**
	 * Move this sprite by the desired distances
	 * @param x Distance to move sprite along x-axis
	 * @param y Distance to move sprite along y-axis
	 */
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
	public List<Joint> getJoints() {
		return joints;
	}
}

/**
 * Used for sorting joints by render order
 */
class JointComparer implements Comparator<Joint> {
    public int compare(Joint j1, Joint j2) {
        return j1.getRenderOrder() - j2.getRenderOrder();
    }
}
class InvertJointComparer implements Comparator<Joint> {
    public int compare(Joint j1, Joint j2) {
        return j2.getRenderOrder() - j1.getRenderOrder();
    }
}