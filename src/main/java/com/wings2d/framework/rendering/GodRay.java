package com.wings2d.framework.rendering;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.wings2d.framework.core.GameObject;
import com.wings2d.framework.core.Level;
import com.wings2d.framework.shape.ShapeUtils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Draws a bunch of lines to resemble a ray of light
 * 
 */
public class GodRay extends GameObject{
	class Line extends Line2D.Double{
		private static final long serialVersionUID = 1L;
		private boolean direction;
		public Line(double x1, double y1, double x2, double y2, boolean dir)
		{
			super(x1, y1, x2, y2);
			this.direction = dir;
		}
		
		public void swapDir()
		{
			direction = !direction;
		}
	}
	private Line2D origStart, origEnd, start, end;
	private List<Line> rays;
	private Color color;
	private Image background;
	private Point2D imageDrawPoint;
	private boolean vertical;
	private int rayCount;
	
	private Level level;
	
	/**
	 * Uses two lines to determine the start and end locations of the godray. Each line must be vertical or horizontal
	 * @param l1x1 Line 1 start x
	 * @param l1y1 Line 1 start y
	 * @param l1x2 Line 1 end x
	 * @param l1y2 Line 1 end y
	 * @param l2x1 Line 2 start x
	 * @param l2y1 Line 2 start y
	 * @param l2x2 Line 2 end x
	 * @param l2y2 Line 2 end y
	 * @param level Level (to get screen scale)
	 * @param rayCount Number of lines to generate in ray
	 * @param color Color of rays
	 */
	public GodRay(double l1x1, double l1y1, double l1x2, double l1y2, double l2x1,
			double l2y1, double l2x2, double l2y2, Level level, int rayCount, Color color)
	{
		this.level = level;
		this.color = color;
		this.rayCount = rayCount;
		
		origStart = new Line2D.Double(l1x1, l1y1, l1x2, l1y2);
		origEnd = new Line2D.Double(l2x1, l2y1, l2x2, l2y2);
		createRay();
	}
	
	private void createRay()
	{
		double scale = level.getManager().getScale();
		start = new Line2D.Double(origStart.getX1() * scale, origStart.getY1() * scale, origStart.getX2() * scale, origStart.getY2() * scale);
		end = new Line2D.Double(origEnd.getX1() * scale, origEnd.getY1() * scale, origEnd.getX2() * scale, origEnd.getY2() * scale);
		
		rays = new ArrayList<Line>();
		Random r = new Random();
		for (int i = 0; i < rayCount; i++)
		{
			double startX = start.getX1() + (start.getX2() - start.getX1()) * r.nextDouble();
			double startY = start.getY1() + (start.getY2() - start.getY1()) * r.nextDouble();
			double endX = end.getX1() + (end.getX2() - end.getX1()) * r.nextDouble();
			double endY = end.getY1() + (end.getY2() - end.getY1()) * r.nextDouble();
			
			rays.add(new Line(startX, startY, endX, endY, r.nextBoolean()));
		}
		vertical = true;
		
		GeneralPath path = new GeneralPath();
		path.moveTo(start.getX1(), start.getY1());
		path.lineTo(start.getX2(), start.getY2());
		path.lineTo(end.getX1(), end.getY1());
		path.lineTo(end.getX2(), end.getY2());

		Shape path2 = ShapeUtils.translate(path, -path.getBounds2D().getX(), -path.getBounds2D().getY());
		background = new Image(path2, color, level, false);
		imageDrawPoint = new Point2D.Double(path.getBounds2D().getX(), path.getBounds2D().getY());
	}
	@Override
	public void render(Graphics2D g2d, boolean debug)
	{
		g2d.drawImage(background.getImage(), (int)imageDrawPoint.getX(), (int)imageDrawPoint.getY(), null);
		g2d.setColor(color);
		g2d.setStroke(new BasicStroke(4));
		for (int i = 0; i < rays.size(); i++)
		{
			Line2D r = rays.get(i);
			g2d.drawLine((int)r.getX1(), (int)r.getY1(), (int)r.getX2(), (int)r.getY2());
		}
		g2d.setStroke(new BasicStroke(1));
	}

	@Override
	public void update(double dt) {
		for (int i = 0; i < rays.size(); i++)
		{
			Line ray = rays.get(i);
			if (vertical)
			{
				if (ray.direction)
				{
					ray.setLine(ray.getX1(), ray.getY1(), ray.getX2(), ray.getY2() + 0.1);
				}
				else
				{
					ray.setLine(ray.getX1(), ray.getY1(), ray.getX2(), ray.getY2() - 0.1);
				}
				if (ray.getY2() >= end.getY2() || ray.getY2() <= end.getY1())
				{
					ray.swapDir();
				}
			}
			else
			{
				if (ray.getX2() >= end.getX2() || ray.getX1() <= end.getX1())
				{
					ray.swapDir();
				}
			}
		}
	}
	@Override
	public void rescale()
	{
		createRay();
	}
	
	public Image getBackground()
	{
		return background;
	}
}
