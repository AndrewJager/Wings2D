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
		canvas = gc.createCompatibleVolatileImage(WIDTH, HEIGHT);
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

		canvasGraphics = canvas.createGraphics();
		canvasGraphics.setColor(Color.DARK_GRAY);
		canvasGraphics.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		manager.render(canvasGraphics, debug);
		manager.renderUI(canvasGraphics, debug);

		g2d.drawImage(canvas, 0, 0, null);
		
		g2d.dispose();
		g.dispose();
		strat.show();
	}
	
	private void setupGrapics() 
	{
		JFrame frame = new JFrame("test");
		int localWidth = 800;
		int localHeight = 700;
		frame.setPreferredSize(new Dimension(localWidth, localHeight));
		frame.setMaximumSize(new Dimension(localWidth + 50, localHeight + 50));
		frame.setMinimumSize(new Dimension(localWidth, localHeight));
		
		JPanel panel = new JPanel();
		panel.setBounds((int)(frame.getWidth() * 0.05), (int)(frame.getHeight() * 0.02), (int)(frame.getWidth() * 0.9), (int)(frame.getHeight() * 0.9));
		panel.setBackground(Color.LIGHT_GRAY);

		JButton readyButton = new JButton("Begin!");
		readyButton.setLocation(new Point(150, (int)(panel.getHeight() * 0.90)));
		readyButton.setSize(80, 25);
	    readyButton.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){  
	    	            initReady = true;
	    	        }  
	    	    });  
		JCheckBox isFullscreen = new JCheckBox("Fullscreen");
		isFullscreen.setBounds(25, 30, 100, 25);
		JLabel sizingText = new JLabel("Change the size of the screen to the desired size.");
		sizingText.setHorizontalAlignment(JLabel.CENTER);
		sizingText.setBounds(25,  70,  400, 25);
		JLabel sizingText2 = new JLabel("You will not be able to change it while the game is running.");
		sizingText2.setHorizontalAlignment(JLabel.CENTER);
		sizingText2.setBounds(25,  100, 400, 25);
		JTextField inputWidth = new JTextField();
		inputWidth.setBounds(25, 130, 50, 25);
		JTextField inputHeight = new JTextField();
		inputHeight.setBounds(75, 160, 50, 25);
		
		frame.addComponentListener(new ComponentAdapter() 
		{  
		        public void componentResized(ComponentEvent evt) {
		            Component c = (Component)evt.getSource();
		            inputWidth.setText(Integer.toString(c.getWidth()));
		            inputHeight.setText(Integer.toString(c.getHeight()));
		            
		            panel.setBounds((int)(frame.getWidth() * 0.05), (int)(frame.getHeight() * 0.02), (int)(frame.getWidth() * 0.9), (int)(frame.getHeight() * 0.9));
		            isFullscreen.setLocation((panel.getWidth() / 2) - (isFullscreen.getWidth() / 2), isFullscreen.getY());
		            sizingText.setLocation((panel.getWidth() / 2) - (sizingText.getWidth() / 2), sizingText.getY());
		            sizingText2.setLocation((panel.getWidth() / 2) - (sizingText2.getWidth() / 2), sizingText2.getY());
		            inputWidth.setLocation((panel.getWidth() / 2) - (inputWidth.getWidth() / 2), inputWidth.getY());
		            inputHeight.setLocation((panel.getWidth() / 2) - (inputHeight.getWidth() / 2), inputHeight.getY());
		            readyButton.setLocation((panel.getWidth() / 2) - (readyButton.getWidth() / 2), (int)(panel.getHeight() * 0.90));
		        }
		});
		frame.getContentPane().setLayout(null);
		panel.setLayout(null);
		frame.add(panel);
		panel.add(readyButton);
		panel.add(isFullscreen);
		panel.add(sizingText);
		panel.add(sizingText2);
		panel.add(inputWidth);
		panel.add(inputHeight);
		
		frame.setVisible(true);
		
		
		while (!initReady)
		{
			try //Don't run as fast as possible
			{
			    Thread.sleep(50);
			}
			catch(InterruptedException ex)
			{
			    Thread.currentThread().interrupt();
			}
		}
		
		frame.setVisible(false);
		frame.dispose();
	}
	
	public Main()
	{
		setupGrapics();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		scale = screenSize.getWidth() / WIDTH;
		scale = 1.0;
		WIDTH = (int)(WIDTH * scale);
		HEIGHT = (int)(HEIGHT * scale);
		
		CustomWindow win = new CustomWindow(WIDTH, HEIGHT, "title", this);
		System.out.println(win.getFrame().getHeight());
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