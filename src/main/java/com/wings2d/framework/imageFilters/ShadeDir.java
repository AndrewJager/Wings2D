package com.wings2d.framework.imageFilters;

/**
 * Enum of directions that shading functions will use to determine how to shade the image
 */
public enum ShadeDir {
	/** Right to left **/
	RIGHT,
	/** Left to right **/
	LEFT,
	/** Top to bottom **/
	TOP,
	/** Bottom to top **/
	BOTTOM;
	
	public static String getAsString(ShadeDir dir)
	{
		if (dir == RIGHT)
		{
			return "Right";
		}
		else if (dir == LEFT)
		{
			return "Left";
		}
		else if (dir == TOP)
		{
			return "Top";
		}
		else if (dir == BOTTOM)
		{
			return "Bottom";
		}
		else
		{
			System.out.println("How? (ShadeDir.getAsString");
			return "?";
		}
	}
	public static ShadeDir createFromString(String dir)
	{
		if (dir.toUpperCase().equals("TOP"))
		{
			return ShadeDir.TOP;
		}
		else if (dir.toUpperCase().equals("BOTTOM"))
		{
			return ShadeDir.BOTTOM;
		}
		else if (dir.toUpperCase().equals("LEFT"))
		{
			return ShadeDir.LEFT;
		}
		else if (dir.toUpperCase().equals("RIGHT"))
		{
			return ShadeDir.RIGHT;
		}
		else
		{
			System.out.println("Invalid input in ShadeDir.createFromString. Defaulting to TOP");
			return ShadeDir.TOP;
		}
	}
}
