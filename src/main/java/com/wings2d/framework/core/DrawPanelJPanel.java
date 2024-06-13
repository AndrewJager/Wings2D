package com.wings2d.framework.core;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
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

		
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				game.getManager().mouseClicked(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				game.getManager().mousePressed(e);	
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				game.getManager().mouseReleased(e);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				game.getManager().mouseEntered(e);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				game.getManager().mouseExited(e);
			}
		});
		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				game.getManager().mouseDragged(e);
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				game.getManager().mouseMoved(e);
			}
		});
		this.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				game.getManager().mouseWheelMoved(e);
			}
		});
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
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					// Use this method, not repaint(), to force draw and reduce stutters with moving graphics
					paintImmediately(0, 0, getWidth(), getHeight());
//					repaint();
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
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
    }
}
