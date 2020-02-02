package game;

import java.awt.Color;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

import framework.Button;
import framework.Image;
import framework.Level;
import framework.LevelManager;

public class Menu extends Level{
	private Button b;
	public Menu(LevelManager manager, GameLevels thisLevel) {
		super(manager, thisLevel.ordinal());
		b = new Button(100, 20, 80, 25, Color.GREEN, this);
		Path2D shape = new Path2D.Double();
		shape.append(new RoundRectangle2D.Double(0, 0, 10, 5, 3, 3), true);
		Image img = new Image(shape, Color.BLACK, this);
		b.setImage(img);
		
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
