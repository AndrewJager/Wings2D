package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import framework.GameObject;
import framework.Image;
import framework.Joint;
import framework.KeyMapping;
import framework.Level;
import framework.Shape;
import framework.Sprite;
import framework.SpriteSheet;

public class Player extends GameObject{
	private Level level;
	private PlayerStates state, targetState;
	private boolean facingRight = true;
	private int idle = 0; // Count of how many frames from last commmand
	private final int IDLE_COUNT = 20;
	private Map<PlayerStates, SpriteSheet> animations;
	private double x, y;
	private Shape myShape;
	private Image img, img2, img3, eye_img;
	
	private Joint body, head, shoulder, elbow, r_eye, l_eye, arm, face;
	private Sprite one, two, three, four, five;
	private SpriteSheet sheet;
	
	public Player(Level level)
	{
		this.level = level;
		animations = new HashMap<PlayerStates, SpriteSheet>();
		
		// create some joints
		body = new Joint(200, 200);
		head = new Joint(body, 0, -20);
		shoulder = new Joint(body, 30, 0);
		elbow = new Joint(shoulder, 0, 40);
		arm = new Joint(shoulder, 0, 20);
		face = new Joint(head, 0, 10);
		r_eye = new Joint(head, 5, 2);
		l_eye = new Joint(head, -5, 2);
		
		// create a shape and image
		myShape = new Shape();
		myShape.addPoint(0, 10);  
		myShape.addPoint(10, 0);
		myShape.addPoint(20, 10);
		myShape.addPoint(10, 40);
		img = new Image(myShape, Color.BLACK);
		face.addImage(img);
		
		myShape = new Shape();
		myShape.addPoint(0, 0);
		myShape.addPoint(8, 0);
		myShape.addPoint(8, 40);
		myShape.addPoint(0, 40);
		img3 = new Image(myShape, Color.BLACK);
		arm.addImage(img3);
		
		myShape = new Shape();
		myShape.addPoint(0, 5);
		myShape.addPoint(2, 3);
		myShape.addPoint(5, 0);
		myShape.addPoint(7, 3);
		myShape.addPoint(9, 5);
		myShape.addPoint(7, 8);
		myShape.addPoint(5, 10);
		myShape.addPoint(2, 8);
		img2 = new Image(myShape, Color.BLUE);
		elbow.addImage(img2);
		
		myShape = new Shape();
		myShape.addPoint(0, 3);
		myShape.addPoint(3, 0);
		myShape.addPoint(6, 3);
		myShape.addPoint(3, 6);
		eye_img = new Image(myShape,Color.GREEN);
		r_eye.addImage(eye_img);
		l_eye.addImage(eye_img);
		
		
		one = new Sprite(body, head, shoulder, elbow, arm, face, r_eye, l_eye);
		shoulder.rotate(-45);
		head.rotate(-45);
		two = new Sprite(body, head, shoulder, elbow, arm, face, r_eye, l_eye);
		head.rotate(-45);
		shoulder.rotate(20);
		head.rotate(-45);
		three = new Sprite(body, head, shoulder, elbow, arm, face, r_eye, l_eye);
		sheet = new SpriteSheet(one, two, three);
		
		animations.put(PlayerStates.WALK_R, sheet);
		
		shoulder.rotate(20);
		head.rotate(-45);
		four = new Sprite(body, head, shoulder, elbow, arm, face, r_eye, l_eye);
		shoulder.rotate(20);
		head.rotate(-90);
		five = new Sprite(body, head, shoulder, elbow, arm, face, r_eye, l_eye);
		
		sheet = new SpriteSheet(four, five);
		animations.put(PlayerStates.WALK_L, sheet);
		sheet = new SpriteSheet(one);
		animations.put(PlayerStates.IDLE_R, sheet);
		animations.put(PlayerStates.IDLE_L, sheet);
		state = PlayerStates.WALK_R;
	}
	@Override
	public void update(KeyState keys) {
		animations.get(state).update(keys);
		
		if (keys.right_key)
		{
			targetState = PlayerStates.WALK_R;
			idle = 0;
		}
		if (keys.left_key)
		{
			targetState = PlayerStates.WALK_L;
			idle = 0;
		}
		
		if (!keys.right_key
				&& !keys.left_key)
		{
			idle++;
		}
		
		if (idle > IDLE_COUNT)
		{
			if (facingRight)
			{
				state = PlayerStates.IDLE_R;
			}
			else
			{
				state = PlayerStates.IDLE_L;
			}
		}
		
		if (targetState != null && targetState != state)
		{
			state = targetState;
			targetState = null;
			animations.get(state).reset();
		}
		System.out.println(keys);
		switch(state)
		{
		case IDLE_R:
			break;
		case IDLE_L:
			break;
		case WALK_R:
			facingRight = true;
			break;
		case WALK_L:
			facingRight = false;
		default:
			break;
		}
		
	}

	@Override
	public void render(Graphics2D g2d, boolean debug) {
		animations.get(state).render(g2d, debug);
		
	}

}
