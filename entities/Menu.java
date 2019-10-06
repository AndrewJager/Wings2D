package entities;

import java.awt.Color;
import java.util.ArrayList;

import framework.Button;
import framework.Image;
import framework.KeyMapping;
import framework.Level;
import framework.LevelManager;
import framework.Shape;

public class Menu extends Level{
	private Button click;
	public Menu(GameLevels thisLevel) {
		super(thisLevel);
		click = new Button(100, 10, 80, 20, Color.GREEN);
		Shape shape = new Shape();
		shape.addPoint(0, 0);
		shape.addPoint(10, 0);
		shape.addPoint(5, 10);
		Image img = new Image(shape, Color.BLUE);
		click.setImage(img);
		
		this.addUI(click);
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
