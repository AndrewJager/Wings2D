package framework;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.List;

/** 
 * A static component of the level, eg. wall/floor/ramp. Represented as a line.
 */
public class Wall{
	private Level level;
	private Line2D line;
	private WallTypes type;
	/** Images to render with the wall */
	private List<Image> images;
	/** a rectangle to be the background of the wall */
	private Image background;
	/** Background color of the background rectangle */
	private Color backgroundColor;
	private double length;
	private boolean flip;
	private Point2D drawPoint;
	/**
	 * Constructor for WallTypes. The points MUST be left to right in floors and ramps, and top to bottom in walls.
	 * @param level Level this wall belongs to
	 * @param x1 X location of first point
	 * @param y1 Y location of first point
	 * @param x2 X location of second point
	 * @param y2 Y location of second point
	 * @param type Type of wall 
	 */
	public Wall(Level level, double x1, double y1, double x2, double y2, WallTypes type)
	{
		double scale = level.getManager().getScale();
		line = new Line2D.Double(x1 * scale, y1 * scale, x2 * scale, y2 * scale);
		this.type = type;
		this.level = level;
		this.images = new ArrayList<Image>();
		// Use og numbers instead of scaled coords, because image made from this will be scaled twice otherwise
		this.length = Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
		this.flip = false;
	}
	public void render(Graphics2D g2d, boolean debug)
	{
		if (background != null)
		{
			g2d.setColor(backgroundColor);
			if (flip)
			{
				if (type == WallTypes.FLOOR)
				{
					g2d.drawImage(background.getImage(), (int)line.getX1(), (int)line.getY1() - background.getHeight(),
							background.getWidth(), background.getHeight(), null);
				}
				else if (type == WallTypes.WALL)
				{
					g2d.drawImage(background.getImage(), (int)line.getX1() - background.getWidth(), (int)line.getY1(),
							background.getWidth(), background.getHeight(), null);
				}
			}
			else
			{
				if (drawPoint != null)
				{
					g2d.drawImage(background.getImage(), (int)drawPoint.getX(), (int)drawPoint.getY(), background.getWidth(), 
							background.getHeight(), null);
				}
				else
				{
					g2d.drawImage(background.getImage(), (int)line.getX1(), (int)line.getY1(), background.getWidth(), 
							background.getHeight(), null);
				}
			}
		}
		for (int i = 0; i < images.size(); i++)
		{
			images.get(i).render(g2d, debug);
		}
		g2d.setColor(Color.GREEN);
		g2d.drawLine((int)line.getX1(), (int)line.getY1(), (int)line.getX2(), (int)line.getY2());
	}
	/**
	 * Create a rectanglar background for the wall
	 * @param height How high the rect is for floors/ramps, how wide it is for walls
	 * @param color Color of the rectangle
	 * @param flip Use to change the side of the wall that the rectangle is drawn on. Ramps cannot be flipped. 
	 */
	public void setBackground(double height, Color color, boolean flip)
	{
		Shape rect = new Rectangle2D.Double(0, 0, length, height);
		this.backgroundColor = color;
		AffineTransform transform = new AffineTransform();
		if (type == WallTypes.WALL)
		{
			rect = new Rectangle2D.Double(0, 0, height, length);
		}
		else if (type == WallTypes.FLOOR)
		{
			rect = new Rectangle2D.Double(0, 0, length, height);
		}
		else if (type == WallTypes.RAMP)
		{
			rect = new Rectangle2D.Double(0, 0, length, height);
			if (line.getY1() < line.getY2()) // Descending
			{
				transform.rotate(Math.toRadians(45));
				rect = transform.createTransformedShape(rect);
				double offsetX = rect.getBounds2D().getX();
				double offsetY = rect.getBounds2D().getY();
				transform = new AffineTransform();
				transform.translate(-offsetX, -offsetY);
				rect = transform.createTransformedShape(rect);
				
				double pointOffset = (rect.getBounds2D().getWidth() * level.getManager().getScale()) - (line.getBounds2D().getWidth());
				drawPoint = new Point2D.Double(line.getX1() - pointOffset, line.getY1());
				System.out.println(line.getBounds2D().getWidth());
				System.out.println(rect.getBounds2D().getWidth());
				System.out.println(pointOffset);
			}
			else // Ascending
			{
				transform.rotate(Math.toRadians(-45));
				rect = transform.createTransformedShape(rect);
				double offsetX = rect.getBounds2D().getX();
				double offsetY = rect.getBounds2D().getY();
				transform = new AffineTransform();
				transform.translate(-offsetX, -offsetY);
				rect = transform.createTransformedShape(rect);
				
				// This case requires a special point to draw the image at
				drawPoint = new Point2D.Double(line.getX1(), line.getY2()); 
			}
		}
		this.background = new Image(rect, color, level);
		this.flip = flip;
	}
	public void addImage(Image image)
	{
		this.images.add(image);
	}
	public WallTypes getType()
	{
		return this.type;
	}
	public Line2D getLine()
	{
		return this.line;
	}
}
