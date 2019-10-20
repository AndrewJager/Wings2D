package framework;

import java.util.HashMap;
import java.util.Map;

/**
 * Map of keys, used to make remappable key controls
 */
public class KeyMapping {
	private Map<String, Integer> keys;
	
	public KeyMapping()
	{
		keys = new HashMap<String, Integer>();
	}
	/**
	 * Get the keyCode mapped to the string
	 * @param k Name of key to get key code for
	 * @return Key code linked to the inputted string
	 */
	public int getKey(String k)
	{
		return keys.get(k);
	}
	/**
	 * Link a string to a key code
	 * @param k Name to refer to key by
	 * @param keyCode KeyCode of key
	 */
	public void setKey(String k, int keyCode)
	{
		keys.put(k, keyCode);
	}
}
