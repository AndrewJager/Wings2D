package com.wings2d.framework.svg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public abstract class SVGItem {
	public static final String GROUP = "g";
	public static final String RECT = "rect";
	public static final String ELLIPSE = "ellipse";
	public static final String PATH = "path";
	
	protected static final boolean DEBUG = false;
	protected static final BasicStroke DEBUG_STROKE = new BasicStroke(1);
	protected static final Color DEBUG_COLOR = Color.RED;
	
	private String id;
	
	public SVGItem(final String id) {
		this.id = id;
	}
	
	public String getID()  {
		return id;
	}
	
	public abstract void render(final Graphics2D g2d);
	public abstract void printData(final String indent);
	public abstract Rectangle2D getBounds();
	public abstract void applyTransform(final AffineTransform t);
}
