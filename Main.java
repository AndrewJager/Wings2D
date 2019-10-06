import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	private JFrame frame;
	private Window win;
	private boolean mouseDown = false;
	
	private LevelManager manager;
	
	private TestLevel levelA;
	private Menu menu;
	
	private ArrayList<Integer> keys;
	
	private void init()
	{
		keys = new ArrayList<Integer>();
		
		manager = new LevelManager();
		
		menu = new Menu(GameLevels.MENU);
		manager.addLevel(menu);
		
		levelA = new TestLevel(GameLevels.TEST);
		manager.addLevel(levelA);	
	}
	private void update()
	{
		//System.out.println(this.frame.getInsets());
		manager.update(keys, this.frame, mouseDown);
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

		g.dispose();
		strat.show();
	}
	
	public Main()
	{
		this.win = new Window(WIDTH, HEIGHT, "title", this, keys);
		this.frame = win.getFrame();
		this.win.addMouseListener(new MouseAdapter(){
	          public void mousePressed(MouseEvent me) { 
	              System.out.println(me); 
	            } 
		});
	}
	
	public ArrayList<Integer> getKeys()
	{
		return keys;
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