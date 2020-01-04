package framework;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.Main;

public class CustomWindow extends JPanel{
	private static final long serialVersionUID = -6335376203853772958L;
	private JFrame frame;
	public CustomWindow(int width, int height, String title, Main game)
	{	
		frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(game);
        frame.setVisible(true);
		game.start();
	}
	
	public JFrame getFrame()
	{
		return frame;
	}
}
