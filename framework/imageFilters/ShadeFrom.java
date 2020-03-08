package framework.imageFilters;

/**
 * Interface to allow lighten/darken from filters to work with the same editor
 */
public interface ShadeFrom {
	/** Get the direction the filter will be run from **/
	public abstract ShadeDir getDirection();
	/** Get the amount of shading **/
	public abstract double getAmt();
}
