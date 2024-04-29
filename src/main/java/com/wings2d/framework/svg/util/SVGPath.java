package com.wings2d.framework.svg.util;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import com.wings2d.framework.svg.SVGShape;

public class SVGPath {
	private static List<Point2D> getPoints(final String[] split) {
		List<Point2D> points = new ArrayList<Point2D>();
		for (int j = 1; j < split.length; j++) {
			String[] coords = split[j].split(",");
			double x = Double.parseDouble(coords[0]);
			double y = Double.parseDouble(coords[1]);
			points.add(new Point2D.Double(x, y));
		}
		return points;
	}
	
	private static Point2D getCurrentPoint(final Path2D path) {
		Point2D curPoint = path.getCurrentPoint();
		double x = curPoint != null
				? curPoint.getX()
				: 0.0;
		double y = curPoint != null
				? curPoint.getY()
				: 0.0; 
		
		return new Point2D.Double(x, y);
	}
	
	public static SVGShape parsePath(final Node pathNode) {
		char[] pathChars = {'M', 'm', 'L', 'l', 'H', 'h', 'V', 'v', 'C', 'c', 'S', 's', 'Q', 'q', 'T', 't', 'A', 'a', 'Z', 'z'};

		Path2D path = new Path2D.Double();
		List<String> pathStrs = new ArrayList<String>();
		
		String pathStr = pathNode.getAttributes().getNamedItem("d").getNodeValue();
		int nextIdx = 0;
		while (nextIdx != Integer.MAX_VALUE) {
			nextIdx = Integer.MAX_VALUE;
			for (int i = 0; i < pathChars.length; i++) {
				int idx = pathStr.indexOf(pathChars[i]);
				if ((idx != 0) && (idx != -1) && (idx < nextIdx)) {
					nextIdx = idx;
				}
			}

			if (nextIdx < Integer.MAX_VALUE) {
				pathStrs.add(pathStr.substring(0, nextIdx));
				pathStr = pathStr.substring(nextIdx);
			}
			else {
				pathStrs.add(pathStr);
			}
		}

		for(int i = 0; i < pathStrs.size(); i++) {
			String[] pathSplit = pathStrs.get(i).split(" ");
			switch (pathSplit[0]) {
				case("M") -> {
					List<Point2D> points = getPoints(pathSplit);
					
					path.moveTo(points.get(0).getX(), points.get(0).getY());
					for (int j = 1; j < points.size(); j++) {
						path.lineTo(points.get(j).getX(), points.get(j).getY());
					}
					
				}
				case("m") -> {
					List<Point2D> points = getPoints(pathSplit);
					Point2D curPoint = getCurrentPoint(path);
					 
					double x = curPoint.getX() + points.get(0).getX();
					double y = curPoint.getY() + points.get(0).getY();
					
					path.moveTo(x, y);
					
					for (int j = 1; j < points.size(); j++) {
						curPoint = path.getCurrentPoint();
						path.lineTo(curPoint.getX() + points.get(j).getX(), curPoint.getY() + points.get(j).getY());
					}
				}
				case("L") -> {
					List<Point2D> points = getPoints(pathSplit);
					for(int j = 0; j < points.size(); j++) {
						path.lineTo(points.get(j).getX(), points.get(j).getY());
					}
				}
				case("l") -> {
					List<Point2D> points = getPoints(pathSplit);
					for(int j = 0; j < points.size(); j++) {
						Point2D curPoint = getCurrentPoint(path);
						path.lineTo(curPoint.getX() + points.get(j).getX(), curPoint.getY() + points.get(j).getY());
					}
				}
				case("H") -> {
					String[] xCoords = pathSplit[1].split(",");
					for (int j = 0; j < xCoords.length; j++) {
						double x = Double.parseDouble(xCoords[j]);
						double y = getCurrentPoint(path).getY();
						path.lineTo(x, y);
					}	
				}
				case("h") -> {
					String[] xCoords = pathSplit[1].split(",");
					for (int j = 0; j < xCoords.length; j++) {
						double x = Double.parseDouble(xCoords[j]);
						Point2D curPoint = getCurrentPoint(path);
						path.lineTo(curPoint.getX() + x, curPoint.getY());
					}	
				}
				case("V") -> {
					String[] yCoords = pathSplit[1].split(",");
					for (int j = 0; j < yCoords.length; j++) {
						double x = getCurrentPoint(path).getX();
						double y = Double.parseDouble(yCoords[j]);
						path.lineTo(x, y);
					}
				}
				case("v") -> {
					String[] yCoords = pathSplit[1].split(",");
					for (int j = 0; j < yCoords.length; j++) {
						double y = Double.parseDouble(yCoords[j]);
						Point2D curPoint = getCurrentPoint(path);
						path.lineTo(curPoint.getX(), curPoint.getY() + y);
					}
				}
				case("C") -> {
					List<Point2D> points = getPoints(pathSplit);
					int curves = points.size() / 3;
					int curCurve = 0;
					while (curCurve < curves) {
						double x1 = points.get((curCurve * 3)).getX();
						double y1 = points.get((curCurve * 3)).getY();
						double x2 = points.get((curCurve * 3) + 1).getX();
						double y2 = points.get((curCurve * 3) + 1).getY();
						double x3 = points.get((curCurve * 3) + 2).getX();
						double y3 = points.get((curCurve * 3) + 2).getY();
						path.curveTo(x1, y1, x2, y2, x3, y3);
						curCurve++;
					}
				}
				case("c") -> {
					List<Point2D> points = getPoints(pathSplit);
					int curves = points.size() / 3;
					int curCurve = 0;
					while (curCurve < curves) {
						Point2D curPoint = getCurrentPoint(path);
						double x = curPoint.getX();
						double y = curPoint.getY();
						double x1 = x + points.get((curCurve * 3)).getX();
						double y1 = y + points.get((curCurve * 3)).getY();
						double x2 = x + points.get((curCurve * 3) + 1).getX();
						double y2 = y + points.get((curCurve * 3) + 1).getY();
						double x3 = x + points.get((curCurve * 3) + 2).getX();
						double y3 = y + points.get((curCurve * 3) + 2).getY();
						path.curveTo(x1, y1, x2, y2, x3, y3);
						curCurve++;
					}
				}
				case("S") -> {
					List<Point2D> points = getPoints(pathSplit);
					int curves = points.size() / 3;
					int curCurve = 0;
					while (curCurve < curves) {
						Point2D curPoint = getCurrentPoint(path);
						double x1 = curPoint.getX();
						double y1 = curPoint.getY();
						double x2 = points.get((curCurve * 2) + 1).getX();
						double y2 = points.get((curCurve * 2) + 1).getY();
						double x3 = points.get((curCurve * 2) + 2).getX();
						double y3 = points.get((curCurve * 2) + 2).getY();
						path.curveTo(x1, y1, x2, y2, x3, y3);
						curCurve++;
					}
				}
				case("s") -> {
					List<Point2D> points = getPoints(pathSplit);
					int curves = points.size() / 2;
					int curCurve = 0;
					while (curCurve < curves) {
						Point2D curPoint = getCurrentPoint(path);
						double x1 = curPoint.getX();
						double y1 = curPoint.getY();
						double x2 = x1 + points.get((curCurve * 2) + 1).getX();
						double y2 = y1 + points.get((curCurve * 2) + 1).getY();
						double x3 = x2 + points.get((curCurve * 2) + 2).getX();
						double y3 = y2 + points.get((curCurve * 2) + 2).getY();
						path.curveTo(x1, y1, x2, y2, x3, y3);
						curCurve++;
					}
				}
				case("Q") -> {
					List<Point2D> points = getPoints(pathSplit);
					int curves = points.size() / 2;
					int curCurve = 0;
					while (curCurve < curves) {
						double x1 = points.get((curCurve * 2) + 1).getX();
						double y1 = points.get((curCurve * 2) + 1).getY();
						double x2 = points.get((curCurve * 2) + 2).getX();
						double y2 = points.get((curCurve * 2) + 2).getY();
						path.quadTo(x1, y1, x2, y2);
						curCurve++;
					}
				}
				case("q") -> {
					List<Point2D> points = getPoints(pathSplit);
					int curves = points.size() / 2;
					int curCurve = 0;
					while (curCurve < curves) {
						Point2D curPoint = getCurrentPoint(path);
						double x1 = curPoint.getX() + points.get((curCurve * 2)).getX();
						double y1 = curPoint.getY() + points.get((curCurve * 2)).getY();
						double x2 = curPoint.getX() + points.get((curCurve * 2) + 1).getX();
						double y2 = curPoint.getY() + points.get((curCurve * 2) + 1).getY();
						path.quadTo(x1, y1, x2, y2);
						curCurve++;
					}
				}
				case("T") -> {
					List<Point2D> points = getPoints(pathSplit);
					int curves = points.size() / 2;
					int curCurve = 0;
					while (curCurve < curves) {
						Point2D curPoint = getCurrentPoint(path);
						double x1 = curPoint.getX();
						double y1 = curPoint.getY();
						double x2 = points.get(curCurve).getX();
						double y2 = points.get(curCurve).getY();
						path.quadTo(x1, y1, x2, y2);
						curCurve++;
					}
				}
				case("t") -> {
					List<Point2D> points = getPoints(pathSplit);
					int curves = points.size() / 2;
					int curCurve = 0;
					while (curCurve < curves) {
						Point2D curPoint = getCurrentPoint(path);
						double x1 = curPoint.getX();
						double y1 = curPoint.getY();
						double x2 = x1 + points.get(curCurve).getX();
						double y2 = y1 + points.get(curCurve).getY();
						path.quadTo(x1, y1, x2, y2);
						curCurve++;
					}
				}
				case("A") -> {
					System.out.println("Arc not implemented!");
				}
				case("a") -> {
					System.out.println("Arc not implemented!");
				}
				case("Z") -> {
					path.closePath();
				}
				case("z") -> {
					path.closePath();
				}
			}
		}
		
		String id = pathNode.getAttributes().getNamedItem("id").getNodeValue();
		
		Node transform = pathNode.getAttributes().getNamedItem("transform");
		AffineTransform t = SVGTransform.parseTransform(transform);
		
		String style = pathNode.getAttributes().getNamedItem("style").getNodeValue();
		Map<String, Object> optionalData = new HashMap<String, Object>();
		SVGStyles styles = SVGStyles.parseStyles(style, optionalData);
		
		Shape p = t.createTransformedShape(path);
		return new SVGShape(id, p, styles, optionalData);
	}
}
