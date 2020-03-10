package framework.imageFilters;

/**
 * Interface to allow lighten/darken from filters to work with the same editor
 */
public interface ShadeFrom {
	/**
	 * Get the direction the filter will be run from
	 * @return A ShadeDir containing the filter direction
	 */
	public abstract ShadeDir getDirection();
	/**
	 * Get the amount of shading
	 * @return Amount of shading specified by the filter
	 */
	public abstract double getAmt();
}
