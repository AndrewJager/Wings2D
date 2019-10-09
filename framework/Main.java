package framework;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

import framework.*;
import entities.*;


public class Main extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 500;
	
	private boolean debug = false;
	private Thread thread;
	private boolean running = false;
	private CustomWindow win;
	private JFrame frame;
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
		
		menu = new Menu(GameLevels.MENU, this.frame);
		manager.addLevel(menu);
		
		levelA = new TestLevel(GameLevels.TEST, this.frame);
		manager.addLevel(levelA);	
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
		this.win = new CustomWindow(WIDTH, HEIGHT, "title", this);
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
				//System.out.println("FPS: " + frames);
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