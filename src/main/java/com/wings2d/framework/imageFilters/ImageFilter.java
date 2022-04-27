package com.wings2d.framework.imageFilters;

import java.awt.image.BufferedImage;

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
	 * @param img {@link com.wings2d.framework.rendering.Image Image} to run the filter over
	 */
	public abstract void filter(BufferedImage img);

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
	 * Uses {@link FilterFactory#fromFileString(String)} to create a copy of the filter
	 * @return A copy of the filter
	 */
	public default ImageFilter copy() {
		return FilterFactory.fromFileString(getFileString());
	}
	
	/**
	 * Used to separate parts of the string when saving to a file
	 */
	final String FILTER_TOKEN = "~";
}