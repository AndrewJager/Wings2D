package framework;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;

import javax.swing.JFrame;

public class Button{
	private int x, y, width, height;
	private Color backgroundColor;
	private Image img;
	private boolean isClicked = false;
	
	public Button (int x, int y, int width, int height, Color backgroundColor)
	{
		this.x = x;
		this.y = y; 
		this.width = width;
		this.height = height;
		this.backgroundColor = backgroundColor;
	}
	public boolean getClicked()
	{
		return isClicked;
	}
	public void setImage(Image img)
	{
		this.img = img;
		this.img.setX(x + (width / 2));
		this.img.setY(y + (height / 2));
	}

	public void updateUI(JFrame frame, boolean mouseDown) {
		boolean isInside = false;
		if (frame.getMousePosition() != null)
		{
			double x = frame.getMousePosition().getX() - frame.getInsets().left;
			double y = frame.getMousePosition().getY() - frame.getInsets().top;
			if (x > this.x && x < (this.x + this.width))
			{
				if (y > this.y && y < (this.y + this.height))
				{
					isInside = true;
				}
			}
		}
		if (isInside && mouseDown)
		{
			isClicked = true;
		}
		//System.out.println(mouseDown);
	}

	public void renderUI(Graphics2D g2d, boolean debug) {
		g2d.setColor(backgroundColor);
		g2d.fillRect(x, y, width, height);
		if (img != null)
		{
			img.render(g2d, debug);
		}
	}
}
