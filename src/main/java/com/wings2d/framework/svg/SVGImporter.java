package com.wings2d.framework.svg;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.wings2d.framework.svg.util.SVGEllipse;
import com.wings2d.framework.svg.util.SVGPath;
import com.wings2d.framework.svg.util.SVGRectangle;

public class SVGImporter {
	public record ImportOverrides(int alpha) {}
	
	public static SVGShapeGroup importSVG(final String svgFilePath, final ImportOverrides overrides) {
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
	    SVGShapeGroup shapeGroup = new SVGShapeGroup(svgID);
	    
	    for (int i = 0; i < svgChildren.getLength(); i++) {
	    	Node n = svgChildren.item(i); 
	    	SVGItem item = parseNode(n);
	    	if (item != null) {
	    		shapeGroup.getChildren().add(item);
	    	}
	    }
	    shapeGroup.recalcBounds();
	    
	    if (overrides != null) {
	    	for (int i = 0; i < shapeGroup.getChildren().size(); i++) {
	    		shapeGroup.getChildren().get(i).applyOverrides(overrides);
	    	}
	    }
		
		return shapeGroup;
	}
	
	public static SVGShapeGroup importSVG(final String svgFilePath) {
		return importSVG(svgFilePath, null);
	}
	
	public static SVGItem parseNode(final Node n) {
		if (n.getNodeName().equals(SVGShape.GROUP)) {
			return SVGShapeGroup.parseG(n);
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
