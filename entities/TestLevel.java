package entities;

import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;

import javax.swing.JFrame;

import framework.CustomWindow;
import framework.Image;
import framework.Joint;
import framework.KeyMapping;
import framework.Level;
import framework.LevelManager;
import framework.Shape;
import framework.Sprite;
import framework.SpriteSheet;

public class TestLevel extends Level{
	private Shape myShape;
	private Image img, img2, img3, eye_img;
	
	private Joint body, head, shoulder, elbow, r_eye, l_eye, arm, face;
	private Sprite one, two, three, four, five;
	private SpriteSheet sheet;
	
	private Polygon test;

	public TestLevel(GameLevels thisLevel, JFrame frame) {
		super(thisLevel, frame);

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
		shoulder.rotate(20);
		head.rotate(-45);
		four = new Sprite(body, head, shoulder, elbow, arm, face, r_eye, l_eye);
		shoulder.rotate(20);
		head.rotate(-90);
		five = new Sprite(body, head, shoulder, elbow, arm, face, r_eye, l_eye);
		sheet = new SpriteSheet(one, two, three, four, five);
		
		this.addObject(sheet);
	}
	@Override
	public void update(ArrayList<Integer> keys)
	{
		KeyMapping mapping = this.getManager().getKeyMapping();
		if (keys.contains(mapping.getKey("Esc")))
		{
			this.getManager().setLevel(GameLevels.MENU);
		}
		
		if (keys.contains(mapping.getKey("Right")))
		{
			
		}
		else if (keys.contains(mapping.getKey("Left")))
		{
			
		}
		else
		{
			
		}
		
		super.update(keys);
	}
}
