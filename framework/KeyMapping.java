package framework;

import java.util.HashMap;
import java.util.Map;

public class KeyMapping {
	private Map<String, Integer> keys;
	
	public KeyMapping()
	{
		keys = new HashMap<String, Integer>();
	}
	public int getKey(String k)
	{
		return keys.get(k);
	}
	public void setKey(String k, int keyCode)
	{
		keys.put(k, keyCode);
	}
}
