package com.wings2d.framework.rendering;

import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * Class for the main drawing {@link java.awt.Canvas Canvas}
 * Canvas is always kept at a 16:9 display ratio
 */
public abstract class DrawPanel {
	/** Convert height to width **/
	private static final double NINE_TO_SIXTEEN = 1.77777778;
	/** Convert width to height **/
	private static final double SIXTEEN_TO_NINE = 0.5625;
	
	protected abstract void setSize(final Dimension dim);
	protected abstract void setLocation(final int xPos, final int yPos);
	public abstract int getWidth();
	public abstract int getHeight();

	/**
	 * Resize the canvas to be the largest it can be at a 16:9 display ratio. Canvas is positioned in the center of the container.
	 * @param container The container the canvas is inside. Uses the width and height of this container to 
	 * calculate the size of the canvas
	 */
	public void resizePanel(final JPanel container) {
		int width = container.getWidth();
        int height = container.getHeight();

    	if ((width * SIXTEEN_TO_NINE) <= height )
    	{
    		setSize(new Dimension(width, (int)(width * SIXTEEN_TO_NINE)));
    	}
    	else
    	{
    		setSize(new Dimension((int)(height * NINE_TO_SIXTEEN), height));
    	}
    	int xPos = (container.getWidth() - getWidth()) / 2;
    	int yPos = (container.getHeight() - getHeight()) / 2;
    	setLocation(xPos, yPos);
        container.revalidate();
    }
}
