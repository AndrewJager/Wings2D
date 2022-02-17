package com.wings2d.framework.misc;

import java.util.ArrayList;
import java.util.List;

public class ActionBinding {
	private List<KeyBind> bindings;
	
	public ActionBinding() {
		bindings = new ArrayList<KeyBind>();
	}
	public ActionBinding(final String keys) {
		this();
		String[] keyStr = keys.split(KeyBind.DELIMITER);
		for (int i = 0; i < keyStr.length; i++) {
			bindings.add(new KeyBind(Integer.parseInt(keyStr[i].trim())));
		}
	}
	
	public void addKey(final KeyBind key) {
		bindings.add(key);
	}
	
	public String getValue() {
		String str = "";
		for (int i = 0; i < bindings.size(); i++) {
			if (!str.equals("")) {
				str = str + " + ";
			}
			str = str + bindings.get(i).getValue();
		}
		return str;
	}
	
	public String getSaveStr() {
		String str = "";
		for (int i = 0; i < bindings.size(); i++) {
			if (!str.equals("")) {
				str = str + ",";
			}
			str = str + bindings.get(i).getKeyCode();
		}
		return str;
	}
	
	public List<KeyBind> getKeys() {
		return bindings;
	}
}
