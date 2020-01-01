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
	
	/**
	 * Returns a substring, but won't fail if start or end is out of bounds
	 * @param str String to get substring from.
	 * @param start Starting character of substring.
	 * @param end Ending character of substring. Substring can end earlier if end is outside length of str.
	 * @return Requested substring, or as close as can be done with the given positions in the string.
	 */
	public static String safeSubstring(String str, int start, int end)
	{
		int strEnd = str.length();
		String subStr;
		if (start > strEnd)
		{
			subStr = "";
		}
		else if (end > strEnd)
		{
			subStr = str.substring(start, strEnd);
		}
		else
		{
			subStr = str.substring(start, end);
		}
		return subStr;
	}
}
