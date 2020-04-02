package framework.animation;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import framework.Level;



public class Frame {
	/** Options used by editor. Should not be used by framework */
	public static class EditOptions {
		private Boolean cascadeChanges;
		private Boolean editing;
		private Boolean rotating;
		private Boolean scaling;
		
		public EditOptions()
		{
			cascadeChanges = true;
			editing = false;
			rotating = false;
			scaling = false;
		}

		public Boolean getCascadeChanges() {
			return cascadeChanges;
		}

		public void setCascadeChanges(Boolean cascadeChanges) {
			this.cascadeChanges = cascadeChanges;
		}

		public Boolean getEditing() {
			return editing;
		}

		public void setEditing(Boolean editing) {
			this.editing = editing;
		}

		public Boolean getRotating() {
			return rotating;
		}

		public void setRotating(Boolean rotating) {
			this.rotating = rotating;
		}

		public Boolean getScaling() {
			return scaling;
		}

		public void setScaling(Boolean scaling) {
			this.scaling = scaling;
		}
	}

	/** Name of this frame */
	private String name;
	/** Animation that this Frame belongs to */
	private Animation parent;
	/** Level that this Frame belongs to */
	private Level level;
	/** Options object for editor */
	private EditOptions options;
	/** The joints for this frame */
	private List<Joint> joints;
	/** Time this frame will display for */
	private double frameTime;
	
	
	// Editor stuff
	private boolean editorIsMoving;
	private Point2D editorObjLoc;
	private int editorSelectedPoint;
	private Frame editorChild = null;
	private int editorFrameTime;
	private int editorTimePassed;
	
	public Frame(Animation parent, String name)
	{
		this.name = name;
		this.parent = parent;
		this.level = parent.getLevel();
		this.joints = new ArrayList<Joint>();
		this.frameTime = 0; // Use the Animations delay time by default
		this.editorFrameTime = 100;
		this.editorTimePassed = 0;
	}
	
	public Frame (Animation parent, String name, Frame editParent)
	{
		this(parent, name);
		editParent.setEditorChild(this);
		
		for (int i = 0; i < editParent.getJoints().size(); i++)
		{
			this.addJoint(editParent.getJoint(i).copy());
		}
	}
	
	public void saveToFile(PrintWriter out)
	{
		out.write("FRAME:" + name + "\n");
		out.write("TIME:" + editorFrameTime + "\n");
		for (int i = 0; i < joints.size(); i++)
		{
			joints.get(i).saveToFile(out);
		}
		out.write("\n");
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return name;
	}
	public Animation getAnimation()
	{
		return parent;
	}
	public Level getLevel()
	{
		return level;
	}
	public void addJoint(Joint joint)
	{
		joints.add(joint);
	}
	public void addNewJoint(String jointName)
	{
		joints.add(new Joint(this, jointName));
	}
	public List<Joint> getJoints()
	{
		return joints;
	}
	public Joint getJoint(int joint)
	{
		return joints.get(joint);
	}
	
	public Joint getJointByName(String name)
	{
		for (int i = 0; i < joints.size(); i++)
		{
			if (joints.get(i).getName().equals(name))
			{
				return joints.get(i);
			}
		}
		return null; // No object found
	}
	public String[] getJointNames()
	{
		String[] names = new String[joints.size()];
		for (int i = 0; i < joints.size(); i++)
		{
			names[i] = joints.get(i).getName();
		}
		return names;
	}
	public double getFrameTime() {
		return frameTime;
	}

	public void setFrameTime(double frameTime) {
		this.frameTime = frameTime;
	}
	public void generateImages()
	{
		for (int i = 0; i < joints.size(); i++)
		{
			joints.get(i).makeImage();
		}
	}


	public void render(Graphics2D g2d, boolean debug)
	{
		for(int i = 0; i < joints.size(); i++)
		{
			joints.get(i).render(g2d, debug);
		}
	}
	
	

	// Editor logic processing
	public EditOptions getEditOptions()
	{
		return options;
	}
	public void setEditOptions(EditOptions options)
	{
		this.options = options;
	}
	public void setEditorChild(Frame child)
	{
		this.editorChild = child;
	}
	public int getTimePassed() {
		return editorTimePassed;
	}
	public void setTimePassed(int timePassed) {
		this.editorTimePassed = timePassed;
	}
	public int getEditorFrameTime() {
		return editorFrameTime;
	}
	public void setEditorFrameTime(int frameTime) {
		this.editorFrameTime = frameTime;
	}
	public void addDefaultJoint(String jointName)
	{
		joints.add(new Joint(this, jointName));
		Joint curJoint = joints.get(joints.size() - 1);
		Path2D path = new Path2D.Double();
		curJoint.setPath(path);
		curJoint.addPoint(50, 0);
		curJoint.addPoint(100, 100);
		curJoint.addPoint(0, 100);
		
		if (editorChild != null)
		{
			editorChild.addDefaultJoint(jointName);
		}
	}
	public void processMousePress(int selected, Point mouseLoc)
	{
		editorIsMoving = false;
		Joint joint = getJoint(selected);
		if (options.getEditing())
		{   
		    List<Ellipse2D> circles = new ArrayList<Ellipse2D>();
		    for (int i = 0; i < joint.getPoints().size(); i++)
		    {
		    	circles.add(new Ellipse2D.Double(joint.getPoints().get(i).getX() - 6,
		    			joint.getPoints().get(i).getY() - 6, 12, 12));
		    }
		    
		    for (int i = 0; i < circles.size(); i++)
		    {
		    	if (circles.get(i).contains(mouseLoc))
		    	{
		    		editorIsMoving = true;
		    		editorObjLoc = new Point2D.Double(circles.get(i).getCenterX(), circles.get(i).getCenterY());
		    		editorSelectedPoint = i;
		    	}
		    }
		}
		else
		{
			Ellipse2D circle = new Ellipse2D.Double(joint.getPath().getBounds2D().getCenterX() - 6 + joint.getX(),
					joint.getPath().getBounds2D().getCenterY() - 6 + joint.getY(), 12, 12);
			
			if (circle.contains(mouseLoc))
			{
				editorIsMoving = true;
				editorObjLoc = new Point2D.Double(joint.getPath().getBounds2D().getCenterX() + joint.getX(),
						joint.getPath().getBounds2D().getCenterY() + joint.getY());
			}
		}
	}
	public void processMouseRelease(String selected, Point mouseLoc)
	{
		
		if (editorIsMoving)
		{
			editorIsMoving = false;
			double xTranslate = mouseLoc.x - editorObjLoc.getX();
			double yTranslate = mouseLoc.y - editorObjLoc.getY();
			if (options.getEditing())
			{
				Point2D newPointLoc = new Point2D.Double(mouseLoc.getX(), mouseLoc.getY());
				Joint joint = getJointByName(selected);
				if (joint != null)
				{
					joint.setPoint(editorSelectedPoint, newPointLoc);
					if (editorChild != null)
					{
						editorChild.childCopyPoints(joint, selected, joint.getPoints());
					}
				}
			}
			else
			{	
				moveObject(selected, xTranslate, yTranslate);
			}
		}
	}
	
	private void childCopyPoints(Joint parentJoint, String jointName, List<Point2D> points)
	{
		Joint joint = getJointByName(jointName);
		if (joint != null)
		{
			double xOffset = joint.getPath().getBounds2D().getX() - parentJoint.getPath().getBounds2D().getX();
			double yOffset = joint.getPath().getBounds2D().getY() - parentJoint.getPath().getBounds2D().getY();
			joint.setPoints(new ArrayList<Point2D>());
			for (int i = 0; i < points.size(); i++)
			{
				joint.addPoint(points.get(i).getX(), points.get(i).getY());
			}
			AffineTransform transform = new AffineTransform();
			transform.translate(xOffset, yOffset);
			joint.getPath().transform(transform);
			
			if (editorChild != null) // Copy to child frame
			{
				editorChild.childCopyPoints(parentJoint, jointName, points);
			}
		}
	}
	
	private void moveObject(String jointName, double xTranslate, double yTranslate)
	{
		Joint joint = getJointByName(jointName);
		if (joint != null)
		{
			AffineTransform transform = new AffineTransform();
			transform.translate(xTranslate, yTranslate);
			joint.setX(joint.getX() + xTranslate);
			joint.setY(joint.getY() + yTranslate);
			if (editorChild != null && options.getCascadeChanges())
			{
				editorChild.moveObject(jointName, xTranslate, yTranslate);
			}
		}
	}

	public void addVertex(int selected)
	{
		if (selected != -1)
		{
			Joint joint = joints.get(selected);
			joint.addPoint(joint.getPath().getBounds2D().getX(), joint.getPath().getBounds2D().getX());
			if (editorChild != null)
			{
				editorChild.addVertex(selected);
			}
		}
	}
	
	public void addNewJointFilter(String filterName, String jointName)
	{
		if (editorChild != null && options.getCascadeChanges())
		{
			Joint joint = editorChild.getJointByName(jointName);
			joint.addNewFilter(filterName); // Will call the child frames addNewJointFillter in a roundabout way
		}
	}
	
	public void swapJointFilters(String jointName, int a, int b)
	{
		if (editorChild != null && options.getCascadeChanges())
		{
			Joint thisJoint = getJointByName(jointName); 
			Joint joint = editorChild.getJointByName(jointName);
			if (joint.getFilters().size() == thisJoint.getFilters().size()) // Don't swap if filter lists are clearly different. Should probably rework this to actually compare the filter lists.
			{
				joint.swapFilters(a, b); // Will call the child frame's swapJointFilters in a roundabout way
			}
		}
	}
	
	public void deleteJointFilter(String jointName, int filter)
	{
		if (editorChild != null && options.getCascadeChanges())
		{
			Joint thisJoint = getJointByName(jointName); 
			Joint joint = editorChild.getJointByName(jointName);
			if (joint.getFilters().size() == thisJoint.getFilters().size()) // Don't swap if filter lists are clearly different. Should probably rework this to actually compare the filter lists.
			{
				joint.deleteFilter(filter); // Will call the child frame's deleteJointFilter in a roundabout way
			}
		}
	}
}
