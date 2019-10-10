package framework;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Point2D;

public class Image {
	private Shape shape, ogShape;
	private BufferedImage image;
	private int width, height;
	private double x, y;
	private Color color;
	private double rotation = 0;
	private static int imageCount = 0;

	public Image(Shape shape, Color color)
	{
		imageCount = getImageCount() + 1;
		this.x = 0;
		this.y = 0;
		this.shape = shape;
		this.ogShape = shape;
		this.width = shape.getBounds().width ;
		this.height = shape.getBounds().height ;
		this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		this.color = color;
		for(int x = 0; x < width; x++) {
		    for(int y = 0; y < height; y++) {
		    	if (shape.contains(new Point(x, y)))
		    	{
		    		image.setRGB(x, y, this.color.getRGB());
		    	}
		    	else
		    	{
		    		image.setRGB(x, y, Color.TRANSLUCENT);
		    	}
		    }
		}
	}
	
	public Image(int x, int y) {
		this.width = x;
		this.height = y;
		this.image = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
	}

	public Image copy()
	{
		Image newImage = new Image(this.width, this.height);
		newImage.color = this.color;
		newImage.x = this.x;
		newImage.y = this.y;
		newImage.rotation = this.rotation;
		for (int x = 0; x < width; x++) 
		{
			for(int y = 0; y < height; y++) {
				newImage.image.setRGB(x, y, this.image.getRGB(x, y));
			}
		}
		return newImage;
	}

	public void rotate(double angle) {
		this.image = null;
		this.rotation += angle;
		Shape newShape = new Shape();
		for (int i = 0; i < this.ogShape.npoints; i++)
		{
			int x = this.ogShape.xpoints[i];
			int y = this.ogShape.ypoints[i];
			newShape.addPoint(x, y);
		}
		Point2D center = new Point2D.Double(this.getCenterX(), this.getCenterY());
		
		for (int i = 0; i < newShape.npoints; i++)
		{
			Point2D point = new Point2D.Double(newShape.xpoints[i], newShape.ypoints[i]);
			getRotatedPoint(point, center, this.rotation);
			newShape.xpoints[i] = (int)point.getX();
			newShape.ypoints[i] = (int)point.getY();
		}
		newShape.invalidate(); //Refreshes the object cache, or something. IDK, but I need it here
		
		// Rotated shape ends up offset for some reason. I have no idea why I have to do this.
		int xOff = (int)newShape.getBounds().getX();
		int yOff = (int)newShape.getBounds().getY();
		for (int i = 0; i < newShape.npoints; i++)
		{
			newShape.xpoints[i] = newShape.xpoints[i] - xOff;
			newShape.ypoints[i] = newShape.ypoints[i] - yOff;
		}
		newShape.invalidate();
		Image rotated = new Image(newShape, this.color);
		this.shape = newShape;
		this.image = rotated.getImage();
		this.width = rotated.getShape().getBounds().width;
		this.height = rotated.getShape().getBounds().height;
		this.x = rotated.getX();
		this.y = rotated.getY();
	}
	
	private void getRotatedPoint(Point2D point, Point2D base, double angle)
	{
		double x = Math.toRadians(angle);
		double newX = base.getX() + (point.getX()-base.getX())*Math.cos(x) - (point.getY()-base.getY())*Math.sin(x);
		double newY = base.getY() + (point.getX()-base.getY())*Math.sin(x) + (point.getY()-base.getY())*Math.cos(x);
		point.setLocation(newX, newY);
	}
	public BufferedImage getImage()
	{
		return this.image;
	}
	public Polygon getShape() {
		return shape;
	}
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	
	public void render(Graphics2D g2d, boolean debug)
	{
		g2d.drawImage(this.image, (int)x, (int)y, this.width, this.height, null);
	}
	public double getCenterX()
	{
		return x + (this.width / 2);
	}
	public double getCenterY()
	{
		return y + (this.height / 2);
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x - (this.width / 2);
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y - (this.height / 2);
	}
	public void setImage(BufferedImage image)
	{
		this.image = image;
	}

	public static int getImageCount() {
		return imageCount;
	}
}
