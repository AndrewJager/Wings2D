package com.wings2d.framework.misc;

/**
 * Enum of north/south/east/west directions
 */
public enum CardinalDir {
	NORTH,
	SOUTH,
	EAST,
	WEST;
	
	public static String getAsString(CardinalDir dir)
	{
		if (dir == NORTH)
		{
			return "North";
		}
		else if (dir == SOUTH)
		{
			return "South";
		}
		else if (dir == EAST)
		{
			return "East";
		}
		else if (dir == WEST)
		{
			return "West";
		}
		else
		{
			throw new RuntimeException(dir + " not handled at CardinalDir.getAsString");
		}
	}
	public static CardinalDir createFromString(String dir)
	{
		if (dir.toUpperCase().equals("NORTH"))
		{
			return CardinalDir.NORTH;
		}
		else if (dir.toUpperCase().equals("SOUTH"))
		{
			return CardinalDir.SOUTH;
		}
		else if (dir.toUpperCase().equals("EAST"))
		{
			return CardinalDir.EAST;
		}
		else if (dir.toUpperCase().equals("WEST"))
		{
			return CardinalDir.WEST;
		}
		else
		{
			throw new RuntimeException(dir + " does not correspond to any cardinal direction");
		}
	}
	
	public static CardinalDir[] getAllDirections() {
		CardinalDir[] directions = new CardinalDir[4];
		for (int i = 0; i < CardinalDir.values().length; i++) 
		{
			directions[i] = CardinalDir.values()[i];
		}
		return directions;
	}
}
