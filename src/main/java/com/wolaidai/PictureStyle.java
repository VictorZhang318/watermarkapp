package com.wolaidai;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class PictureStyle
{
	
	private Map<String, String> m_styleRules = new HashMap<String, String>();
	
	public void addStyle(String styleName, String styleRule)
	{
		m_styleRules.put(styleName, styleRule);
	}
	
	public Set<Entry<String, String>> getStyles()
	{
		return m_styleRules.entrySet();
	}
}
