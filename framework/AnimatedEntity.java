package framework;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class AnimatedEntity extends GameObject{
	private List<SpriteSheet> animations;
	private StateMachine stateManager;
	
	public AnimatedEntity(SpriteSheet...anims)
	{
		animations = new ArrayList<SpriteSheet>();
		stateManager = new StateMachine();
		for (int i = 0; i < anims.length; i++)
		{
			animations.add(anims[i]);
		}
	}
	public void update() {
		// TODO Auto-generated method stub
		
	}
	public void render(Graphics2D g2d, boolean debug) {
		// TODO Auto-generated method stub
		
	}
}
