package framework;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.List;


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
	/** Maximum characters in line before it wraps **/
	private int lineLimit = 80;
	private int fontSize = 12;
	private int lineHeight = 18;
	private int borderWidth = 2;
	private Font font;
	private List<String> lines;
	
	private int x = 200;
	private int y = 410;
	private int height = 60;
	private int width = 500;
	
	public TextBox(String text, Level level)
	{
		double scale = level.getManager().getScale();
		this.backgroundColor = Color.BLACK;
		this.textColor = Color.WHITE;
		x = (int)(x * scale);
		y = (int)(y * scale);
		height = (int)(height * scale);
		width = (int)(width * scale);
		
		fontSize = (int)(fontSize * scale);
		lineHeight = (int)(lineHeight * scale);
		borderWidth = (int)(borderWidth * scale);
		this.font = new Font("TimesRoman", Font.ITALIC, fontSize);
		
		textDrawPoint = new Point2D.Double(x + (x * 0.5), y + (y * 0.05));
		imageDrawPoint = new Point2D.Double(x + (width * 0.05), y + (height * 0.1));
		
		lines = new ArrayList<String>();
		int charactersIndexed = 0;
		while (charactersIndexed < text.length())
		{
			lines.add(Utils.safeSubstring(text, charactersIndexed, charactersIndexed + lineLimit).trim());
			charactersIndexed = charactersIndexed + lineLimit;
		}
	}
	public TextBox(String text, Image icon, Level level)
	{
		this(text, level);
		this.icon = icon;
		this.icon.setX(imageDrawPoint.getX());
		this.icon.setY(imageDrawPoint.getY());
	}

	public void renderUI(Graphics2D g2d, boolean debug) {
		g2d.setColor(borderColor);
		g2d.fillRect(x - borderWidth, y - borderWidth, width + (borderWidth * 2), height + (borderWidth * 2));
		g2d.setColor(backgroundColor);
		g2d.fillRect(x, y, width, height);
		g2d.setColor(textColor);
		if (!g2d.getFont().equals(font))
		{
			g2d.setFont(font);
		}
		
		int lineOffset = 0;
		for (int i = 0; i < lines.size(); i++)
		{
			g2d.drawString(lines.get(i), (int)textDrawPoint.getX(), (int)textDrawPoint.getY() + lineOffset);
			lineOffset = lineOffset + lineHeight;
		}
		
		if (icon != null)
		{
			icon.render(g2d, debug);
		}
	}

	public void updateUI(Point mouseClick) {
		
	}
	public Image getIcon() {
		return icon;
	}
	public void setIcon(Image icon) {
		this.icon = icon;
	}

}
