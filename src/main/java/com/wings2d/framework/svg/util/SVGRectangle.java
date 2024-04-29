package com.wings2d.framework.svg.util;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

import com.wings2d.framework.svg.SVGShape;

public class SVGRectangle {
	public static SVGShape parseRect(final Node rectNode) {
		double x = Double.valueOf(rectNode.getAttributes().getNamedItem("x").getNodeValue());
		double y = Double.valueOf(rectNode.getAttributes().getNamedItem("y").getNodeValue());
		double w = Double.valueOf(rectNode.getAttributes().getNamedItem("width").getNodeValue());
		double h = Double.valueOf(rectNode.getAttributes().getNamedItem("height").getNodeValue());
		Shape r = new Rectangle2D.Double(x, y, w, h);
		
		String id = rectNode.getAttributes().getNamedItem("id").getNodeValue();
		
		Node transform = rectNode.getAttributes().getNamedItem("transform");
		AffineTransform t = SVGTransform.parseTransform(transform);
		
		String style = rectNode.getAttributes().getNamedItem("style").getNodeValue();
		Map<String, Object> optionalData = new HashMap<String, Object>();
		SVGStyles styles = SVGStyles.parseStyles(style, optionalData);

		r = t.createTransformedShape(r);
		return new SVGShape(id, r, styles, optionalData);
	}
};