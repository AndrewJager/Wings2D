package com.wings2d.framework.svg.util;

import java.awt.geom.AffineTransform;

import org.w3c.dom.Node;

/**
 * Based off https://developer.mozilla.org/en-US/docs/Web/SVG/Attribute/transform
 */
public class SVGTransform {
	public static final String MATRIX = "matrix";
	public static final String TRANSLATE = "translate";
	public static final String SCALE = "scale";
	public static final String ROTATE = "rotate";
	public static final String SKEW_X = "skewX";
	public static final String SKEW_Y = "skewY";

	public static AffineTransform parseTransform(final Node transform) {
		AffineTransform t = new AffineTransform();
		
		if (transform != null) {
			String transformStr = transform.getNodeValue();
			String transformType = transformStr.substring(0, transformStr.indexOf("("));
			String transformValue = transformStr.substring(transformStr.indexOf("(")+1, transformStr.indexOf(")"));
			switch(transformType) {
				case (MATRIX) -> {
					String[] matrix = transformValue.split("[ ,]+"); 
					double m00 = Double.parseDouble(matrix[0]);
					double m10 = Double.parseDouble(matrix[1]);
					double m01 = Double.parseDouble(matrix[2]);
					double m11 = Double.parseDouble(matrix[3]);
					double m02 = Double.parseDouble(matrix[4]);
					double m12 = Double.parseDouble(matrix[5]);
					
					t.setTransform(m00, m10, m01, m11, m02, m12);
				}
				case (TRANSLATE) -> {
					String[] translate = transformValue.split("[ ,]+");
					double xTrans = Double.parseDouble(translate[0]);
					double yTrans = translate.length > 1
							? Double.parseDouble(translate[1])
							: 0.0f;
					t.translate(xTrans, yTrans);
				}
				case (SCALE) -> {
					String[] scale = transformValue.split("[ ,]+");
					double xScale = Double.parseDouble(scale[0]);
					double yScale = scale.length > 1
							? Double.parseDouble(scale[1])
							: xScale;
					t.scale(xScale, yScale);
				}
				case (ROTATE) -> {
					String[] rotate = transformValue.split("[ ,]+");
					double radians = Math.toRadians(Double.parseDouble(rotate[0]));
					if (rotate.length > 1) {
						double xAnchor = Double.parseDouble(rotate[1]);
						double yAnchor = Double.parseDouble(rotate[2]);
						t.rotate(radians, xAnchor, yAnchor);
					}
					else {
						t.rotate(radians);
					}
				}
				case (SKEW_X) -> {
					double skewX = Math.toRadians(Double.parseDouble(transformValue));
					t.setToShear(skewX, 0);
				}
				case (SKEW_Y) -> {
					double skewY = Math.toRadians(Double.parseDouble(transformValue));
					t.setToShear(0, skewY);
				}
			}
		}
		
		return t;
	}
}
