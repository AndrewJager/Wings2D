package com.wings2d.framework.text;

import java.awt.Font;
import java.awt.Graphics2D;

public abstract class DisplayableText {
	private double x;
	private double y;
	private double ogX;
	private double ogY;
	private double textSize;
	private double ogTextSize;
	private Font font;
	public void setX(double x)
	{ 
		this.x = x;
	}
	public void setY(double y)
	{
		this.y = y;
	}
	public double getX()
	{
		return x;
	}
	public double getY()
	{
		return y;
	}	
	public double getTextSize() 
	{
		return textSize;
	}
	public void setTextSize(double textSize) 
	{
		this.textSize = textSize;
	}
	public double getOgX() {
		return ogX;
	}
	public void setOgX(double ogX) {
		this.ogX = ogX;
	}
	public double getOgY() {
		return ogY;
	}
	public void setOgY(double ogY) {
		this.ogY = ogY;
	}
	public double getOgTextSize() {
		return ogTextSize;
	}
	public void setOgTextSize(double ogTextSize) {
		this.ogTextSize = ogTextSize;
	}	
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	
	
	public abstract void update(double dt);
	public abstract void render(Graphics2D g2d);
}
