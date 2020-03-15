package framework;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * Class for the main drawing {@link java.awt.Canvas Canvas}
 * Canvas is always kept at a 16:9 display ratio
 */
public class DrawPanel {
	/** Convert height to width **/
	private static final double NINE_TO_SIXTEEN = 1.77777778;
	/** Convert width to height **/
	private static final double SIXTEEN_TO_NINE = 0.5625;
	/** {@link java.awt.Canvas Canvas} to draw with */
	private Canvas canvas;
	
	/**
	 *	Initialize the canvas. Background color is set to DARK_GRAY. 
	 */
	public DrawPanel()
	{
        canvas = new Canvas();
        canvas.setBackground(Color.DARK_GRAY);
	}
	
	/**
	 * Get the canvas
	 * @return The {@link java.awt.Canvas Canvas} object used by this object
	 */
	public Canvas getCanvas()
	{
		return canvas;
	}
	/**
	 * Resize the canvas to be the largest it can be at a 16:9 display ratio
	 * @param container The container the canvas is inside. Uses the width and height of this container to 
	 * calculate the size of the canvas
	 */
	public void resizePreview(JFrame container) {
		int width = container.getWidth();
        int height = container.getHeight();

    	if ((width * SIXTEEN_TO_NINE) <= height )
    	{
    		canvas.setSize(new Dimension(width, (int)(width * SIXTEEN_TO_NINE)));
    	}
    	else
    	{
    		canvas.setSize(new Dimension((int)(height * NINE_TO_SIXTEEN), height));
    	}
        container.revalidate();
    }
}
