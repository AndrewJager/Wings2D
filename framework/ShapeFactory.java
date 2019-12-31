package framework;

import java.awt.Shape;
import java.awt.geom.GeneralPath;

public final class ShapeFactory {
	public static Shape triangle()
	{
		GeneralPath path = new GeneralPath();
		path.moveTo(5, 0);
		path.lineTo(10, 5);
		path.lineTo(0, 5);
		
		return path;
	}
}
