package framework.text;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Starts off displaying nothing, and randomly chooses a character to start displaying, until the entire text is displaying
 */
public class RandomCharPop extends DisplayableText{
	/** Full text to display */
	private String text;
	/** Indexes of characters not currently displaying */
	private List<Integer> freeChars;
	/** List of booleans to control which characters to display */
	private List<Boolean> charsToDisplay;
	/** How many characters are not being displayed */
	private int remainingChars;
	/** Time between each character being added to the display. Char display time = time / num of chars. */
	private double charTime;
	/** Time keeping variable */
	private double count;
	
	/**
	 * Creates a new RandomCharPop object
	 * @param text The text to display
	 * @param time The total time from start to displaying the entire text. 
	 */
	public RandomCharPop(String text, double time)
	{
		this.text = text;
		this.charTime = time / text.length();
		this.count = 0;
		freeChars = new ArrayList<Integer>();
		charsToDisplay = new ArrayList<Boolean>();
		remainingChars = text.length();
		
		for (int i = 0; i < text.length(); i++)
		{
			freeChars.add(i);
			charsToDisplay.add(false);
		}
	}

	@Override
	public void update(double dt) {
		if (remainingChars > 0)
		{
			count = count + dt;
			if (count > charTime)
			{
				Random rand = new Random();
				int newCharIndex = rand.nextInt(freeChars.size());
				int newChar = freeChars.get(newCharIndex);
				charsToDisplay.set(newChar, true);
				freeChars.remove(newCharIndex);
				remainingChars--;
				count = 0;
			}
		}
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setFont(getFont());
		if (remainingChars > 0)
		{
			for (int i = 0; i < text.length(); i++)
			{
				if (charsToDisplay.get(i) == true)
				{
					String str = text.substring(0, i);
					int width = g2d.getFontMetrics().stringWidth(str);
					Point2D drawPoint = new Point2D.Double(getX() + width, getY());
					g2d.drawString(String.valueOf(text.substring(i, i + 1)), (float)drawPoint.getX(), (float)drawPoint.getY());
				}
			}
		}
		else
		{
			g2d.drawString(text, (float)getX(), (float)getY());
		}
	}
}
