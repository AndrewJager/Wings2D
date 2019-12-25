package framework;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;


public class TextBox extends UIElement{
	private String text;
	private Color backgroundColor;
	private Color textColor;
	private Image icon;
	private Point2D textDrawPoint;
	/** Maximum characters in line before it wraps **/
	private int lineLimit = 80;
	private int fontSize = 12;
	private int lineHeight = 18;
	private Font font;
	private List<String> lines;
	
	private double x = 200;
	private double y = 400;
	private double height = 100;
	private double width = 550;
	
	public TextBox(String text, Level level)
	{
		double scale = level.getManager().getScale();
		this.text = text;
		this.backgroundColor = Color.BLACK;
		this.textColor = Color.WHITE;
		x = x * scale;
		y = y * scale;
		height = height * scale;
		width = width * scale;
		
		fontSize = (int)(fontSize * scale);
		lineHeight = (int)(lineHeight * scale);
		this.font = new Font("TimesRoman", Font.ITALIC, fontSize);
		
		textDrawPoint = new Point2D.Double(x + (x * 0.5), y + (y * 0.05));
		
		lines = new ArrayList<String>();
		int charactersIndexed = 0;
		while (charactersIndexed < text.length())
		{
			lines.add(Utils.safeSubstring(text, charactersIndexed, charactersIndexed + lineLimit).trim());
			charactersIndexed = charactersIndexed + lineLimit;
		}
	}

	public void renderUI(Graphics2D g2d, boolean debug) {
		g2d.setColor(backgroundColor);
		g2d.fillRect((int)x, (int)y, (int)width, (int)height);
		g2d.setColor(textColor);
		g2d.setFont(font);
		
		int lineOffset = 0;
		for (int i = 0; i < lines.size(); i++)
		{
			g2d.drawString(lines.get(i), (int)textDrawPoint.getX(), (int)textDrawPoint.getY() + lineOffset);
			lineOffset = lineOffset + lineHeight;
		}
	}

	public void updateUI(Point mouseClick) {
		
	}

}
