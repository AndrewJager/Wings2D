package framework.imageFilters;

import framework.Image;

/**
 * Interface for all image filters. Contains a single procedure that all must implement.
 */
public interface ImageFilter
{
	/**
	 * Run the filter 
	 * @param img Image to run the filter over
	 */
	public abstract void filter(Image img);
	public abstract String getFilterName();
}