package framework;

/**
 * Different types of static level components
 */
public enum WallTypes {
	/** Horizontal, prevents player from falling */
	FLOOR,
	/** 45 degree angle, player can move up/down on it */
	WALL,
	/** Vertical, prevents player from passing left/right through it */
	RAMP,
}
