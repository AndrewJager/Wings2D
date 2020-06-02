package framework.animation;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import framework.GameObject;

public class AnimatedEntity extends GameObject{
	/** States for this entity */
	private List<String> states;
	/** SpriteSheets that are the animations for this entity */
	private List<SpriteSheet> anims;
	
	public AnimatedEntity()
	{
		states = new ArrayList<String>();
		anims = new ArrayList<SpriteSheet>();
	}
	
	public void loadFromFile()
	{
//		Scanner in = new Scanner("test/cat.txt");
//		in.useDelimiter(Pattern.compile("(\\n)")); // Regex. IDK
//		
//		SpriteSheet newSheet;
//		Sprite newSprite;
//		Joint newJoint;
//		List<Sprite> tempSprites;
//		
//		while(in.hasNext())
//		{
//			String line = in.next();
//			if (line.strip() != "")
//			{
//				String[] split = line.split(":");
//				String token = split[0];
//				String value = split[1];
//				
//				switch (token)
//				{
//					case "SPRITE":
//						break;
//					case "ANIM":
//						newSheet = new SpriteSheet(value);
//						anims.add(newSheet); 
//						break;
//					case "FRAME":
//						newSprite = new Sprite();
//						anims.get(anims.size() - 1).addSprite(newSprite);
//						break;
//					case "TIME":
//						tempSprites = anims.get(anims.size() - 1).getSprites();
//						tempSprites.get(tempSprites.size() - 1).setSpriteDelay(Integer.parseInt(value));
//						break;
//					case "OBJECT":
//						tempSprites = anims.get(anims.size() - 1).getSprites();
////						newJoint = new Joint();
//				}
//			}
//		}
	}

	@Override
	public void rescale() {
		// TODO Auto-generated method stub
		
	}
}
