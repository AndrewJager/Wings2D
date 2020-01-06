package game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SettingsScreen {
	protected static boolean initReady = false;
	
	public static void runSettingsScreen(UserPrefs preferences)
	{
		JFrame frame = new JFrame("test");
		frame.setPreferredSize(new Dimension(preferences.getScreenWidth(), preferences.getScreenHeight()));
		frame.setMinimumSize(new Dimension(preferences.getScreenWidth(), preferences.getScreenHeight()));
		frame.setResizable(true);
		if (preferences.getFullscreen())
		{
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		}
		else if (preferences.getMaximized())
		{
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		

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
		isFullscreen.setSelected(preferences.getFullscreen());
		isFullscreen.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
            	if (e.getStateChange() == ItemEvent.SELECTED)
            	{
            		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
            	}
            	else
            	{
//            		frame.setExtendedState(JFrame.NORMAL); 
            	}
            }
        });
		
		JLabel sizingText = new JLabel("Change the width of the screen to the desired size.");
		sizingText.setHorizontalAlignment(JLabel.CENTER);
		sizingText.setBounds(25,  70,  400, 25);
		JLabel sizingText2 = new JLabel("You will not be able to change it while the game is running.");
		sizingText2.setHorizontalAlignment(JLabel.CENTER);
		sizingText2.setBounds(25,  100, 400, 25);
		
		frame.addComponentListener(new ComponentAdapter() 
		{  
            @Override
            public void componentResized(ComponentEvent e) {
	            panel.setBounds((int)(frame.getWidth() * 0.05), (int)(frame.getHeight() * 0.02), (int)(frame.getWidth() * 0.9), (int)(frame.getHeight() * 0.9));
	            isFullscreen.setLocation((panel.getWidth() / 2) - (isFullscreen.getWidth() / 2), isFullscreen.getY());
	            sizingText.setLocation((panel.getWidth() / 2) - (sizingText.getWidth() / 2), sizingText.getY());
	            sizingText2.setLocation((panel.getWidth() / 2) - (sizingText2.getWidth() / 2), sizingText2.getY());
	            readyButton.setLocation((panel.getWidth() / 2) - (readyButton.getWidth() / 2), (int)(panel.getHeight() * 0.90));
        		frame.setPreferredSize(new Dimension((int)frame.getWidth(), (int)((int)frame.getWidth() * 0.5625)));
        		frame.setMinimumSize(new Dimension((int)frame.getWidth(), (int)((int)frame.getWidth() * 0.5625)));
        		frame.pack();
                super.componentResized(e);
            }
		});
		frame.getContentPane().setLayout(null);
		panel.setLayout(null);
		frame.add(panel);
		panel.add(readyButton);
		panel.add(isFullscreen);
		panel.add(sizingText);
		panel.add(sizingText2);
		
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
		System.out.println(frame.getExtendedState());
		preferences.setFullscreen(isFullscreen.isSelected());
		preferences.setMaximized(frame.getExtendedState() == JFrame.MAXIMIZED_BOTH); 
		preferences.setScreenWidth(frame.getWidth());
		preferences.setScreenHeight((int)(preferences.getScreenWidth() * 0.5625));
		
		if (preferences.getFullscreen() || preferences.getMaximized())
		{
			preferences.setScreenWidth((int)GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth());
			preferences.setScreenHeight((int)GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight());
		}
		GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		try
		{
		    FileOutputStream fileOutputStream
		      = new FileOutputStream("preferences.txt");
		    ObjectOutputStream objectOutputStream 
		      = new ObjectOutputStream(fileOutputStream);
		    objectOutputStream.writeObject(preferences);
		    objectOutputStream.flush();
		    objectOutputStream.close();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		
		frame.setVisible(false);
		frame.dispose();
	}
}
