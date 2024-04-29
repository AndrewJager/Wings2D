package com.wings2d.framework.input;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.wings2d.framework.core.Level;
import com.wings2d.framework.core.Wings2DUtils;
import com.wings2d.framework.rendering.Image;


public class TextBox extends UIElement{
	/** Color of box containing the text **/
	private Color backgroundColor;
	/** Color of border around box **/
	private Color borderColor;
	/** Color of text **/
	private Color textColor;
	private Image icon;
	/** Used to determine the location of each line on the screen **/
	private Point2D textDrawPoint;
	/** Used to determine the location of the icon on the screen **/
	private Point2D imageDrawPoint;
	private String text;
	/** Maximum characters in line before it wraps **/
	private int lineLimit = 80;
	private int fontSize = 12;
	private int scaledFontSize;
	private int lineHeight = 18;
	private int scaledLineHeight;
	private int borderWidth = 2;
	private int scaledBorderWidth;
	private Font font;
	private List<String> lines;
	private Level level;
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	//Scaled coordinates
	private int scaledX;
	private int scaledY;
	private int scaledHeight;
	private int scaledWidth;
	
	
	public TextBox(String text, int x, int y, int width, int height, Level level)
	{
		this.text = text;
		this.level = level;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		makeTextBox();
	}
	public TextBox(String text, int x, int y, int width, int height, Image icon, Level level)
	{
		this(text, x, y, width, height, level);
		this.icon = icon;
		this.icon.setX(imageDrawPoint.getX());
		this.icon.setY(imageDrawPoint.getY());
	}
	
	private void makeTextBox()
	{
		double scale = level.getManager().getScale();
		this.backgroundColor = Color.BLACK;
		this.textColor = Color.WHITE;
		this.borderColor = Color.BLUE;
		this.scaledX = (int)(x * scale);
		this.scaledY = (int)(y * scale);
		this.scaledHeight = (int)(height * scale);
		this.scaledWidth = (int)(width * scale);
		
		scaledFontSize = (int)(fontSize * scale);
		scaledLineHeight = (int)(lineHeight * scale);
		scaledBorderWidth = (int)(borderWidth * scale);
		this.font = new Font("TimesRoman", Font.ITALIC, scaledFontSize);
		
		textDrawPoint = new Point2D.Double(scaledX + (scaledX * 0.5), scaledY + (scaledY * 0.05));
		imageDrawPoint = new Point2D.Double(scaledX + (scaledWidth * 0.02), scaledY + (scaledHeight * 0.1));
		
		lines = new ArrayList<String>();
		int charactersIndexed = 0;
		while (charactersIndexed < text.length())
		{
			lines.add(Wings2DUtils.safeSubstring(text, charactersIndexed, charactersIndexed + lineLimit).trim());
			charactersIndexed = charactersIndexed + lineLimit;
		}
	}
	
	@Override
	public void renderUI(Graphics2D g2d, boolean debug) {
		g2d.setColor(borderColor);
		g2d.fillRect(scaledX - scaledBorderWidth, scaledY - scaledBorderWidth, scaledWidth + (scaledBorderWidth * 2), scaledHeight + (scaledBorderWidth * 2));
		g2d.setColor(backgroundColor);
		g2d.fillRect(scaledX, scaledY, scaledWidth, scaledHeight);
		g2d.setColor(borderColor);
		g2d.fillRect((int)(textDrawPoint.getX() - (scaledWidth * 0.03)), scaledY - scaledBorderWidth, scaledBorderWidth, scaledHeight + scaledBorderWidth);
		g2d.setColor(textColor);
		if (!g2d.getFont().equals(font))
		{
			g2d.setFont(font);
		}
		
		int lineOffset = 0;
		for (int i = 0; i < lines.size(); i++)
		{
			g2d.drawString(lines.get(i), (int)textDrawPoint.getX(), (int)textDrawPoint.getY() + lineOffset);
			lineOffset = lineOffset + scaledLineHeight;
		}
		
		if (icon != null)
		{
			icon.render(g2d, debug);
		}
	}
	@Override
	public void updateUI(Point mouseClick) {
		
	}
	@Override
	public void rescale()
	{
		makeTextBox();
		if (icon != null)
		{
			icon.setX(imageDrawPoint.getX());
			icon.setY(imageDrawPoint.getY());
			icon.rescale();
		}
	}
	
	public Image getIcon() {
		return icon;
	}
	public void setIcon(Image icon) {
		this.icon = icon;
	}

}
