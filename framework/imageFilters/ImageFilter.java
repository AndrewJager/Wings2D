package framework.imageFilters;

import framework.Image;

/**
 * Interface for all image filters. Contains a single procedure that all must implement.
 */
public interface ImageFilter
{
	/**
	 * Run the filter 
	 * @param img {@link framework.Image Image} to run the filter over
	 */
	public abstract void filter(Image img);
	/**
	 * Get the name of the filter, in English (so with spaces/capitalization)
	 * @return The name of the filter
	 */
	public abstract String getFilterName();
	
	/**
	 * Get a quick summary of the settings for the filter, for use in the editor's filter list. Not guaranteed to contain all info, as space is limited.
	 * @return String with a arbitrary amount of info from the filter
	 */
	public abstract String getFilterInfoString();
	
	/**
	 * Used to separate parts of the string when saving to a file
	 */
	final String delimiter = "~";
}