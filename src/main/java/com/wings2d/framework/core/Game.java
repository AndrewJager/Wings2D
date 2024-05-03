package com.wings2d.framework.core;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * <p> Extend this class with your main game class, and override the
 * init/update/render methods. </p> 
 * <p> {@code super.update()} and {@code super.render()}
 * should be called by your init/update/render functions. </p>
 */
public abstract class Game{
	public class GameOptions {
		private int targetFPS;
		private boolean useVSync;
		
		private final String debugSeparator = " | ";
		
		public GameOptions(final int fps) {
			this.targetFPS = fps;
			useVSync = false;
		}
		public String getDebugPrint() {
			
			String print;
			print = "Target FPS: " + targetFPS + debugSeparator; 
			if (useVSync) {
				print += "VSync = true";
			}
			else {
				print += "VSync = false";
			}
			return print;
		}
		
		public int getTargetFPS() {
			return targetFPS;
		}
		public void setTargetFPS(final int fps) {
			targetFPS = fps;
		}
		public boolean getUseVSync() {
			return useVSync;
		}
		public void setUseVSync(final boolean vSync) {
			useVSync = vSync;
		}
	}
	
	public class DebugInfo {
		protected int updateFps, renderFps;
		private boolean printInfo;
		
		public DebugInfo()
		{
			updateFps = 0;
			renderFps = 0;
			setPrintInfo(false);
		}
		
		public int getUpdateFPS() {
			return updateFps;
		}
		public int getRenderFPS() {
			return renderFps;
		}
		public boolean shouldPrintInfo() {
			return printInfo;
		}
		public void setPrintInfo(boolean printInfo) {
			this.printInfo = printInfo;
		}
	}
	
	// Members that can be accessed with getter functions
	/** Game options */
	private GameOptions options;
	/** {@link javax.swing.JFrame JFrame} used to contain the canvas the game will be drawn on **/
	private JFrame frame;
	/** Handles the drawing canvas */
	private DrawPanelJPanel draw;
	/** Background {@link java.awt.Color Color} of the draw panel */
	private Color backgroundColor;
	/** Background {@link java.awt.Color Color} of the frame */
	private Color frameColor;
	/** Level manager for this game. Other LevelManagers should not be created */
	private SceneManager manager;
	/** Debug information */
	private DebugInfo debugInfo;
	
	
	// Internal variables
	/** Used to run the frames at fixed intervals */
	private ScheduledExecutorService runner, runner2;
	/** Used to run the init() function only once */
	private boolean initalized = false;
	/** {@link java.awt.Canvas Canvas} needs to be inside a panel for sizing to work properly. 
	 * This is not exposed to the user
	 */
	private JPanel panel;
	/** The width with which the game was created. Used to determine scale */
	private int ogWidth;
	
	
	/** Used to calculated the number of frames executed in a second */
	private double lastUpdateFpsTime = 0;
	private double lastRenderFpsTime = 0;

	/** The current fps */
	private int updateFramesInSecond = 0;
	private int renderFramesInSecond = 0;

	/** Time at which the previous frame rendered */
	private long lastUpdateLoopTime = 0;
	private long lastRenderLoopTime = 0;
	/** Time at which the current frame rendered */
	private long curUpdateLoopTime = 0;
	private long curRenderLoopTime = 0;
	private boolean renderInitalized = false;
	private double updateDeltaSum;
	private double renderDeltaSum;
	private Toolkit toolkit;
	
	/**
	 * Call super(debug) from your constructor to use this
	 * @param debug Use debug prints (FPS, ect.)
	 */
	public Game(final int width, final int height, final int fps) {
		options = new GameOptions(fps);
		debugInfo = new DebugInfo();
		manager = new SceneManager(this);
		toolkit = Toolkit.getDefaultToolkit();
		
		ogWidth = width;
		
		frameColor = Color.BLACK;
		draw = new DrawPanelJPanel(this);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				// Set JVM properties
				System.setProperty("sun.java2d.uiScale", "1.0");
//				System.setProperty("sun.java2d.opengl", "true");
				
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

				panel.add(draw);
				frame.add(panel);
				frame.addComponentListener(new ComponentAdapter() {
					public void componentResized(ComponentEvent e) {
						draw.resizePanel(panel);
						onResize(draw);
					}
				});
				
				setCanvasColor(Color.GRAY);
				
				frame.setVisible(true);
//				frame.createBufferStrategy(2);
//				draw.b = frame.getBufferStrategy();
				draw.initGraphics();
			}
		});
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				init();
				startLoop();
			}
		});
	}
	
	public Game(final int width, final int height) {
		this(width, height, 60);
	}
	
	private void startLoop() {
		final long OPTIMAL_TIME = 1000000000 / options.getTargetFPS();  
		runner = Executors.newSingleThreadScheduledExecutor();
		runner2 = Executors.newSingleThreadScheduledExecutor();

        runner.scheduleAtFixedRate(this::run, 0, OPTIMAL_TIME, TimeUnit.NANOSECONDS);   
        runner2.scheduleAtFixedRate(this::renderLoop, 0, OPTIMAL_TIME, TimeUnit.NANOSECONDS);   
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
			runner2.shutdown();
			System.exit(0);
		}
	}
	
	/**
	 * Called when the frame is resized. Override this to use this event.
	 * @param draw {@link com.wings2d.framework.rendering.DrawPanel DrawPanel} The game's window
	 */
	public void onResize(final DrawPanelJPanel draw)
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
		return draw.getGraphics2D();
	}

	/** "Game Loop" of the program. */
	private void run() {
		lastUpdateLoopTime = curUpdateLoopTime; // Must be before setting of lastLoopTime in init
		if (!initalized)
		{
			lastUpdateLoopTime = System.nanoTime();
			initalized = true;
		}
		
		// Calculate time delta
		long now = System.nanoTime();
		curUpdateLoopTime = now;
		long updateLength = (curUpdateLoopTime - lastUpdateLoopTime);
		double delta = updateLength / 1000000000.0;
		updateDeltaSum += delta;
		lastUpdateFpsTime += updateLength;
		updateFramesInSecond++;
	
		// Update debug info every second
		if (lastUpdateFpsTime >= 1000000000)
		{
			debugInfo.updateFps = updateFramesInSecond;
			if (debugInfo.shouldPrintInfo())
			{
				System.out.print("(FPS: " + updateFramesInSecond + ")" + " (DELTA: " + delta +")"); 
				System.out.printf(" (AVG DELTA: %f", (updateDeltaSum / updateFramesInSecond));
				System.out.print(")\n");
			}
			lastUpdateFpsTime = 0;
			updateFramesInSecond = 0;
			updateDeltaSum = 0;
		}
		try {
			update(delta);
//			draw.render();
//			draw.afterRender();
		}
		catch (Exception e) {
			// Unsure why I have to do this, but I had an exception that was crashing the thread, but not printing to the console.
			// This let's me debug that.
			e.printStackTrace();
		}
	}
	
	private void renderLoop() {
		lastRenderLoopTime = curRenderLoopTime; // Must be before setting of lastLoopTime in init
		if (!renderInitalized)
		{
			lastRenderLoopTime = System.nanoTime();
			renderInitalized = true;
		}
		
		// Calculate time delta
		long now = System.nanoTime();
		curRenderLoopTime = now;
		long renderLength = (curRenderLoopTime - lastRenderLoopTime);
		double delta = renderLength / 1000000000.0;
		renderDeltaSum += delta;
		lastRenderFpsTime += renderLength;
		renderFramesInSecond++;
	
		// Update debug info every second
		if (lastRenderFpsTime >= 1000000000)
		{
			debugInfo.renderFps = renderFramesInSecond;
			if (debugInfo.shouldPrintInfo())
			{
				System.out.print("(FPS: " + renderFramesInSecond + ")" + " (DELTA: " + delta +")"); 
				System.out.printf(" (AVG DELTA: %f", (renderDeltaSum / renderFramesInSecond));
				System.out.print(")\n");
			}
			lastRenderFpsTime = 0;
			renderFramesInSecond = 0;
			renderDeltaSum = 0;
		}
		try {
			draw.render();
			draw.afterRender();
		}
		catch (Exception e) {
			// Unsure why I have to do this, but I had an exception that was crashing the thread, but not printing to the console.
			// This let's me debug that.
			e.printStackTrace();
		}
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
		manager.update(delta);
	}

	/**
	 * Used to draw to screen
	 * 
	 * @param g2d Passed in by the base class. Don't pass in your own graphics
	 *            object.
	 */
	public void render(final Graphics2D g2d) {
		if (options.getUseVSync()) {
			toolkit.sync();
		}

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		

		
		g2d.setColor(backgroundColor);
		g2d.fillRect(0, 0, draw.getWidth(), draw.getHeight());
		

		
		manager.render(g2d, debugInfo.shouldPrintInfo());
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
//	public Color getCanvasColor() {
//		return backgroundColor;
//	}
	/**
	 * Set the background color of the canvas
	 * @param color {@link java.awt.Color Color} to set the canvas to
	 */
	public void setCanvasColor(Color color) {
		draw.setBackground(color);
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
	public void setFrameColor(final Color color) {
		this.frame.setBackground(color);
		this.panel.setBackground(color);
		this.frameColor = color;
	}
	
	public void setTitle(final String title) {
		this.frame.setTitle(title);
	}
	
	/**
	 * Get object to control the levels for the game
	 * @return {@link com.wings2d.framework.core.SceneManager LevelManager} for this game
	 */
	public SceneManager getManager()
	{
		return manager;
	}
	
	public DrawPanelJPanel getDrawPanel()
	{
		return draw;
	}
	public DebugInfo getDebugInfo() {
		return debugInfo;
	}
	public GameOptions getOptions() {
		return options;
	}
	public JPanel getFullPanel() {
		return panel;
	}
}