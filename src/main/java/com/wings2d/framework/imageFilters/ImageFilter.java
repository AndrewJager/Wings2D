package com.wings2d.framework.imageFilters;

import com.wings2d.framework.WingsImage;

/**
 * Interface for all image filters. 
 */
public interface ImageFilter
{
	/**
	 * Return the value to be displayed in the UI
	 * @return A likely incomplete amount of information about the filter
	 */
	public abstract String toString();
	/**
	 * Run the filter 
	 * @param img {@link com.wings2d.framework.Image Image} to run the filter over
	 */
	public abstract void filter(WingsImage img);

	/**
	 * Gets the string to be used to save the filter to the file
	 * @return A string that contains all information about the filter
	 */
	public abstract String getFileString();
	
	/**
	 * Gets the name of the filter in plain English 
	 * @return Name of the filter, with spaces
	 */
	public abstract String getFilterName();
	
	/**
	 * Used to separate parts of the string when saving to a file
	 */
	final String DELIMITER = "~";
}