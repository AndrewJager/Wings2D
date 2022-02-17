package com.wings2d.framework.misc;

import java.awt.event.KeyEvent;

public class KeyBind{	
	public static final String DELIMITER = ",";
	
	private int keyCode;
	
	public KeyBind(final int keyCode) {
		
		this.keyCode = keyCode;
	}
	
	public String getValue() {
		return KeyEvent.getKeyText(keyCode);
	}
	
	public int getKeyCode() {
		return keyCode;
	}
}
