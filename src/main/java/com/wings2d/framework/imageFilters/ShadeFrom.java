package com.wings2d.framework.imageFilters;

import java.awt.Frame;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.wings2d.framework.misc.CardinalDir;

/**
 * Interface to allow lighten/darken from filters to work with the same editor
 */
public interface ShadeFrom {
	public static abstract class ShadeFromEdit<T extends ImageFilter> extends FilterEdit<T>{
		private static final long serialVersionUID = 1L;

		public enum ShadeType{
			LIGHTEN,
			DARKEN
		}
		
		private ShadeType type;
		private CardinalDir dir;
		private double amt;
		
		private JComboBox<CardinalDir> directionSelect;
		private SpinnerModel spinModel;
		private JSpinner filterAmt;
		
		public ShadeFromEdit(final Frame owner, final ShadeType type)
		{
			super(owner);
			this.type = type;
		}
		
		public ShadeFromEdit(final T filter, final Frame owner, final ShadeType type) {
			super(filter, owner);
			this.type = type;
		}

		public DarkenFrom getDarken()
		{
			dir = (CardinalDir) directionSelect.getSelectedItem();
			amt = (double) filterAmt.getValue();
			return new DarkenFrom(dir, amt);
		}
		public LightenFrom getLighten()
		{
			dir = (CardinalDir) directionSelect.getSelectedItem();
			amt = (double) filterAmt.getValue();
			return new LightenFrom(dir, amt);
		}
		
		@Override
		public void setup() {
			CardinalDir[] directions = new CardinalDir[4];
			for (int i = 0; i < CardinalDir.values().length; i++) 
			{
				directions[i] = CardinalDir.values()[i];
			}
			
			
			directionSelect = new JComboBox<CardinalDir>(directions);
			this.add(directionSelect);
			
			spinModel = new SpinnerNumberModel(1, 0.001, 100, 0.05);
			filterAmt = new JSpinner(spinModel);
			this.add(filterAmt);
		}
		
		@Override
		public void setValues(final T filter) {
			if (filter instanceof DarkenFrom) {
				DarkenFrom darken = (DarkenFrom)filter;
				directionSelect.setSelectedItem(darken.getDirection());
				filterAmt.setValue(darken.getAmt());
				
			}
			else if (filter instanceof LightenFrom) {
				LightenFrom lighten = (LightenFrom)filter;
				directionSelect.setSelectedItem(lighten.getDirection());
				filterAmt.setValue(lighten.getAmt());
			}
		}
		
		@Override
		public ImageFilter getFilter() {
			switch(type)
			{
			case LIGHTEN:
				return getLighten();
			case DARKEN:
				return getDarken();
			default:
				return null;
			}
		}
	}
	
	
	/**
	 * Get the direction the filter will be run from
	 * @return A ShadeDir containing the filter direction
	 */
	public abstract CardinalDir getDirection();
	/**
	 * Get the amount of shading
	 * @return Amount of shading specified by the filter
	 */
	public abstract double getAmt();
}
