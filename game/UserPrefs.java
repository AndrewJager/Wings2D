package game;

import java.io.Serializable;

public class UserPrefs implements Serializable{
	private static final long serialVersionUID = 1L;

	private boolean fullscreen;
	private boolean maximized;
	private int screenWidth;
	private int screenHeight;
	
	/**
	 * Default constructor. Sets all settings to best guess values.
	 */
	public UserPrefs()
	{
		fullscreen = false;
		screenWidth = 900;
		screenHeight = 700;
		System.out.println("Hi");
	}
	
	public boolean getFullscreen() {
		return fullscreen;
	}
	public void setFullscreen(boolean isFullscreen) {
		this.fullscreen = isFullscreen;
	}
	public int getScreenWidth() {
		return screenWidth;
	}
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}
	public int getScreenHeight() {
		return screenHeight;
	}
	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
	public boolean getMaximized() {
		return maximized;
	}
	public void setMaximized(boolean maximized) {
		this.maximized = maximized;
	}
	
	
}
