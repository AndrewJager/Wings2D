package com.wings2d.framework.CharImageCreator;

public class ImgTestPoint {
	private int x;
	private int y;
	private ImgTestColor color;
	
	ImgTestPoint(final int x, final int y, final ImgTestColor color)
	{
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	
	@Override
	public String toString()
	{
		return x + ", " + y + ", {" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ", " + color.getAlpha() + "}";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImgTestPoint other = (ImgTestPoint) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
