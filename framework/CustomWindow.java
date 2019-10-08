package framework;
import java.awt.AWTEvent;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class CustomWindow extends JPanel{
	private static final long serialVersionUID = -6335376203853772958L;
	private JFrame frame;
	public CustomWindow(int width, int height, String title, Main game, ArrayList<Integer> keys)
	{
		frame = new JFrame(title);
		JButton b = new JButton("Hello");
		b.setBounds(50,60,95,30);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		//frame.add(b);
		frame.add(game);
        frame.setVisible(true);
		game.start();
	}
	
	public JFrame getFrame()
	{
		return frame;
	}
}
