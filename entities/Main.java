package entities;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import framework.*;
import entities.*;


public class Main extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	private static int WIDTH = (int)(768 * 1.2);
	private static int HEIGHT = (int)(432 * 1.2);
	private double scale;
	
	private boolean debug = false;
	private Thread thread;
	private boolean running = false;
	private boolean mouseDown = false;
	
	private LevelManager manager;
	private KeyState keys;
	
	private TestLevel levelA;
	private Menu menu;
	
	private Point mouseClick;
	
	private void init()
	{
		keys = new KeyState();
		
		manager = new LevelManager();
		manager.setScale(scale);
		
		menu = new Menu(manager, GameLevels.MENU);
		levelA = new TestLevel(manager, GameLevels.TEST);
		
		manager.setLevel(GameLevels.TEST);
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
		
		Graphics g = strat.getDrawGraphics();
		Graphics2D g2d = (Graphics2D)g;
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		manager.render(g2d, debug);
		manager.renderUI(g2d, debug);

		g.dispose();
		strat.show();
	}
	
	public Main()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		scale = screenSize.getWidth() / WIDTH;
//		scale = 1.0;
		WIDTH = (int)(WIDTH * scale);
		HEIGHT = (int)(HEIGHT * scale);
		new CustomWindow(WIDTH, HEIGHT, "title", this);
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
//				System.out.println("FPS: " + frames);
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