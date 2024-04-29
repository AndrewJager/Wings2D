package com.wings2d.framework.svg.util;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

import com.wings2d.framework.svg.SVGShape;

public class SVGEllipse {
	public static SVGShape parseEllipse(final Node ellipseNode) {
		double cx = Double.valueOf(ellipseNode.getAttributes().getNamedItem("cx").getNodeValue());
		double cy = Double.valueOf(ellipseNode.getAttributes().getNamedItem("cy").getNodeValue());
		double rx = Double.valueOf(ellipseNode.getAttributes().getNamedItem("rx").getNodeValue());
		double ry = Double.valueOf(ellipseNode.getAttributes().getNamedItem("ry").getNodeValue());
		
		double x = cx - rx;
		double y = cy - ry;
		double width = rx * 2;
		double height = ry * 2;
		
		Ellipse2D e = new Ellipse2D.Double(x, y, width, height);
		
		String id = ellipseNode.getAttributes().getNamedItem("id").getNodeValue();
		
		Node transform = ellipseNode.getAttributes().getNamedItem("transform");
		AffineTransform t = SVGTransform.parseTransform(transform);
		
		String style = ellipseNode.getAttributes().getNamedItem("style").getNodeValue();
		Map<String, Object> optionalData = new HashMap<String, Object>();
		SVGStyles styles = SVGStyles.parseStyles(style, optionalData);
		
		Shape s = t.createTransformedShape(e);
		return new SVGShape(id, s, styles, optionalData);
	}
}
