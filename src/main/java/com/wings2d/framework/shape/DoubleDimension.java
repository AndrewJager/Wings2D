package com.wings2d.framework.shape;

import java.awt.geom.Dimension2D;

public class DoubleDimension extends Dimension2D{
	private double width;
	private double height;
	
	public DoubleDimension(final double width, final double height) {
		this.width = width;
		this.height = height;
	}
	
	public void setWidth(final double width) {
		this.width = width;
	}
	public void setHeight(final double height) {
		this.height = height;
	}
	@Override
	public double getWidth() {
		return width;
	}
	@Override
	public double getHeight() {
		return height;
	}
	@Override
	public void setSize(double width, double height) {
		this.width = width;
		this.height = height;	
	}
}
