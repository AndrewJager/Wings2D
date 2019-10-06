import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends Canvas{
	private static final long serialVersionUID = -6335376203853772958L;
	private JFrame frame;
	public Window(int width, int height, String title, Main game, ArrayList<Integer> keys)
	{
		frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.addKeyListener(new KeyListener() {
	        @Override
	        public void keyTyped(KeyEvent e) {
	        }

	        @Override
	        public void keyPressed(KeyEvent e) {
	            if (!game.getKeys().contains(e.getKeyCode()))
	            {
	            	game.getKeys().add(e.getKeyCode());
	            }
	            System.out.println(game.getKeys());
	        }

	        @Override
	        public void keyReleased(KeyEvent e) {
	        }
	    });
		frame.add(game);
        frame.setVisible(true);
		game.start();
	}
	
	public JFrame getFrame()
	{
		return frame;
	}
}
