package com.wings2d.framework.misc;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * <p>A panel that manages the size and position of its children based on a user defined grid. The cells in the grid
 * will always be the same size, regardless of their content. It may be helpful to think of this as a 
 * {@link java.awt.GridLayout GridLayout} where components can take up multiple cells. If a finer level of control 
 * is required, then an alternative such as {@link java.awt.GridBagLayout GridBagLayout} should be considered
 * (however, an inability to get GridBagLayout to behave like this class was the motivation to create this class)</p>
 * 
 * <p>Cell size is set as a percentage of the size of the FixedGrid. For example, a 3 x 2 grid with a width of 150 and a 
 * height of 50 will result in cells with widths of 50 and heights of 25. Cells will be automatically resized when the 
 * FixedGrid is resized.</p>
 * 
 * <p>FixedGrids can be used as children of other FixedGrids, allowing for for grids within grids</p>
 * 
 * <b>Example</b><br>
 * <code>FixedGrid myGrid = new FixedGrid(4, 3) </code> An empty 4 x 3 grid is created:<br>
 * ▢ ▢ ▢ ▢ <br>
 * ▢ ▢ ▢ ▢ <br>
 * ▢ ▢ ▢ ▢ <br> <br>
 * <code>myGrid.add(new JPanel(), 0, 0, 1, 1) </code> Add a panel in the top left cell <br>
 * ■ ▢ ▢ ▢ <br>
 * ▢ ▢ ▢ ▢ <br>
 * ▢ ▢ ▢ ▢ <br> <br>
 * <code>myGrid.add(new JPanel(), 0, 1, 4, 1) </code> Add a panel across the center row <br>
 * ■ ▢ ▢ ▢ <br>
 * ■ ■ ■ ■ <br>
 * ▢ ▢ ▢ ▢ <br> <br>
 * <code>myGrid.add(new JPanel(), 2, 2, 2, 1) </code> Finally, add a panel across the final two cells of the bottom row <br>
 * ■ ▢ ▢ ▢ <br>
 * ■ ■ ■ ■ <br>
 * ▢ ▢ ■ ■ <br> <br>
 * The remaining cells will stay empty
 */
@SuppressWarnings("serial")
public class FixedGrid extends JPanel{
	private record ChildComp(Component panel, int x, int y, int length, int height) {}
	
	private int width;
	private int height;
	private List<ChildComp> panels;
	
	private static final String ERR_UNSUPPORTED = "Method not supported! Use addChild instead.";

	/**
	 * Constructs a new FixedGrid panel
	 * @param gridWidth How many columns width the grid should be. Must be at least one.
	 * @param gridHeight How many columns hight the grid should be. Must be at least one.
	 */
	public FixedGrid(final int gridWidth, final int gridHeight) {
		validateGreaterThanZero(gridWidth, "Grid Width");
		validateGreaterThanZero(gridHeight, "Grid Height");
		
		this.width = gridWidth;
		this.height = gridHeight;
		panels = new ArrayList<ChildComp>();

		this.setLayout(null);
		
		this.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				recalcChildren();
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {}
			@Override
			public void componentShown(ComponentEvent e) {}
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
	}
	
	/**
	 * Add a component as a child of this panel
	 * @param comp {@link java.awt.Component Component} to be added.
	 * @param x The horizontal location in the grid to add the component to. Begins with 0.
	 * @param y The vertical location in the grid to add the component to. Begins with 0.
	 * @param length Amount of horizontal cells to use for this component. Must be equal to or greater than 1.
	 * @param height Amount of vertical cells to use for this component. Must be equal to or greater than 1.
	 */
	public void addChild(final Component comp, final int x, final int y, final int length, final int height) {
		validateNonNegative(x, "X");
		validateNonNegative(y, "Y");
		validateGreaterThanZero(length, "Length");
		validateGreaterThanZero(height, "Height");
		
		super.add(comp);
		panels.add(new ChildComp(comp, x, y, length, height));
		recalcChildren();
	}
	
	private void recalcChildren() {
		int colWidth = this.getWidth() / width;
		int colHeight = this.getHeight() / height;
		
		for (int i = 0; i < panels.size(); i++) {
			ChildComp panel = panels.get(i);
			Component c = panel.panel;
			c.setLocation(panel.x * colWidth, panel.y * colHeight);
			
			c.setSize(new Dimension(panel.length * colWidth, panel.height * colHeight));
		}
	}
	
	private void validateGreaterThanZero(final int val, final String valName) {
		if (val <= 0) {
			throw new IllegalArgumentException(valName + " must be at least 1! (value recived: " + val + ")");
		}
	}
	
	private void validateNonNegative(final int val, final String valName) {
		if (val < 0) {
			throw new IllegalArgumentException(valName + " must not be negative! (value recived: " + val + ")");
		}
	}
	
	/**
	 * Cannot be used in FixedGrid, as the required information is not provided. 
	 * Use {@link #addChild(Component, int, int, int, int) addChild} instead.
	 */
	@Override
	public Component add(Component comp) {
		throw new UnsupportedOperationException(ERR_UNSUPPORTED);
	}
	/**
	 * Cannot be used in FixedGrid, as the required information is not provided. 
	 * Use {@link #addChild(Component, int, int, int, int) addChild} instead.
	 */
	@Override
	 public Component add(String name, Component comp) {
		throw new UnsupportedOperationException(ERR_UNSUPPORTED);
	}
	/**
	 * Cannot be used in FixedGrid, as the required information is not provided. 
	 * Use {@link #addChild(Component, int, int, int, int) addChild} instead.
	 */
	@Override
	public Component add(Component comp, int index) {
		throw new UnsupportedOperationException(ERR_UNSUPPORTED);
	}
	/**
	 * Cannot be used in FixedGrid, as the required information is not provided. 
	 * Use {@link #addChild(Component, int, int, int, int) addChild} instead.
	 */
	@Override
	public void add(Component comp, Object constraints) {
		throw new UnsupportedOperationException(ERR_UNSUPPORTED);
	}
	/**
	 * Cannot be used in FixedGrid, as the required information is not provided. 
	 * Use {@link #addChild(Component, int, int, int, int) addChild} instead.
	 */
	@Override
	public void add(Component comp, Object constraints, int index) {
		throw new UnsupportedOperationException(ERR_UNSUPPORTED);
	}
}
