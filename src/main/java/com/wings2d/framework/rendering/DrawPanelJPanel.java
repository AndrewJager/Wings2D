package com.wings2d.framework.rendering;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.wings2d.framework.core.Game;

public class DrawPanelJPanel extends JPanel{
	/** Convert height to width **/
	private static final double NINE_TO_SIXTEEN = 1.77777778;
	/** Convert width to height **/
	private static final double SIXTEEN_TO_NINE = 0.5625;
	/** Reference to the game, used to call the render function */
	protected Game game;
	
	public DrawPanelJPanel(final Game game)
	{
		super();
		this.game = game;
	}
	
	@Override
	public void paintComponent(final Graphics g)
	{
		super.paintComponent(g);
		game.render((Graphics2D)g);
	}
	
	public Graphics2D getGraphics2D() {
		return (Graphics2D)this.getGraphics();
	}

	public void initGraphics() {
		
	}

	public boolean afterRender() {
		return false;
	}

	public void render() {
		this.repaint();
	}
	
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
