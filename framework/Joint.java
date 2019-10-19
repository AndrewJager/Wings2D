package framework;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Joint {
	private double x, y;
	private List<Joint> children;
	private List<Image> images;
	private static int jointCount = 0;
	private int renderOrder = 1; // Order in which joints get rendered, highest first
	private Level level;
	
	public Joint(double x, double y, Level level)
	{
		double scale = level.getManager().getScale();
		this.x = x;
		this.y = y;
		this.children = new ArrayList<Joint>();
		this.images = new ArrayList<Image>();
		this.level = level;
		jointCount = getJointCount() + 1;
	}
	public Joint(Joint parent, double xOffset, double yOffset)
	{
		this(parent.getX() + (xOffset * parent.level.getManager().getScale()), 
				parent.getY() + (yOffset * parent.level.getManager().getScale()), parent.getLevel());
		parent.addChild(this);
		this.setRenderOrder(parent.getRenderOrder());
	}
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
	
	public void rotate(double angle)
	{
		rotateJoint(this, this, angle);
		for (int i = 0; i < children.size(); i++)
		{
			rotateJoint(children.get(i), this, angle);
			children.get(i).rotateChildren(this, angle);
		}
	}
	
	private void rotateChildren(Joint base, double angle)
	{
		for (int i = 0; i < children.size(); i++)
		{
			rotateJoint(children.get(i), base, angle);
			children.get(i).rotateChildren(base, angle);
		}
	}
	
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
