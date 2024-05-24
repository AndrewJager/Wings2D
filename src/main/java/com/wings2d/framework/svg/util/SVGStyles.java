package com.wings2d.framework.svg.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import com.wings2d.framework.svg.SVGShape;

@SuppressWarnings("serial")
public class SVGStyles extends HashMap<String, Object>{
	public static final String FILL = "fill";
	public static final String STROKE = "stroke";
	public static final String STROKE_WIDTH = "stroke-width";
	public static final String STROKE_LINECAP = "stroke-linecap";
	public static final String STROKE_LINEJOIN = "stroke-linejoin";
	
	public static SVGStyles parseStyles(final String stylesStr, final Map<String, Object> optionalData) {
		SVGStyles styles = new SVGStyles();
		
		String[] stylesSplit = stylesStr.split(";");
		for (int i = 0; i < stylesSplit.length; i++) {
			String [] styleParts = stylesSplit[i].split(":");
			String styleName = styleParts[0];
			String styleValue = styleParts[1];
			
			switch(styleName) {
				case (FILL) -> {
					if (!styleValue.equals("none")) {
						Color c = Color.decode(styleValue);
						styles.put(styleName, c);
					}
				}
				case (STROKE) -> {
					if (!styleValue.equals("none")) {
						Color c = Color.decode(styleValue);
						styles.put(styleName, c);
					}
				}
				case (STROKE_WIDTH) -> {
					String strWidth = styleValue; 
					if (styleValue.substring(styleValue.length() - 2).equals("px")) {
						System.out.println(STROKE_WIDTH + " value is in px. Ignoring units.");
						strWidth = styleValue.substring(0, styleValue.length() - 2);
					}
					Float width = Float.parseFloat(strWidth);
					styles.put(styleName, width);
				}
				case (STROKE_LINECAP) -> {
					styles.put(styleName, styleValue);
				}
				case (STROKE_LINEJOIN) -> {
					styles.put(styleName, styleValue);
				}
			}
		}
		
		float width = styles.containsKey(STROKE_WIDTH)
				? (Float)styles.get(STROKE_WIDTH)
				: 1.0f;
		int cap = BasicStroke.CAP_BUTT;
		if (styles.containsKey(STROKE_LINECAP)) {
			String capValue = (String)styles.get(STROKE_LINECAP);
			cap = switch(capValue) {
				case ("butt") -> {
					yield BasicStroke.CAP_BUTT;
				}
				case ("round") -> {
					yield BasicStroke.CAP_ROUND;
				}
				case ("square") -> {
					yield BasicStroke.CAP_SQUARE;
				}
				default -> {
					System.out.println("No result found for STYLE_STROKE_LINECAP value '" + capValue + "'");
					yield BasicStroke.CAP_BUTT;
				}
			};
		}
		int join = BasicStroke.JOIN_MITER;
		if (styles.containsKey(STROKE_LINEJOIN)) {
			String joinValue = (String)styles.get(STROKE_LINEJOIN);
			join = switch(joinValue) {
				case ("miter") -> {
					yield BasicStroke.JOIN_MITER;
				}
				case ("round") -> {
					yield BasicStroke.JOIN_ROUND;
				}
				case ("bevel") -> {
					yield BasicStroke.JOIN_BEVEL;
				}
				default -> {
					System.out.println("No result found for STYLE_STROKE_LINEJOIN value '" + joinValue + "'");
					yield BasicStroke.JOIN_MITER;
				}
			};
		}
		BasicStroke s = styles.containsKey(STROKE) 
				? new BasicStroke(
						width,
						cap,
						join
						)
				: null;
		if (s != null) {
			optionalData.put(SVGShape.OPTIONAL_STROKE, s);
		}
		
		
		return styles;
	}
}
