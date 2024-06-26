package com.wings2d.framework.core;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Contains various static utility functions that can be used anywhere in the program.
 */
public final class Wings2DUtils {
	/**
	 * If the value is less then the minimum, or greater than the maximum, force it to be inside the range.
	 * @param value Number to make in range
	 * @param min If value is less than this, value will equal this
	 * @param max If value is more than this, value will equal this
	 * @return Value, possible changed to be inside range
	 */
	public static int makeInRange(int value, final int min, final int max)
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
	public static double makeInRange(double value, final int min, final int max)
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
	 * Mix two colors by averaging their red, green, and blue values
	 * @param base First color. Will determine the alpha of the final color
	 * @param highlight Second color
	 * @return A blend of the two provided colors
	 */
	public static Color getBlended(final Color base, final Color highlight)
	{
		int red = (base.getRed() + highlight.getRed()) / 2;
		int green = (base.getGreen() + highlight.getGreen()) / 2;
		int blue = (base.getBlue() + highlight.getBlue()) / 2;
		return new Color(red, green, blue, base.getAlpha());
	}
	
	/**
	 * Increase the base color using the values of the highlight color
	 * @param base Color will never be darker than this. Alpha value is from this color
	 * @param highlight Increase the base color with this color's RGB values, never going above 255
	 * @return Base color increased by the values of the highlight color
	 */
	public static Color overlayColor(final Color base, final Color highlight)
	{
		int red = makeInRange(base.getRed() + highlight.getRed(), 0, 255);
		int green = makeInRange(base.getGreen() + highlight.getGreen(), 0, 255);
		int blue = makeInRange(base.getBlue() + highlight.getBlue(), 0, 255);
		return new Color(red, green, blue, base.getAlpha());
	}
	
	/**
	 * Return a color with the RGB values in the input color multiplied by the percent
	 * @param color Color to use, value is not modified
	 * @param percent Can be over 100
	 * @return A new color, with the RGB values modified
	 */
	public static Color modifyColorByPercent(final Color color, final double percent)
	{
		double red = makeInRange(color.getRed() * percent, 0, 255);
		double green = makeInRange(color.getGreen() * percent, 0, 255);
		double blue = makeInRange(color.getBlue() * percent, 0, 255);
		return new Color((int)red, (int)green, (int)blue, color.getAlpha());
	}
	/**
	 * Returns a substring, but won't fail if start or end is out of bounds
	 * @param str String to get substring from.
	 * @param start Starting character of substring.
	 * @param end Ending character of substring. Substring can end earlier if end is outside length of str.
	 * @return Requested substring, or as close as can be done with the given positions in the string.
	 */
	public static String safeSubstring(final String str, final int start, final int end)
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
	
	/**
	 * Returns a string containing the RGBA values of the color, separated by the delimiter string
	 * @param color Color to get string values from
	 * @param delimiter Used to separate each value
	 * @return "Red(delimiter)Green(delimiter)Blue(delimiter)Alpha"
	 */
	public static String colorToString(final Color color, final String delimiter)
	{
		return color.getRed() + delimiter + color.getGreen() + delimiter + color.getBlue() + delimiter + color.getAlpha();
	}
	
	/**
	 * Returns a string containing the RGBA values of the color, using ", " as the delimiter
	 * @param color Color to get values from
	 * @return "Red, Green, Blue, Alpha"
	 */
	public static String colorToString(final Color color)
	{
		return colorToString(color, ", ");
	}
	
	/**
	 * Creates a Color object from a string containing RGBA values
	 * @param str Expected in format of Red(delimiter)Green(delimiter)Blue(delimiter)Alpha
	 * @param delimiter Used to separate each value
	 * @return Color object created from string
	 */
	public static Color stringToColor(final String str, final String delimiter)
	{
		String[] strCol = str.split(delimiter);
		Color newColor = new Color(Integer.parseInt(strCol[0]), Integer.parseInt(strCol[1]), Integer.parseInt(strCol[2]), Integer.parseInt(strCol[3]));
		return newColor;
	}
	
	/**
	 * Creates a Color object from a string containing RGBA values, using ", " as a delimiter
	 * @param str Expected in format of Red, Green, Blue, Alpha
	 * @return Color object created from string
	 */
	public static Color stringToColor(final String str)
	{
		return stringToColor(str, ", ");
	}
	
	/**
	 * Convert a percentage value to a 0-255 color value
	 * @param percent 0-100
	 * @return percent * 2.55
	 */
	public static double percentTo255(final double percent)
	{
		return percent * 2.55;
	}
	
	public static void translateRender(final Graphics2D g2d, final Runnable renderMethod, final double xDist, final double yDist) {
		g2d.translate(xDist, yDist);
		renderMethod.run();
		g2d.translate(-xDist, -yDist);
	}
}
