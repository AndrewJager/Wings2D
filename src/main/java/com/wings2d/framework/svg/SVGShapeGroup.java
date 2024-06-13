package com.wings2d.framework.svg;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.wings2d.framework.svg.SVGImporter.ImportOverrides;
import com.wings2d.framework.svg.util.SVGTransform;

public class SVGShapeGroup extends SVGItem{
	private List<SVGItem> children;
	private Rectangle2D bounds;
	
	public SVGShapeGroup(final String id) {
		super(id);
		children = new ArrayList<SVGItem>();
	}

	public void printData(final String indent) {
		System.out.println(indent + "Begin group " + getID());
		for (int i = 0; i < children.size(); i++) {	
			children.get(i).printData(indent + " ");
		}
		System.out.println(indent + "End group " + getID());
	}
	
	@Override
	public void render(final Graphics2D g2d) {
		for (int i = 0; i < children.size(); i++) {	
			children.get(i).render(g2d);
		}
		
		if (DEBUG) {
			g2d.setColor(DEBUG_COLOR);
			g2d.setStroke(DEBUG_STROKE);
			g2d.draw(bounds);
		}
	}
	@Override
	public Rectangle2D getBounds() {
		return bounds;
	}
	@Override
	public void applyTransform(final AffineTransform t) {
		for (int i = 0; i < children.size(); i++) {	
			children.get(i).applyTransform(t);
		}
		recalcBounds();
	}
	@Override
	public void endUpdate() {
		for (int i = 0; i < children.size(); i++) {	
			children.get(i).endUpdate();
		}
		recalcBounds();
	}
	
	public static SVGShapeGroup parseG(final Node gNode) {
		String gID = gNode.getAttributes().getNamedItem("id").getNodeValue();
		Node transform = gNode.getAttributes().getNamedItem("transform");
		AffineTransform t = SVGTransform.parseTransform(transform);

		SVGShapeGroup group = new SVGShapeGroup(gID);
    	NodeList l = gNode.getChildNodes();
    	for (int i = 0; i < l.getLength(); i++) {
    		Node gChild = l.item(i);
    		SVGItem data = SVGImporter.parseNode(gChild);
    		if (data != null) {
    			group.getChildren().add(data);
    		}
    	}
    	group.applyTransform(t);
    	group.recalcBounds();
    	
    	return group;
	}
	
	public List<SVGItem> getChildren() {
		return children;
	}
	
	public void recalcBounds() {
		Area a = new Area();
		for (int i = 0; i < children.size(); i++) {
			a.add(new Area(children.get(i).getBounds()));
		}
		this.bounds = a.getBounds2D();
	}
	
	@Override
	public void applyOverrides(final ImportOverrides overrides) {
		for (int i = 0; i < children.size(); i++) {
			children.get(i).applyOverrides(overrides);
		}
	}
}
