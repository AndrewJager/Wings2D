package framework;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/** 
 * A static component of the level, eg. wall/floor/ramp. Represented as a line.
 * Includes a rectangle image for display purposes only, the line is the only thing that collides.
 */
public class Wall extends GameObject{
	/** Level that this Wall is associated with */
	private Level level;
	/** Line before scaling */
	private Line2D origLine;
	/** Line that defines the collisions of the Wall */
	private Line2D line;
	/** Type of wall (wall, floor, ramp) */
	private WallTypes type;
	/** Images to render with the wall */
	private List<Image> images;
	/** a rectangle to be the background of the wall */
	private Image background;
	/** Background color of the background rectangle */
	private Color backgroundColor;
	/** The dimension of the rectangle not determined by the length of the line */
	private double length;
	private double height;
	/** If true, draw the image on the other side of the line. Not possible for ramps */
	private boolean flip;
	/** Point to draw the image at for ramps */
	private Point2D drawPoint;
	private Shape rect;
	/**
	 * Constructor for Wall. The points MUST be left to right in floors and ramps, and top to bottom in walls.
	 * @param level Level this wall belongs to
	 * @param x1 X location of first point
	 * @param y1 Y location of first point
	 * @param x2 X location of second point
	 * @param y2 Y location of second point
	 * @param height Height of the display image. This is width for walls.
	 * @param type Type of wall 
	 */
	public Wall(Level level, double x1, double y1, double x2, double y2, double height, WallTypes type)
	{
		origLine = new Line2D.Double(x1, y1, x2, y2);
		
		this.type = type;
		this.level = level;
		this.height = height;
		this.images = new ArrayList<Image>();
		// Use og numbers instead of scaled coords, because image made from this will be scaled twice otherwise
		this.length = Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
		this.flip = false;
		
		createLine();
	}
	private void createLine()
	{
		double scale = level.getManager().getScale();
		line = new Line2D.Double(origLine.getX1() * scale, origLine.getY1() * scale, origLine.getX2() * scale, origLine.getY2() * scale);
		rect = new Rectangle2D.Double(0, 0, length, height);
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
	}
	
	/**
	 * Create a rectanglar background for the wall
	 * @param color Color of the rectangle
	 * @param flip Use to change the side of the wall that the rectangle is drawn on. Ramps cannot be flipped. 
	 */
	public void setBackground(Color color, boolean flip)
	{
		this.flip = flip;
		this.backgroundColor = color;
		
		this.background = new Image(rect, color, level);
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
	public Image getBackground()
	{
		return this.background;
	}
	@Override
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
		if (debug)
		{
			g2d.setColor(Color.GREEN);
			g2d.drawLine((int)line.getX1(), (int)line.getY1(), (int)line.getX2(), (int)line.getY2());
		}
	}
	@Override
	public void rescale() {
		createLine();
		if (this.background != null)
		{
			this.background.rescale();
		}
	}
	@Override
	public PhysicsType getPhysicsType()
	{
		return PhysicsType.STATIC;
	}
}
