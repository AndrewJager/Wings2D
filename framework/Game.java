package framework;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import framework.DrawPanel;

/**
 * Extend this class with your main game class, and override the
 * init/update/render methods. super.init() / super.update() / super.render()
 * should be called by your int/update/render functions.
 */
public abstract class Game extends Thread {
	/** Frame used to contain the canvas the game will be drawn on **/
	private JFrame frame;
	/** Canvas used to draw with **/
	private Canvas canvas;
	private Graphics2D graphics;
	private Graphics2D renderer;
	/** Used to stop program execution */
	private boolean isRunning = true;
	/** Used to run the init() function once */
	private boolean initalized = false;
	private DrawPanel draw;
	private BufferStrategy strat;
	private double lastFpsTime = 0;
	private int fps = 0;
	private boolean debug = false;

	private int width = 600;
	private int height = 400;

	/**
	 * Call super(debug) from your constructor to use this
	 * @param debug Use debug prints (FPS, ect.)
	 */
	public Game(boolean debug) {
		this.debug = debug;
		
		frame = new JFrame();
		frame.addWindowListener(new FrameClose());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setSize(width, height);
		frame.setBackground(Color.WHITE);

		JPanel panel = new JPanel();
		panel.setBackground(Color.YELLOW);
		panel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0f)));
		panel.setLayout(null);

		draw = new DrawPanel();
		panel.add(draw.getCanvas(), BorderLayout.CENTER);
		canvas = draw.getCanvas();
		frame.add(panel);
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				draw.resizePreview(frame);
			}
		});
		
		frame.setVisible(true);
		draw.getCanvas().createBufferStrategy(3);
		do {
			strat = draw.getCanvas().getBufferStrategy();
		} while (strat == null);
		start();
	}

	/**
	 * Used to handle closing events when the frame is closed
	 */
	private class FrameClose extends WindowAdapter {
		@Override
		/** Calls the onClose function */
		public void windowClosing(final WindowEvent e) {
			isRunning = false;
			onClose();
		}
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
	 * Get the drawing canvas
	 * @return The {@link java.awt.Canvas Canvas} used by the game to draw to
	 */
	public Canvas getCanvas()
	{
		return canvas;
	}

	/**
	 * Does nothing, but is called when the frame is closed. Override to do
	 * something on close
	 **/
	public void onClose() {
		System.out.println("Closed");
	}

	/**
	 * Get the drawing graphics, creating them if null;
	 * 
	 * @return Graphics2D object to draw with
	 */
	protected Graphics2D getDrawGraphics() {
		if (graphics == null) {
			try {
				graphics = (Graphics2D) strat.getDrawGraphics();
			} catch (IllegalStateException e) {
				return null;
			}
		}
		return graphics;
	}

	/**
	 * Handle some possible exceptions from drawing
	 * 
	 * @return True if any error found
	 */
	private boolean updateScreen() {
		graphics.dispose();
		graphics = null;
		try {
			strat.show();
			return (!strat.contentsLost());

		} catch (NullPointerException e) {
			return true;

		} catch (IllegalStateException e) {
			return true;
		}
	}

	public void run() {
		long lastLoopTime = System.nanoTime();
		final int TARGET_FPS = 60;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;   

		// keep looping round til the game ends
		while (isRunning)
		{
			if (!initalized)
			{
				init();
				initalized = true;
			}
			// work out how long its been since the last update, this
			// will be used to calculate how far the entities should
			// move this loop
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			double delta = updateLength / ((double)OPTIMAL_TIME);

			// update the frame counter
			lastFpsTime += updateLength;
			fps++;

			// update our FPS counter if a second has passed since
			// we last recorded
			if (lastFpsTime >= 1000000000)
			{
				if (debug)
				{
					System.out.println("(FPS: "+fps+")");
				}
				lastFpsTime = 0;
				fps = 0;
			}

			// update the game logic
			//	      doGameUpdates(delta);
			update(delta);

			// draw everything			
			renderer = getDrawGraphics();
			render(renderer);
			updateScreen();

			// we want each frame to take 10 milliseconds, to do this
			// we've recorded when we started the frame. We add 10 milliseconds
			// to this and then factor in the current time to give 
			// us our final value to wait for
			// remember this is in ms, whereas our lastLoopTime etc. vars are in ns.
			try {
				long time = (lastLoopTime-System.nanoTime() + OPTIMAL_TIME) / 1000000;
				if (time < 0)
				{
					time = 0;
				}
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * Called once on startup
	 */
	public void init() {

	}

	/**
	 * Use to update game logic
	 */
	public void update(double delta) {

	}

	/**
	 * Used to draw to screen
	 * 
	 * @param g2d Passed in by the base class. Don't pass in your own graphics
	 *            object.
	 */
	public void render(Graphics2D g2d) {
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect(0, 0, draw.getCanvas().getWidth(), draw.getCanvas().getHeight());
	}

}