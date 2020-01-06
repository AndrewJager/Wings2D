package game;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window.Type;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CustomWindow extends JPanel{
	private static final long serialVersionUID = -6335376203853772958L;
	private JFrame frame;
	public CustomWindow(String title, Main game, UserPrefs preferences)
	{	
		frame = new JFrame(title); 
		frame.setPreferredSize(new Dimension(preferences.getScreenWidth(), preferences.getScreenHeight()));
		frame.setMaximumSize(new Dimension(preferences.getScreenWidth(), preferences.getScreenHeight()));
		frame.setMinimumSize(new Dimension(preferences.getScreenWidth(), preferences.getScreenHeight()));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.println("Test");
		if (preferences.getFullscreen())
		{
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
			frame.setUndecorated(true);
			System.out.println("Test 2");
		}
		else if (preferences.getMaximized())
		{
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();  
			frame.setMaximizedBounds(env.getMaximumWindowBounds());
			System.out.println("Test 3");
		}
		frame.setResizable(false);
		
		//This is done to prevent the window from being slightly off center to the right. I don't know why this has to be done.
		frame.setLocationRelativeTo(null);
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    frame.setLocation(x, frame.getY());
		frame.add(game);
        frame.setVisible(true);
		game.start();
	}
	
	public JFrame getFrame()
	{
		return frame;
	}
}
