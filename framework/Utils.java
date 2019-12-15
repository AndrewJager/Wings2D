package framework;

import java.awt.Color;

public final class Utils {
	public static int makeInRange(int value, int min, int max)
	{
		if (value > max)
		{
			value = max;
		}
		if (value < min)
		{
			value = min;
		}
		return value;
	}
	public static double makeInRange(double value, int min, int max)
	{
		if (value > max)
		{
			value = max;
		}
		if (value < min)
		{
			value = min;
		}
		return min;
	}
	
	public static Color getBlended(Color base, Color highlight)
	{
		int red = (base.getRed() + highlight.getRed()) / 2;
		int green = (base.getGreen() + highlight.getGreen()) / 2;
		int blue = (base.getBlue() + highlight.getBlue()) / 2;
		return new Color(red, green, blue, base.getAlpha());
	}
}
