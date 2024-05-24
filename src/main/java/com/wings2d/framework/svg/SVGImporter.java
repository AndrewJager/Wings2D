package com.wings2d.framework.svg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.wings2d.framework.core.Game;
import com.wings2d.framework.svg.util.SVGEllipse;
import com.wings2d.framework.svg.util.SVGPath;
import com.wings2d.framework.svg.util.SVGRectangle;

public class SVGImporter {
	public static SVGShapeGroup importSVG(final String svgFilePath, final Game game) {
		DocumentBuilder builder;
		Document doc = null;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = builder.parse(new File(svgFilePath));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
		doc.getDocumentElement().normalize();
	    Node svg = doc.getElementsByTagName("svg").item(0);
	    NodeList svgChildren = svg.getChildNodes();
	    
	    String svgID = svg.getAttributes().getNamedItem("id").getNodeValue();
	    SVGShapeGroup shapeGroup = new SVGShapeGroup(svgID, game);
	    
	    for (int i = 0; i < svgChildren.getLength(); i++) {
	    	Node n = svgChildren.item(i); 
	    	SVGItem item = parseNode(n, game);
	    	if (item != null) {
	    		shapeGroup.getChildren().add(item);
	    	}
	    }
	    shapeGroup.recalcBounds();
		
		return shapeGroup;
	}
	public static SVGItem parseNode(final Node n, final Game game) {
		if (n.getNodeName().equals(SVGShape.GROUP)) {
			return SVGShapeGroup.parseG(n, game);
		}
		else if (n.getNodeName().equals(SVGShape.RECT)) {
			return SVGRectangle.parseRect(n);
		}
		else if (n.getNodeName().equals(SVGShape.ELLIPSE)) {
			return SVGEllipse.parseEllipse(n);
		}
		else if (n.getNodeName().equals(SVGShape.PATH)) {
			return SVGPath.parsePath(n);
		}
		else {
			return null;
		}
	}
	


}
