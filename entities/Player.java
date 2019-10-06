package entities;

import java.awt.Graphics2D;
import java.util.List;

import framework.GameObject;
import framework.SpriteSheet;

public class Player extends GameObject{
	private PlayerStates state;
	private List<SpriteSheet> animations;
	private double x, y;
	public Player(SpriteSheet...animations)
	{
		for (int i = 0; i < animations.length; i++)
		{
			this.animations.add(animations[i]);
		}
		state = PlayerStates.IDLE_R;
	}
	@Override
	public void update() {
		switch(state)
		{
		case IDLE_R:
			break;
		case IDLE_L:
			break;
		default:
			break;
		}
		
	}

	@Override
	public void render(Graphics2D g2d, boolean debug) {
		// TODO Auto-generated method stub
		
	}

}
