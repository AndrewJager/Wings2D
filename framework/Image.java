package framework;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import framework.imageFilters.ImageFilter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 * Custom Image class, based off a BufferedImage and a Shape. 
 */
public class Image {
	/** Shape used to generate the BufferedImage **/
	private Shape shape;
	/** Shape used when the first Image was created, before any rotations. **/
	private Shape ogShape;
	/** The actual image **/
	private BufferedImage image;
	/** Functions that are run over the BufferedImage, saved here to be used for copied or rotated Images **/
	private List<ImageFilter> filters;
	/** Base color to fill the shape with **/
	private Color color;
	/** Level that the Image is associated with **/
	private Level level;
	
	/** Width of the BufferedImage **/
	private int width;
	/** Height of the BufferedImage **/
	private int height;
	/** X position to draw image **/
	private double x;
	/** Y position to draw image **/
	private double y;
	/** Degrees the shape has been rotated from its original position **/
	private double rotation = 0;
	/** Number of Image objects that currently exist in the program **/
	private static int imageCount = 0;

	/**
	 * Create an image by filling in the provided shape with the provided color
	 * @param shape Shape that will be filled in. Can be any class that implements the Shape interface 
	 * @param color Color that the the shape will be filled with
	 * @param level Level that the Image is associated with
	 * @param scaleImg Scale the image by the levelManger scale value
	 */
	public Image(Shape shape, Color color, Level level, boolean scaleImg)
	{
		imageCount = getImageCount() + 1;
		this.level = level;
		this.x = 0;
		this.y = 0;
		this.shape = shape;
		this.ogShape = shape;
		double scale = level.getManager().getScale();
		Shape scaled = this.shape;
		if (scaleImg)
		{
			scaled = ShapeUtils.scale(this.shape, scale);
		}
		this.width = (int)Math.ceil(scaled.getBounds2D().getWidth());
		this.height = (int)Math.ceil(scaled.getBounds2D().getHeight());
		this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		this.color = color;
		this.filters = new ArrayList<ImageFilter>();
		for(int x = 0; x < width; x++) {
		    for(int y = 0; y < height; y++) {
		    	if (scaled.contains(x, y))
		    	{
		    		image.setRGB(x, y, this.color.getRGB());
		    	}
		    	else
		    	{
		    		image.setRGB(x, y, Color.TRANSLUCENT);
		    	}
		    }
		}
	}
	public Image(Shape shape, Color color, Level level)
	{
		this(shape, color, level, true);
	}
	/**
	 * Private constructor with the bare minimum of info needed to make a BufferedImage. Used when making a copy.
	 * @param width Width of new image
	 * @param height Height of new image
	 */
	private Image(int width, int height) {
		this.width = width;
		this.height = height;
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}
	/**
	 * Makes a copy of the Image, including all info such as shape and filters
	 * @return A new Image object
	 */
	public Image copy()
	{
		Image newImage = new Image(this.width, this.height);
		newImage.color = this.color;
		newImage.x = this.x;
		newImage.y = this.y;
		newImage.rotation = this.rotation;
		newImage.level = this.level;
		for (int x = 0; x < width; x++) 
		{
			for(int y = 0; y < height; y++) {
				newImage.image.setRGB(x, y, this.image.getRGB(x, y));
			}
		}
		newImage.filters = new ArrayList<ImageFilter>();
		for (int i = 0; i < this.filters.size(); i++)
		{
			newImage.addFilter(this.filters.get(i));
		}
		newImage.shape = this.shape;
		return newImage;
	}
	/**
	 * Rotate the image. This procedure does not rotate the BufferedImage, but makes a new Image with a rotated shape.
	 * @param angle Angle in degrees to rotate
	 */
	public void rotate(double angle) {
		this.image = null;
		this.rotation += Math.toRadians(angle);
		double scale = level.getManager().getScale();
		
		Shape newShape = ShapeUtils.rotateAround(this.ogShape, this.rotation, this.x + this.ogShape.getBounds2D().getCenterX(),
				this.y + this.ogShape.getBounds2D().getCenterY());
		
		// Rotated shape ends up offset for some reason. I have no idea why I have to do this.
		newShape = ShapeUtils.translate(newShape, -newShape.getBounds2D().getX(), -newShape.getBounds2D().getY());
		Image rotated = new Image(newShape, this.color, this.level);
		
		this.shape = ogShape;
		this.image = rotated.getImage();
		this.width = (int)Math.ceil(rotated.getShape().getBounds2D().getWidth() * scale);
		this.height = (int)Math.ceil(rotated.getShape().getBounds2D().getHeight() * scale);
		rotated.filters = new ArrayList<ImageFilter>();
		for (int i = 0; i < this.filters.size(); i++)
		{
			rotated.addFilter(this.filters.get(i));
		}
		this.x = rotated.getX();
		this.y = rotated.getY();
	}
	/** 
	 * Flip the image around the Y axis
	 */
	public void flipY()
	{
	    for (int x=0; x < image.getWidth(); x++)
	    {
	        for (int y=0; y < image.getHeight() / 2; y++)
	        {
	            int tmp = image.getRGB(x, y);
	            image.setRGB(x, y, image.getRGB(x, image.getHeight()-y-1));
	            image.setRGB(x, image.getHeight()-y-1, tmp);
	        }
	    }
	}
	/**
	 * Flip the image around the X axis
	 */
	public void flipX()
	{
	    for (int y=0; y < image.getHeight(); y++)
	    {
	        for (int x=0; x < image.getWidth() / 2; x++)
	        {
	            int tmp = image.getRGB(x, y);
	            image.setRGB(x, y, image.getRGB(image.getWidth()-x-1, y));
	            image.setRGB(image.getWidth()-x-1, y, tmp);
	        }
	    }
	}
	/**
	 * Adds the given filter to the Image's list of filters, and runs the filter.
	 * @param filter Can be any class the implements the ImageFilter interface
	 */
	public void addFilter(ImageFilter filter)
	{
		filters.add(filter);
		filter.filter(this);
	}
	
	/**
	 * Add a shape to the image. After this, rotation of the image will not work do to the new shapes not being saved.
	 * Previously applied filters are not run when this operation is done, be design.
	 * @param newShape Shape which will be added to the image
	 * @param color Color to fill the new shape with. The rest of the image will be the same color.
	 * @param xLoc X location to draw new shape at, relative to the top-left corner of the image.
	 * @param yLoc Y location to draw new shape at, relative to the top-left corner of the image.	
	 */
	public void addShape(Shape newShape, Color color, int xLoc, int yLoc)
	{
		newShape = ShapeUtils.scale(newShape, level.getManager().getScale());
		newShape = ShapeUtils.translate(newShape, -(newShape.getBounds2D().getX() - xLoc), newShape.getBounds2D().getY() - yLoc);
		Rectangle2D imageRect = new Rectangle2D.Double(0, 0, this.width, this.height);
		Rectangle2D newImageRect = new Rectangle2D.Double(xLoc, yLoc, newShape.getBounds2D().getWidth(), newShape.getBounds2D().getHeight());

		if (imageRect.contains(newImageRect))
		{
			
			for (int x = 0; x < this.getImage().getWidth(); x++)
			{
				for (int y = 0; y < this.getImage().getHeight(); y++)
				{
					if (newShape.contains(x, y))
					{
						this.getImage().setRGB(x, y, color.getRGB());
					}
				}
			}
		}
		else
		{
			Rectangle2D fullRect = imageRect.createUnion(newImageRect);
			BufferedImage newImage = new BufferedImage((int)fullRect.getWidth(), (int)fullRect.getHeight(), BufferedImage.TYPE_INT_ARGB);
			int imgOneXOffset = 0;
			int imgOneYOffset = 0;
			if (xLoc < 0)
			{
				imgOneXOffset = -xLoc;
				newShape = ShapeUtils.translate(newShape, -xLoc, 0); 
			}
			if (yLoc < 0)
			{
				imgOneYOffset = -yLoc;
				newShape = ShapeUtils.translate(newShape, 0, -yLoc); 
			}
			
			
			Graphics2D g2d = (Graphics2D)newImage.getGraphics();
			g2d.drawImage(this.image, imgOneXOffset, imgOneYOffset, null);
			
			for (int x = 0; x < (int)fullRect.getWidth(); x++)
			{
				for (int y = 0; y < (int)fullRect.getHeight(); y++)
				{
					if (newShape.contains(x, y))
					{
						newImage.setRGB(x, y, color.getRGB());
					}
				}
			}
			
			this.setImage(newImage);
		}
	}
	/**
	 * Returns the image data of this Image
	 * @return BufferedImage that contains the pixel data
	 */
	public BufferedImage getImage()
	{
		return this.image;
	}
	/** 
	 * Gets the Shape that this Image used to create its BufferedImage.
	 * @return Shape of the current image, not necessarily the original shape if the image has been rotated 
	 */
	public Shape getShape() {
		return shape;
	}
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	/**
	 * Render the Image using the internal coordinates of the Image. 
	 * Can also render elsewhere using getImage() if desired.
	 * @param g2d Graphics object to render with
	 * @param debug Can be used to test stuff, doesn't do anything at the moment
	 */
	public void render(Graphics2D g2d, boolean debug)
	{
		g2d.drawImage(this.image, (int)x, (int)y, this.image.getWidth(), this.image.getHeight(), null);
	}
	/** 
	 * Get the X coordinate of the center of this image
	 * @return x position of image + (width of image / 2)
	 */
	public double getCenterX()
	{
		return x + (this.width / 2);
	}
	/**
	 * Get the Y coordinate of the center of this image
	 * @return y position of image + (height of image / 2)
	 */
	public double getCenterY()
	{
		return y + (this.height / 2);
	}
	public double getX() {
		return x;
	}
	public void setX(double x)
	{
		this.x = x;
	}
	public void setCenterX(double x) {
		this.x = x - (this.width / 2);
	}
	public double getY() {
		return y;
	}
	public void setY(double y)
	{
		this.y = y;
	}
	public void setCenterY(double y) {
		this.y = y - (this.height / 2);
	}
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	public void setImage(BufferedImage image)
	{
		this.image = image;
	}

	public static int getImageCount() {
		return imageCount;
	}
}
