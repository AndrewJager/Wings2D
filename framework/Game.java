package framework;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
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
 * Extend this class with your main game class, and override the init/update/render methods
 */
public abstract class Game extends Thread {
	private Graphics2D graphics;
    private boolean isRunning = true;
    private boolean shouldInit = true;
    private DrawPanel draw;
    private BufferStrategy strat;
    private Graphics2D renderer;
    private JFrame frame;
    private int width = 600;
    private int height = 400;

    public Game() {
        frame = new JFrame();
        frame.addWindowListener(new FrameClose());
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setSize(width, height);
        frame.setBackground(Color.WHITE);
        
        
        JPanel panel = new JPanel();
        panel.setBackground(Color.YELLOW);
        panel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0f)));
        
        draw = new DrawPanel();
        panel.add(draw.getCanvas(), BorderLayout.CENTER);
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

    private class FrameClose extends WindowAdapter {
        @Override
        public void windowClosing(final WindowEvent e) {
            isRunning = false;
        }
    }

    /**
     * Get the drawing graphics, creating them if null;
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
        long fpsWait = (long) (1.0 / 30 * 1000);
        main: while (isRunning) {
            long renderStart = System.nanoTime();
            if (shouldInit)
            {
            	init();
            	shouldInit = false;
            }
            update();

            do {
                renderer = getDrawGraphics();
                if (!isRunning) {
                    break main;
                }

                render(renderer);

                renderer.dispose();
            } while (!updateScreen());

            // FPS limiting
            long renderTime = (System.nanoTime() - renderStart) / 1000000;
            try {
                Thread.sleep(Math.max(0, fpsWait - renderTime));
            } catch (InterruptedException e) {
                Thread.interrupted();
                break;
            }
            renderTime = (System.nanoTime() - renderStart) / 1000000;

        }
        frame.dispose();
    }
    /** 
     * Called once on startup
     */
    public void init()
    {
    	
    }
    /**
     * Use to update game logic
     */
    public void update() {
    	
    }
    /**
     * Used to draw to screen
     * @param g2d Passed in by the base class. Don't pass in your own graphics object.
     */
    public void render(Graphics2D g2d) {
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, draw.getCanvas().getWidth(), draw.getCanvas().getHeight());
    }


}