package com.wings2d.framework.imageFilters;

import java.util.ArrayList;
import java.util.List;

public class FilterFactory {
	// I think I know what I'm doing?
	private static List<Class<? extends ImageFilter>> filterClasses = 
			new ArrayList<Class<? extends ImageFilter>>() 
			{
				{
					add(BasicVariance.class);
					add(BlurEdges.class);
					add(DarkenFrom.class);
					add(LightenFrom.class);
					add(Outline.class);
				}
			};
	
	public static List<Class<? extends ImageFilter>> getFilterClasses()
	{
		return filterClasses;
	}
}
