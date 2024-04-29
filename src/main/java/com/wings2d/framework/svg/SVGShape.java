package com.wings2d.framework.svg;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Map;

import com.wings2d.framework.svg.util.SVGStyles;

public class SVGShape extends SVGItem{
	public static final String OPTIONAL_STROKE = "stroke";
	public static final String OPTIONAL_TRANSFORM = "transform"; 
	
	private Shape shape;
	private SVGStyles styles; 
	private Map<String, Object> optionalData;

	public SVGShape(final String id, final Shape shape, final SVGStyles styles, final Map<String, Object> optionalData) {
		super(id);
		this.shape = shape;
		this.styles = styles;
		this.optionalData = optionalData;
	}

	@Override
	public void render(Graphics2D g2d) {
		if (styles.containsKey(SVGStyles.FILL)) {
			g2d.setColor((Color)styles.get(SVGStyles.FILL));
			g2d.fill(shape);
		}
		if (styles.containsKey(SVGStyles.STROKE)) {
			g2d.setColor((Color)styles.get(SVGStyles.STROKE));
			if (optionalData.containsKey(OPTIONAL_STROKE)) {
				g2d.setStroke((Stroke)optionalData.get(OPTIONAL_STROKE));
			}
			
			g2d.draw(shape);
		}
		
		if (DEBUG) {
			g2d.setColor(DEBUG_COLOR);
			g2d.setStroke(DEBUG_STROKE);
			g2d.draw(getBounds());
		}
	}
	@Override
	public void printData(final String indent) {
		System.out.println(indent + "shape");
	}
	@Override
	public Rectangle2D getBounds() {
		return shape.getBounds2D();
	}

	@Override
	public void applyTransform(AffineTransform t) {
		shape = t.createTransformedShape(shape);
	}

}
