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
	/**
	 * Get the name of the filter, in English (so with spaces/capitalization)
	 * @return The name of the filter
	 */
	public abstract String getFilterName();
	
	/**
	 * Used to separate parts of the string when saving to a file
	 */
	final String delimiter = "~";
}