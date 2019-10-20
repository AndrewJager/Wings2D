package framework;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * A piece of the rig that makes up a sprite
 */
public class Joint {
	private double x, y, offsetX;
	/** Joints that will be moved relative to this joint */
	private List<Joint> children;
	/** Images to be draw at the location of this joint */
	private List<Image> images;
	/** Counter of how many joints currently exist */
	private static int jointCount = 0;
	/** Order in which joints are rendered, highest first */
	private int renderOrder = 1; 
	private Level level;
	/** This joint's parent */
	private Joint parent;
	
	/**
	 * Constructor for the "body" or main piece of the rig, all other joints are parented to this joint, directly or indirectly
	 * @param x X location of this joint
	 * @param y Y location of this joint
	 * @param level Level that this joint belongs to
	 */
	public Joint(double x, double y, Level level)
	{
		this.x = x;
		this.y = y;
		this.children = new ArrayList<Joint>();
		this.images = new ArrayList<Image>();
		this.level = level;
		jointCount = getJointCount() + 1;
		offsetX = 0;
	}
	/**
	 * Constructor for a joint parented to another joint
	 * @param parent Parent of this joint
	 * @param xOffset Distance on x-axis between this joint and it's parent
	 * @param yOffset Distance on y-axis between this joint and it's parent
	 */
	public Joint(Joint parent, double xOffset, double yOffset)
	{
		this(parent.getX() + (xOffset * parent.level.getManager().getScale()), 
				parent.getY() + (yOffset * parent.level.getManager().getScale()), parent.getLevel());
		parent.addChild(this);
		this.setRenderOrder(parent.getRenderOrder());
		this.offsetX = xOffset;
		this.parent = parent;
	}
	/** 
	 * Recursively searches for the highest level parent of this joint, and returns the x position of that joint
	 * @return X position the base joint of this joints rig.
	 */
	private double getBaseX()
	{
		Joint parent = this.parent;
		if (parent == null)
		{
			return 0;
		}
		else {
		boolean keepLooking = true;
			while (keepLooking)
			{
				if (parent.parent != null)
				{
					parent = parent.parent;
				}
				else
				{
					keepLooking = false;
				}
			}
			return parent.getX();
		}
	}
	/** 
	 *  Move this joint the specified distance
	 * @param x Distance to move along the x-axis
	 * @param y Distance to move along the y-axis
	 */
	public void translate(int x, int y)
	{
		this.x = this.x + x;
		this.y = this.y + y;
		for (int i = 0; i < images.size(); i++)
		{
			Image img = images.get(i);
			img.setX(img.getX() + x);
			img.setY(img.getY() + y);
		}
	}
	/**  
	 * Creates an copy of this joint, also copying the child joints and images
	 * @return A copy of this joint
	 */
	public Joint copy()
	{
		Joint newJoint = new Joint(this.x, this.y, this.level);
		newJoint.setRenderOrder(this.getRenderOrder());
		for (int i = 0; i < this.children.size(); i++)
		{
			newJoint.children.add(this.children.get(i).copy());
		}
		for (int i = 0; i < this.images.size(); i++)
		{
			newJoint.images.add(this.images.get(i).copy());
		}
		return newJoint;
	}
	/**  
	 * Top-level function to rotate this joint and all of its children
	 * @param angle Degrees to rotate
	 */
	public void rotate(double angle)
	{
		rotateJoint(this, this, angle);
		for (int i = 0; i < children.size(); i++)
		{
			rotateJoint(children.get(i), this, angle);
			children.get(i).rotateChildren(this, angle);
		}
	}
	/**  
	 * private function to rotate this joints children
	 * @param base Joint to rotate around
	 * @param angle Degrees to rotate
	 */
	private void rotateChildren(Joint base, double angle)
	{
		for (int i = 0; i < children.size(); i++)
		{
			rotateJoint(children.get(i), base, angle);
			children.get(i).rotateChildren(base, angle);
		}
	}
	/** 
	 * private function to handle the rotation of this joint. Could probably refactor joint rotation, as it currently uses three functions
	 * @param joint Joint to rotate
	 * @param base Joint to rotate around
	 * @param angle Degrees to rotate this joint
	 */
	
	private void rotateJoint(Joint joint, Joint base, double angle)
	{
		double x = Math.toRadians(angle);
		double newX = base.x + (joint.getX()-base.x)*Math.cos(x) - (joint.getY()-base.y)*Math.sin(x);
		double newY = base.y + (joint.getX()-base.x)*Math.sin(x) + (joint.getY()-base.y)*Math.cos(x);
		joint.setX(newX);
		joint.setY(newY);
		
		for (int i = 0; i < joint.images.size(); i++)
		{
			Image img = joint.images.get(i);
			img.rotate(angle);
		}
	}
	public void render(Graphics2D g2d, boolean debug)
	{
		int WIDTH = 3, HEIGHT = 3; //size of debug circles
		for (int i = 0; i < images.size(); i++)
		{
			Image img = images.get(i);
			img.setX(this.getX());
			img.setY(this.getY());
			img.render(g2d, debug);
		}
		if (debug)
		{
			Color c = g2d.getColor();
			g2d.setColor(Color.GREEN);
			g2d.drawArc((int)this.getX() - (WIDTH / 2), (int)this.getY() - (HEIGHT / 2), WIDTH, HEIGHT, 0, 360);
			g2d.setColor(c);
		}
	}
	/**
	 * "Mirror" this joint along the x-axis of the rig
	 * @param xPos X position of the sprite
	 */
	public void flip(double xPos)
	{
		double offset = getX() - getBaseX();
		this.x = (this.x - (offset * 2)) + xPos * 2;
		for (int i = 0; i < images.size(); i++)
		{
			images.get(i).flip();
		}
	}
	public void addImage(Image image)
	{
		images.add(image);
	}
	public void addChild(Joint child)
	{
		children.add(child);
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
	public double getOffsetX()
	{ return offsetX; }
	public List<Image> getImages() {
		return images;
	}
	public static int getJointCount() {
		return jointCount;
	}
	public int getRenderOrder() {
		return renderOrder;
	}
	public void setRenderOrder(int renderOrder) {
		this.renderOrder = renderOrder;
	}
	public Level getLevel()
	{
		return level;
	}
	public String toString()
	{
		return Math.round(this.x) + " " + Math.round(this.y);
	}
}
