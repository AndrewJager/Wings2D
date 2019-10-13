package framework;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Path2D.Double;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Image {
	private Path2D shape, ogShape;
	private BufferedImage image;
	private int width, height;
	private double x, y;
	private Color color;
	private double rotation = 0;
	private static int imageCount = 0;

	public Image(Path2D shape, Color color)
	{
		imageCount = getImageCount() + 1;
		this.x = 0;
		this.y = 0;
		this.shape = shape;
		this.ogShape = shape;
		this.width = (int)Math.ceil(shape.getBounds2D().getWidth());
		this.height = (int)Math.ceil(shape.getBounds2D().getHeight());
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
		newImage.shape = this.shape;
		return newImage;
	}

	public void rotate(double angle) {
		this.image = null;
		this.rotation += Math.toRadians(angle);
		AffineTransform transform = new AffineTransform();
		transform.rotate(this.rotation, this.x + this.ogShape.getBounds2D().getCenterX(), 
				this.y + this.ogShape.getBounds2D().getCenterY());
		Path2D.Double newShape = (Double) this.ogShape.clone();
		newShape.transform(transform);
		transform = new AffineTransform();
		// Rotated shape ends up offset for some reason. I have no idea why I have to do this.
		transform.translate(-newShape.getBounds2D().getX(), -newShape.getBounds2D().getY());
		newShape.transform(transform);
		Image rotated = new Image(newShape, this.color);
		
		this.shape = ogShape;
		this.image = rotated.getImage();
		this.width = (int)Math.ceil(rotated.getShape().getBounds2D().getWidth());
		this.height = (int)Math.ceil(rotated.getShape().getBounds2D().getHeight());
		this.x = rotated.getX();
		this.y = rotated.getY();
	}
	
	public BufferedImage getImage()
	{
		return this.image;
	}
	public Path2D getShape() {
		return shape;
	}
	public void setShape(Path2D shape) {
		this.shape = shape;
	}
	
	public void render(Graphics2D g2d, boolean debug)
	{
//		g2d.drawRect((int)this.getShape().getBounds2D().getX(), (int)this.getShape().getBounds2D().getY(),
//				(int)this.getShape().getBounds2D().getWidth(), (int)this.getShape().getBounds2D().getHeight());
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
