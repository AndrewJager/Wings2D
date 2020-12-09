package com.wings2d.framework.CharImageCreator;

import java.awt.image.BufferedImage;

public class ImgWithInfo {
	private BufferedImage img;
	private String methodName;
	private char character;
	
	public ImgWithInfo(final BufferedImage img, final String methodName, final char character)
	{
		this.img = img;
		this.methodName = methodName;
		this.character = character;
	}
	
	public BufferedImage getImage() {
		return img;
	}
	public String getMethodName() {
		return methodName;
	}
	public char getCharacter() {
		return character;
	}
}
