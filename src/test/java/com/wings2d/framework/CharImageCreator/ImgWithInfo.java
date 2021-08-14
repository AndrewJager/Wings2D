package com.wings2d.framework.CharImageCreator;

import java.awt.image.BufferedImage;

public class ImgWithInfo {
	private BufferedImage img;
	private String methodName;
	private String methodData;
	
	public ImgWithInfo(final BufferedImage img, final String methodName, final String methodData)
	{
		this.img = img;
		this.methodName = methodName;
		this.methodData = methodData;
	}
	
	public BufferedImage getImage() {
		return img;
	}
	public String getMethodName() {
		return methodName;
	}
	public String getMethodData() {
		return methodData;
	}
}
