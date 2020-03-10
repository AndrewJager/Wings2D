package framework;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

public class DrawPanel {
	private static final double NINE_TO_SIXTEEN = 1.77777778;
	private static final double SIXTEEN_TO_NINE = 0.5625;
	
	private Canvas canvas;
	
	public DrawPanel()
	{
        canvas = new Canvas();
        canvas.setBackground(Color.DARK_GRAY);
	}
	
	
	public Canvas getCanvas()
	{
		return canvas;
	}
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
