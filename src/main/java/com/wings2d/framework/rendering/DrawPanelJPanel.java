package com.wings2d.framework.rendering;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.wings2d.framework.Game;

public class DrawPanelJPanel extends DrawPanel{
	private class DrawingPanel extends JPanel{
		private Game game;
		
		public DrawingPanel(final Game game) {
			this.game = game;
		}
		
		@Override
		public void paintComponent(final Graphics g)
		{
			super.paintComponent(g);
			game.render((Graphics2D)g);
		}
	}
	
	private JPanel panel;
	
	public DrawPanelJPanel(final Game game)
	{
		super(game);
		panel = new DrawingPanel(game);
	}

	@Override
	protected void setSize(final Dimension dim) {
		panel.setSize(dim);
	}

	@Override
	protected void setLocation(final int xPos, final int yPos) {
		panel.setLocation(xPos, yPos);
	}

	@Override
	public int getWidth() {
		return panel.getWidth();
	}

	@Override
	public int getHeight() {
		return panel.getHeight();
	}

	@Override
	public Component getCanvas() {
		return panel;
	}

	@Override
	public Graphics2D getGraphics() {
		return (Graphics2D)panel.getGraphics();
	}

	@Override
	public void initGraphics() {
		
	}

	@Override
	public boolean afterRender() {
		return false;
	}

	@Override
	public void render() {
		panel.repaint();
	}
}
