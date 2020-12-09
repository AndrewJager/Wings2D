package com.wings2d.framework.CharImageCreator;

import java.awt.Color;

public class ImgTestColor extends Color{
	public ImgTestColor(final int r, final int g, final int b, final int a) {
		super(r, g, b, a);
	}
	public ImgTestColor(final int rgba, final boolean hasalpha) {
		super(rgba, hasalpha);
	}
	public ImgTestColor(final Color c){
		super(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
	}
	
	@Override
	public String toString()
	{
		return "{" + this.getRed() + ", " + this.getGreen() + ", " + this.getBlue() + ", " + this.getAlpha() +"}";
	}
}
