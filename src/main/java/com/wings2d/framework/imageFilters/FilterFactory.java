package com.wings2d.framework.imageFilters;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class FilterFactory {
	public static final String FILTER_NAME_TOKEN = "-";
	
	private static List<Class<? extends ImageFilter>> filterClasses = 
			new ArrayList<Class<? extends ImageFilter>>() 
			{
				{
					add(BasicVariance.class);
					add(DarkenFrom.class);
					add(LightenFrom.class);
				}
			};
	
	public static List<Class<? extends ImageFilter>> getFilterClasses()
	{
		return filterClasses;
	}
	
	public static ImageFilter fromFileString(final String filterString)
	{
		ImageFilter newFilter = null;
		String[] tokens = filterString.split(FILTER_NAME_TOKEN);
		Class<? extends ImageFilter> filterClass = null;
		for (int i = 0; i < filterClasses.size(); i++)
		{
			if (tokens[0].equals(filterClasses.get(i).getSimpleName()))
			{
				filterClass = filterClasses.get(i);
			}
		}
		if (filterClass == null)
		{
			throw new RuntimeException("No filter class for string " + tokens[0] + " found!");
		}
		
		try {
			Constructor<? extends ImageFilter> con = filterClass.getConstructor(String.class);
			newFilter = con.newInstance(tokens[1]);
		}
		catch (SecurityException | InstantiationException | IllegalAccessException 
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("File constructor not found for filter class " + filterClass.getSimpleName());
		}
		return newFilter;
	}
}
