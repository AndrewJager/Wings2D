package entities;

import java.awt.Color;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

import framework.Button;
import framework.CustomWindow;
import framework.Image;
import framework.KeyMapping;
import framework.Level;
import framework.LevelManager;
import framework.Shape;

public class Menu extends Level{
	private Button b;
	public Menu(GameLevels thisLevel, JFrame frame) {
		super(thisLevel, frame);
		b = new Button(100, 20, 80, 25, Color.GREEN);
		
		this.addUI(b);
	}

	@Override
	public void update(ArrayList<Integer> keys)
	{
		KeyMapping mapping = this.getManager().getKeyMapping();
		if (keys.contains(mapping.getKey("Enter")))
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
