package game;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import framework.*;


public class Main extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	private static int WIDTH = (int)(768 * 1.2);
	private static int HEIGHT = (int)(432 * 1.2);
	private double scale;
	
	private UserPrefs preferences; 
	
	private boolean debug = false;
	private Thread thread;
	private boolean running = false;
	private boolean mouseDown = false;
	
	private boolean initReady = false;
	
	private LevelManager manager;
	private KeyState keys;
	
	private TestLevel levelA;
	private Menu menu;
	
	private Point mouseClick;
	
	private VolatileImage canvas;
	private Graphics g;
	/** Used only to draw the canvas **/
	private Graphics2D g2d;
	/** Used to draw to the canvas **/
	private Graphics2D canvasGraphics;
	
	private GraphicsConfiguration gc;
	
	
	private void init()
	{
		keys = new KeyState();
		
		manager = new LevelManager();
		manager.setScale(scale);
		
		menu = new Menu(manager, GameLevels.MENU);
		levelA = new TestLevel(manager, GameLevels.TEST);
		
		manager.setLevel(GameLevels.TEST);
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getScreenDevices()[0];
		gc = gd.getConfigurations()[0];
		canvas = gc.createCompatibleVolatileImage(preferences.getScreenWidth(), preferences.getScreenHeight());
	}
	private void update()
	{
		manager.update(keys, mouseDown);
		manager.updateUI(mouseClick);
		mouseClick = null;
	}
	private void render()
	{
		BufferStrategy strat = this.getBufferStrategy();
		if (strat == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		g = strat.getDrawGraphics();
		g2d = (Graphics2D)g;
//		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		canvasGraphics = canvas.createGraphics();
		canvasGraphics.setColor(Color.DARK_GRAY);
		canvasGraphics.fillRect(0, 0, preferences.getScreenWidth(), preferences.getScreenHeight());

		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, preferences.getScreenWidth(), preferences.getScreenHeight());
		
		manager.render(canvasGraphics, debug);
		manager.renderUI(canvasGraphics, debug);

		g2d.drawImage(canvas, 0, 0, null);
		
		g2d.dispose();
		g.dispose();
		strat.show();
	}
	
	public Main()
	{
		File prefFile = new File("preferences.txt");
		if (prefFile.exists())
		{
			try {
				FileInputStream fileInStream = new FileInputStream("preferences.txt");
				ObjectInputStream prefStream = new ObjectInputStream(fileInStream);
		        preferences = (UserPrefs) prefStream.readObject();
		        prefStream.close();
		        prefStream.close();
			} 
			catch (ClassNotFoundException | IOException e1) {
				System.out.println("What should never have happened, happened");
			}
		}
		else
		{
			preferences = new UserPrefs();
		}
		
		SettingsScreen.runSettingsScreen(preferences);
		scale = Double.valueOf(preferences.getScreenWidth()) / WIDTH; // Need to cast to double here, or it will round
		
		CustomWindow win = new CustomWindow("title", this, preferences);
		this.addKeyListener(new KeyListener() {
	        @Override
	        public void keyTyped(KeyEvent e) {
	        }

	        @Override
	        public void keyPressed(KeyEvent e) {
	        	int k = e.getKeyCode();
	        	if (k == manager.getKeyMapping().getKey("Right"))
	        	{
	        		keys.right_key = true;
	        	}
	        	else if(k == manager.getKeyMapping().getKey("Left"))
	        	{
	        		keys.left_key = true;
	        	}
	        	else if(k == manager.getKeyMapping().getKey("Enter"))
	        	{
	        		keys.left_key = true;
	        	}
	        	else if(k == manager.getKeyMapping().getKey("Esc"))
	        	{
	        		keys.esc_key = true;
	        	}
	        	else if(k == manager.getKeyMapping().getKey("Jump"))
	        	{
	        		keys.jump_key = true;
	        	}
	        }

	        @Override
	        public void keyReleased(KeyEvent e) {
	        	int k = e.getKeyCode();
	        	if (k == manager.getKeyMapping().getKey("Right"))
	        	{
	        		keys.right_key = false;
	        	}
	        	else if(k == manager.getKeyMapping().getKey("Left"))
	        	{
	        		keys.left_key = false;
	        	}
	        	else if(k == manager.getKeyMapping().getKey("Enter"))
	        	{
	        		keys.left_key = false;
	        	}
	        	else if(k == manager.getKeyMapping().getKey("Esc"))
	        	{
	        		keys.esc_key = false;
	        	}
	        	else if(k == manager.getKeyMapping().getKey("Jump"))
	        	{
	        		keys.jump_key = false;
	        	}
	        }
	    });
		this.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		    	mouseClick = e.getPoint();
		    }
		});
	}
	public synchronized void start()
	{
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	public synchronized void stop()
	{
		try {
			thread.join();
			running = false;
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000/amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		init();
		while (running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1)
			{
				update();
				delta--;
			}
			if (running)
			{
				render();
			}
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	public static void main(String args[])
	{
		new Main();
	}
}