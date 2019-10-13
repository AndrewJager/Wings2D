package entities;

import java.awt.Color;

import javax.swing.JFrame;

import framework.Button;
import framework.Image;
import framework.Level;

public class Menu extends Level{
	private Button b;
	public Menu(GameLevels thisLevel, JFrame frame) {
		super(thisLevel, frame);
		b = new Button(100, 20, 80, 25, Color.GREEN);
//		Outline arrow = new Outline();
//		arrow.addPoint(0, 0);
//		arrow.addPoint(15, 8);
//		arrow.addPoint(0, 15);
//		Image img = new Image(arrow, Color.BLUE);
//		b.setImage(img);
		
		this.addUI(b);
	}

	@Override
	public void update(KeyState keys)
	{
		if (keys.enter_key)
		{
			this.getManager().setLevel(GameLevels.TEST);
		}
		if (b.getClicked())
		{
			this.getManager().setLevel(GameLevels.TEST);
		}
		
		super.update(keys);
	}
}
