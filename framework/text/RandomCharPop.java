package framework.text;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomCharPop extends DisplayableText{
	/** Full text to display */
	private String text;
	private char[] chars;
	/** Display text */
	private String display;
	/** Which characters in the string are currently displaying */
	private List<Integer> freeChars;
	private List<Point2D> drawPoints;
	/** Time to display the string. Char display time = time / num of chars. */
	private double time;
	private double charTime;
	private double count;
	
	public RandomCharPop(String text, double time)
	{
		this.text = text;
		this.time = time;
		this.charTime = time / text.length();
		this.count = 0;
		freeChars = new ArrayList<Integer>();
		drawPoints = new ArrayList<Point2D>();
		this.chars = new char[text.length()];
		
		for (int i = 0; i < text.length(); i++)
		{
			chars[i] = ' ';
			freeChars.add(i);
		}
		display = new String(chars);
	}

	@Override
	public void update(double dt) {
		if (freeChars.size() > 0)
		{
			count = count + dt;
			if (count > charTime)
			{
				int newChar;
				Random rand = new Random();
				newChar = rand.nextInt(freeChars.size());
				chars[freeChars.get(newChar)] = text.charAt(freeChars.get(newChar));
				freeChars.remove(newChar);
				display = new String(chars);
				count = 0;
			}
		}
	}

	@Override
	public void render(Graphics2D g2d) {
		if (freeChars.size() > 0)
		{
			for (int i = 0; i < text.length(); i++)
			{
				String str = text.substring(0, i);
				int width = g2d.getFontMetrics().stringWidth(str);
				Point2D drawPoint = new Point2D.Double(getX() + width, getY());
				g2d.drawString(String.valueOf(chars[i]), (float)drawPoint.getX(), (float)drawPoint.getY());
			}
		}
		else
		{
			g2d.drawString(display, (float)getX(), (float)getY());
		}
	}

}
