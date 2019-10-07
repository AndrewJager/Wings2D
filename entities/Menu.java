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
	public Menu(GameLevels thisLevel, JFrame frame) {
		super(thisLevel, frame);
		
		JButton b = new JButton("Hello");
		b.setBounds(50,60,95,30);
		frame.add(b);
	}

	@Override
	public void update(ArrayList<Integer> keys)
	{
		KeyMapping mapping = this.getManager().getKeyMapping();
		if (keys.contains(mapping.getKey("Enter")))
		{
			this.getManager().setLevel(GameLevels.TEST);
		}
		
		super.update(keys);
	}
}
