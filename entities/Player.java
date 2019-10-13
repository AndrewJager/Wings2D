package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import framework.GameObject;
import framework.Image;
import framework.Joint;
import framework.KeyMapping;
import framework.Level;
import framework.Sprite;
import framework.SpriteSheet;
import framework.Wall;
import framework.WallTypes;

public class Player extends GameObject{
	private PlayerStates state, targetState;
	private boolean facingRight = true, idle = true;
	private Map<PlayerStates, SpriteSheet> animations;
	private Level level;
	
	//private Shape myShape;
	private Image img, img2, img3, eye_img;
	private Joint body, head, eye, r_shoulder, l_shoulder, r_elbow, l_elbow, r_upper_arm, l_upper_arm,
		r_hip, l_hip, r_upper_leg, l_lower_leg;
	private Sprite one, two, three, four, five;
	private SpriteSheet sheet;
	private Rectangle floorCheck, r_check, l_check; 
	private List<Integer> jumpMotion; // List of y velocities for jumping
	
	private int x, y;
	private int SPEED = 3, CLIMBSPEED = 2, GRAVITY = 3;
	private boolean onGround, onRamp, canJump = false, jumping;
	private int jumpCount;
	
	public Player(Level level)
	{
		this.level = level;
		animations = new HashMap<PlayerStates, SpriteSheet>();
		this.x = 100;
		this.y = 300;
		
		jumpMotion = new ArrayList<Integer>();
		jumpMotion.add(8);
		jumpMotion.add(9);
		jumpMotion.add(6);
		jumpMotion.add(4);
		jumpMotion.add(3);
		jumpMotion.add(1);
		
		int w = 40;
		int h = 8;
		int xOffset = 0;
		int yOffset = 10;
		floorCheck = new Rectangle(x - (w / 2), y + yOffset - (h / 2), w, h);
		
		xOffset = 15;
		yOffset = -10;
		w = 10;
		h = 15;
		r_check = new Rectangle(x + xOffset - (w / 2), y + yOffset - (h / 2), w, h);
		l_check = new Rectangle(x - xOffset - (w / 2), y + yOffset - (h / 2), w, h);
		
		one = setToBaseSprite();
		sheet = new SpriteSheet(one);
		
		animations.put(PlayerStates.WALK_R, sheet);
		
		sheet = new SpriteSheet(one);
		animations.put(PlayerStates.WALK_L, sheet);
		sheet = new SpriteSheet(one);
		animations.put(PlayerStates.IDLE_R, sheet);
		animations.put(PlayerStates.IDLE_L, sheet);
		state = PlayerStates.WALK_R;
	}
	
	/**
	 * Sets all joints to starting position, and returns a new sprite
	 * @return Sprite
	 */
	public Sprite setToBaseSprite()
	{
		// create the joints
		body = new Joint(this.x, this.y);
		head = new Joint(body, 0, -15);
		
		// create shapes and images
		Path2D myShape = new Path2D.Double();
		myShape.append(new Rectangle2D.Double(0, 0, 10, 20), true);
		img = new Image(myShape, Color.BLACK);
		body.addImage(img);
		body.rotate(135);
		
//		myShape = new Outline();
//		myShape.addPoint(0, 0);  
//		myShape.addPoint(10, 5);
//		myShape.addPoint(0, 10);
//		img = new Image(myShape, Color.BLACK);
//		head.addImage(img);
		
		Sprite BaseSprite = new Sprite(body, head);
		return BaseSprite;
	}
	
	@Override
	public void update(KeyState keys) {
		int xVel = 0;
		int yVel = 0;
		animations.get(state).update(keys);
		idle = false;
		if (keys.right_key)
		{
			targetState = PlayerStates.WALK_R;
			xVel = SPEED;
		}
		if (keys.left_key)
		{
			targetState = PlayerStates.WALK_L;
			xVel = -SPEED;
		}
		if (keys.jump_key)
		{
			if (canJump)
			{
				jumping = true;
				jumpCount = 0;
			}
		}
		
		if (!keys.right_key
				&& !keys.left_key)
		{
			idle = true;
		}
		
		if (idle)
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
		
		onGround = false;
		onRamp = false;
		canJump = false;
		for (int i = 0; i < level.getWalls().size(); i++)
		{
			Wall wall = level.getWalls().get(i);
			if (floorCheck.intersectsLine(wall.getLine()))
			{
				if (wall.getType() == WallTypes.FLOOR)
				{
					onGround = true;
					canJump = true;
				}
				else if (wall.getType() == WallTypes.RAMP)
				{
					canJump = true;
				}
			}
			if (r_check.intersectsLine(wall.getLine()))
			{
				if (wall.getType() == WallTypes.RAMP)
				{
					onRamp = true;
					if (xVel > 0)
					{
						xVel = CLIMBSPEED;
						yVel = -CLIMBSPEED;
						
					}
				}
				else if (wall.getType() == WallTypes.WALL)
				{
					if (xVel > 0)
					{
						xVel = 0;
					}
				}
			}
			if (l_check.intersectsLine(wall.getLine()))
			{
				if (wall.getType() == WallTypes.RAMP)
				{
					onRamp = true;
					if (xVel < 0)
					{
						xVel = -CLIMBSPEED;
						yVel = -CLIMBSPEED;
						
					}
				}
				else if (wall.getType() == WallTypes.WALL)
				{
					if (xVel < 0)
					{
						xVel = 0;
					}
				}
			}
		}
		if (onGround && !onRamp)
		{
			yVel = 0;
		}
		else if (!onGround && !onRamp && !jumping)
		{
			yVel = GRAVITY;
		}
		if (jumping)
		{
			yVel = -jumpMotion.get(jumpCount);
			jumpCount++;
			if (jumpCount == jumpMotion.size())
			{
				jumpCount = 0;
				jumping = false;
			}
		}
		this.y += yVel;
		this.x += xVel;
		for (SpriteSheet sheet : animations.values()) {
			sheet.setTranslated(false);
		}
		for (SpriteSheet sheet : animations.values()) {
			if (!sheet.getTranslated())
			{
				sheet.translate(xVel, yVel);
			}
		}
		floorCheck.translate(xVel, yVel);
		r_check.translate(xVel, yVel);
		l_check.translate(xVel, yVel);
	}

	@Override
	public void render(Graphics2D g2d, boolean debug) {
		animations.get(state).render(g2d, debug);
		if (debug)
		{
			g2d.setColor(Color.GREEN);
//			g2d.fillRect(floorCheck.x, floorCheck.y, floorCheck.width, floorCheck.height);
//			g2d.fillRect(r_check.x, r_check.y, r_check.width, r_check.height);
//			g2d.fillRect(l_check.x, l_check.y, l_check.width, l_check.height);
		}
	}

}
