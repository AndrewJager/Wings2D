package com.wings2d.framework;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.wings2d.framework.rendering.DrawPanel;
import com.wings2d.framework.rendering.DrawPanelCanvas;
import com.wings2d.framework.rendering.DrawPanelJPanel;

/**
 * Extend this class with your main game class, and override the
 * init/update/render methods. super.init() / super.update() / super.render()
 * should be called by your int/update/render functions.
 */
public abstract class Game{
	public class DebugInfo {
		protected int fps;
		private boolean printInfo;
		
		public DebugInfo()
		{
			fps = 0;
			setPrintInfo(false);
		}
		
		public int getFPS() {
			return fps;
		}
		public boolean shouldPrintInfo() {
			return printInfo;
		}
		public void setPrintInfo(boolean printInfo) {
			this.printInfo = printInfo;
		}
	}
	
	// Members that can be accessed with getter functions
	/** {@link javax.swing.JFrame JFrame} used to contain the canvas the game will be drawn on **/
	private JFrame frame;
	/** Handles the drawing canvas */
	private DrawPanel draw;
	/** Background {@link java.awt.Color Color} of the draw panel */
	private Color backgroundColor;
	/** Background {@link java.awt.Color Color} of the frame */
	private Color frameColor;
	/** Target Frames Per Second */
	private int targetFPS;
	/** Level manager for this game. Other LevelManagers should not be created */
	private LevelManager manager;
	/** Debug information */
	private DebugInfo debugInfo;
	
	
	// Internal variables
	/** Used to run the frames at fixed intervals */
	private ScheduledExecutorService runner;
	/** Used to run the init() function only once */
	private boolean initalized = false;
	/** {@link java.awt.Canvas Canvas} needs to be inside a panel for sizing to work properly. 
	 * This is not exposed to the user
	 */
	private JPanel panel;
	/** Used to calculated the number of frames executed in a second */
	private double lastFpsTime = 0;
	/** Time the previous frame took to render, used to calculate the frame delta */
	private long lastLoopTime;
	/** The current fps */
	private int fps = 0;
	/** The width with which the game was created. Used to determine scale */
	private int ogWidth;
	
	/**
	 * Call super(debug) from your constructor to use this
	 * @param debug Use debug prints (FPS, ect.)
	 */
	public Game(final int width, final int height, final int fps, final boolean useCanvas) {
		debugInfo = new DebugInfo();
		manager = new LevelManager();
		targetFPS = fps;
		
		ogWidth = width;
		
		backgroundColor = Color.DARK_GRAY;
		frameColor = Color.BLACK;
		
		frame = new JFrame();
		frame.addWindowListener(new FrameClose());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setMinimumSize(new Dimension(480, 270));
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setBackground(frameColor);

		panel = new JPanel();
		panel.setBackground(frameColor);
		panel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0f)));
		panel.setLayout(null);

		if (useCanvas) {
			draw = new DrawPanelCanvas(this);
		}
		else {
			draw = new DrawPanelJPanel(this);
		}
		panel.add(draw.getDrawComponent(), BorderLayout.CENTER);
		frame.add(panel);
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				draw.resizePanel(panel);
				onResize(draw);
			}
		});
		
		frame.setVisible(true);
		draw.initGraphics();
		
		
		final long OPTIMAL_TIME = 1000000000 / targetFPS;  
		runner = Executors.newSingleThreadScheduledExecutor();
        runner.scheduleAtFixedRate(this::run, 0, OPTIMAL_TIME, TimeUnit.NANOSECONDS);	
	}
	
	public Game(final int width, final int height) {
		this(width, height, 60, false);
	}
	public Game(final int width, final int height, final int fps) {
		this(width, height, fps, false);
	}

	/**
	 * Used to handle closing events when the frame is closed
	 */
	private class FrameClose extends WindowAdapter {
		@Override
		/** Calls the onClose function */
		public void windowClosing(final WindowEvent e) {
			onClose();
			runner.shutdown();
			System.exit(0);
		}
	}
	
	/**
	 * Called when the frame is resized. Override this to use this event.
	 * @param draw {@link com.wings2d.framework.rendering.DrawPanel DrawPanel} The game's window
	 */
	public void onResize(final DrawPanel draw)
	{
		double scale = Double.valueOf(draw.getWidth()) / ogWidth; 
		manager.setScale(scale);
	}

	/**
	 * Does nothing, but is called when the frame is closed. Override to do
	 * something when the window is closed.
	 **/
	public void onClose() {
		if (debugInfo.shouldPrintInfo())
		{
			System.out.println("Closed");
		}
	}

	/**
	 * Get the drawing graphics, creating them if null.
	 * @return Graphics2D object to draw with
	 */
	protected Graphics2D getDrawGraphics() {
		return draw.getGraphics();
	}

	/** "Game Loop" of the program. */
	private void run() {
		if (!initalized)
		{
			init();
			lastLoopTime = System.nanoTime();
			initalized = true;
		}
		// work out how long its been since the last update, this
		// will be used to calculate how far the entities should
		// move this loop
		long now = System.nanoTime();
		long updateLength = now - lastLoopTime;
		double delta = updateLength / 1000000000.0;
		lastLoopTime = now;
	
		// update the frame counter
		lastFpsTime += updateLength;
		fps++;
	
		// update our FPS counter if a second has passed since
		// we last recorded
		if (lastFpsTime >= 1000000000)
		{
			debugInfo.fps = fps;
			if (debugInfo.shouldPrintInfo())
			{
				System.out.println("(FPS: " + fps + ")" + " (DELTA: " + delta +")");
			}
			lastFpsTime = 0;
			fps = 0;
		}

		update(delta);
	
		draw.render();
		draw.afterRender();
	}

	/**
	 * Called once on startup
	 */
	public void init() {

	}

	/**
	 * Use to update game logic
	 * @param delta Time from last update. The sum of the deltas for one second should be 1.0. 
	 * Therefore, the delta for one frame at 60 FPS should be about 0.0166667 (1/60).
	 */
	public void update(final double delta) {

	}

	/**
	 * Used to draw to screen
	 * 
	 * @param g2d Passed in by the base class. Don't pass in your own graphics
	 *            object.
	 */
	public void render(final Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(backgroundColor);
		g2d.fillRect(0, 0, draw.getDrawComponent().getWidth(), draw.getDrawComponent().getHeight());
	}
	
	/**
	 * Get the main frame
	 * @return The {@link javax.swing.JFrame JFrame} used by this game
	 */
	public JFrame getFrame() 
	{
		return frame;
	}
	/**
	 * Get the background color of the canvas
	 * @return {@link java.awt.Color Color} of the canvas
	 */
	public Color getCanvasColor() {
		return backgroundColor;
	}
	/**
	 * Set the background color of the canvas
	 * @param color {@link java.awt.Color Color} to set the canvas to
	 */
	public void setCanvasColor(Color color) {
		draw.getDrawComponent().setBackground(color);
		this.backgroundColor = color;
	}
	/**
	 * Get the background color of the canvas
	 * @return {@link java.awt.Color Color} of the frame
	 */
	public Color getFrameColor() {
		return frameColor;
	}
	/**
	 * Set the background color of the canvas
	 * @param color {@link java.awt.Color Color} to set the frame to
	 */
	public void setFrameColor(Color color) {
		this.frame.setBackground(color);
		this.panel.setBackground(color);
		this.frameColor = color;
	}
	/**
	 * Get object to control the levels for the game
	 * @return {@link com.wings2d.framework.LevelManager LevelManager} for this game
	 */
	public LevelManager getManager()
	{
		return manager;
	}
	
	public DrawPanel getDrawPanel()
	{
		return draw;
	}
	public DebugInfo getDebugInfo() {
		return debugInfo;
	}
	public int getTargetFPS() {
		return targetFPS;
	}
}