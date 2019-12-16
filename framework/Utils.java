package framework;

import java.awt.Color;

/**
 * Contains various static utility functions that can be used anywhere in the program.
 */
public final class Utils {
	/**
	 * If the value is less then the minimum, or greater than the maximum, force it to be inside the range.
	 * @param value Number to make in range
	 * @param min If value is less than this, value will equal this
	 * @param max If value is more than this, value will equal this
	 * @return Value, possible changed to be inside range
	 */
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
	/**
	 * If the value is less then the minimum, or greater than the maximum, force it to be inside the range.
	 * @param value Number to make in range
	 * @param min If value is less than this, value will equal this
	 * @param max If value is more than this, value will equal this
	 * @return Value, possible changed to be inside range
	 */
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
	/**
	 * Mix two colors by averaging their red, green, and blue values
	 * @param base First color. Will determine the alpha of the final color
	 * @param highlight Second color
	 * @return A blend of the two provided colors
	 */
	public static Color getBlended(Color base, Color highlight)
	{
		int red = (base.getRed() + highlight.getRed()) / 2;
		int green = (base.getGreen() + highlight.getGreen()) / 2;
		int blue = (base.getBlue() + highlight.getBlue()) / 2;
		return new Color(red, green, blue, base.getAlpha());
	}
}
