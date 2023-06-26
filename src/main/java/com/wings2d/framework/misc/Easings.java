package com.wings2d.framework.misc;


/**
 * Ported from <a href="https://easings.net/">https://easings.net/</a>
 */
public class Easings {
	private static final double c1 = 1.70158;
	private static final double c2 = c1 * 1.525;
	private static final double c3 = c1 + 1;
	private static final double c4 = (2 * Math.PI) / 3;
	private static final double c5 = (2 * Math.PI) / 4.5;

	/**
	 * <a href="https://easings.net/#easeInSine">https://easings.net/#easeInSine</a>
	 */
	public static double easeInSine(final double x) {
		return 1 - Math.cos((x * Math.PI) / 2);
	}
	
	/**
	 * <a href="https://easings.net/#easeOutSine">https://easings.net/#easeOutSine</a>
	 */
	public static double easeOutSine(final double x) {
		return Math.sin((x * Math.PI) / 2);
	}
	
	/**
	 * <a href="https://easings.net/#easeInOutSine">https://easings.net/#easeInOutSine</a>
	 */
	public static double easeInOutSine(final double x) {
		return -(Math.cos(Math.PI * x) - 1) / 2;
	}
	
	/**
	 * <a href="https://easings.net/#easeInQuad">https://easings.net/#easeInQuad</a>
	 */
	public static double easeInQuad(final double x) {
		return x * x;
	}

	/**
	 * <a href="https://easings.net/#easeOutQuad">https://easings.net/#easeOutQuad</a>
	 */
	public static double easeOutQuad(final double x) {
		return 1 - (1 - x) * (1 - x);
	}

	/**
	 * <a href="https://easings.net/#easeInOutQuad">https://easings.net/#easeInOutQuad</a>
	 */
	public static double easeInOutQuad(final double x) {
		return x < 0.5 ? 2 * x * x : 1 - Math.pow(-2 * x + 2, 2) / 2;
	}

	/**
	 * <a href="https://easings.net/#easeInCubic">https://easings.net/#easeInCubic</a>
	 */
	public static double easeInCubic(final double x) {
		return x * x * x;
	}
	
	/**
	 * <a href="https://easings.net/#easeOutCubic">https://easings.net/#easeOutCubic</a>
	 */
	public static double easeOutCubic(final double x) {
		return 1 - Math.pow(1 - x, 3);
	}
	
	/**
	 * <a href="https://easings.net/#easeInOutCubic">https://easings.net/#easeInOutCubic</a>
	 */
	public static double easeInOutCubic(final double x) {
		return x < 0.5 ? 4 * x * x * x : 1 - Math.pow(-2 * x + 2, 3) / 2;
	}
	
	/**
	 * <a href="https://easings.net/#easeInQuart">https://easings.net/#easeInQuart</a>
	 */
	public static double easeInQuart(final double x) {
		return x * x * x * x;
	}

	/**
	 * <a href="https://easings.net/#easeOutQuart">https://easings.net/#easeOutQuart</a>
	 */
	public static double easeOutQuart(final double x) {
		return 1 - Math.pow(1 - x, 4);
	}

	/**
	 * <a href="https://easings.net/#easeInOutQuart">https://easings.net/#easeInOutQuart</a>
	 */
	public static double easeInOutQuart(final double x) {
		return x < 0.5 ? 8 * x * x * x * x : 1 - Math.pow(-2 * x + 2, 4) / 2;
	}

	/**
	 * <a href="https://easings.net/#easeInQuint">https://easings.net/#easeInQuint</a>
	 */
	public static double easeInQuint(final double x) {
		return x * x * x * x * x;
	}
	
	/**
	 * <a href="https://easings.net/#easeOutQuint">https://easings.net/#easeOutQuint</a>
	 */
	public static double easeOutQuint(final double x) {
		return 1 - Math.pow(1 - x, 5);
	}
	
	/**
	 * <a href="https://easings.net/#easeInOutQuint">https://easings.net/#easeInOutQuint</a>
	 */
	public static double easeInOutQuint(final double x) {
		return x < 0.5 ? 16 * x * x * x * x * x : 1 - Math.pow(-2 * x + 2, 5) / 2;
	}
	
	/**
	 * <a href="https://easings.net/#easeInExpo">https://easings.net/#easeInExpo</a>
	 */
	public static double easeInExpo(final double x) {
		return x == 0 ? 0 : Math.pow(2, 10 * x - 10);
	}

	/**
	 * <a href="https://easings.net/#easeOutExpo">https://easings.net/#easeOutExpo</a>
	 */
	public static double easeOutExpo(final double x) {
		return x == 1 ? 1 : 1 - Math.pow(2, -10 * x);
	}

	/**
	 * <a href="https://easings.net/#easeInOutExpo">https://easings.net/#easeInOutExpo</a>
	 */
	public static double easeInOutExpo(final double x) {
		return x == 0
				  ? 0
				  : x == 1
				  ? 1
				  : x < 0.5 ? Math.pow(2, 20 * x - 10) / 2
				  : (2 - Math.pow(2, -20 * x + 10)) / 2;
	}

	/**
	 * <a href="https://easings.net/#easeInCirc">https://easings.net/#easeInCirc</a>
	 */
	public static double easeInCirc(final double x) {
		return 1 - Math.sqrt(1 - Math.pow(x, 2));
	}
	
	/**
	 * <a href="https://easings.net/#easeOutCirc">https://easings.net/#easeOutCirc</a>
	 */
	public static double easeOutCirc(final double x) {
		return Math.sqrt(1 - Math.pow(x - 1, 2));
	}
	
	/**
	 * <a href="https://easings.net/#easeInOutCirc">https://easings.net/#easeInOutCirc</a>
	 */
	public static double easeInOutCirc(final double x) {
		return x < 0.5
				? (1 - Math.sqrt(1 - Math.pow(2 * x, 2))) / 2
				: (Math.sqrt(1 - Math.pow(-2 * x + 2, 2)) + 1) / 2;
	}
	
	/**
	 * <a href="https://easings.net/#easeInBack">https://easings.net/#easeInBack</a>
	 */
	public static double easeInBack(final double x) {
		return c3 * x * x * x - c1 * x * x;
	}

	/**
	 * 
	 * <a href="https://easings.net/#easeOutBack">https://easings.net/#easeOutBack</a>
	 */
	public static double easeOutBack(final double x) {
		return 1 + c3 * Math.pow(x - 1, 3) + c1 * Math.pow(x - 1, 2);
	}

	/**
	 * <a href="https://easings.net/#easeInOutBack">https://easings.net/#easeInOutBack</a>
	 */
	public static double easeInOutBack(final double x) {
		return x < 0.5
		  ? (Math.pow(2 * x, 2) * ((c2 + 1) * 2 * x - c2)) / 2
		  : (Math.pow(2 * x - 2, 2) * ((c2 + 1) * (x * 2 - 2) + c2) + 2) / 2;
	}

	/**
	 * <a href="https://easings.net/#easeInElastic">https://easings.net/#easeInElastic</a>
	 */
	public static double easeInElastic(final double x) {
		return x == 0
		  ? 0
		  : x == 1
		  ? 1
		  : -Math.pow(2, 10 * x - 10) * Math.sin((x * 10 - 10.75) * c4);
	}
	
	/**
	 * <a href="https://easings.net/#easeOutElastic">https://easings.net/#easeOutElastic</a>
	 */
	public static double easeOutElastic(final double x) {
		return x == 0
				  ? 0
				  : x == 1
				  ? 1
				  : Math.pow(2, -10 * x) * Math.sin((x * 10 - 0.75) * c4) + 1;
	}
	
	/**
	 * <a href="https://easings.net/#easeInOutElastic">https://easings.net/#easeInOutElastic</a>
	 */
	public static double easeInOutElastic(final double x) {
		return x == 0
		  ? 0
		  : x == 1
		  ? 1
		  : x < 0.5
		  ? -(Math.pow(2, 20 * x - 10) * Math.sin((20 * x - 11.125) * c5)) / 2
		  : (Math.pow(2, -20 * x + 10) * Math.sin((20 * x - 11.125) * c5)) / 2 + 1;
	}
	
	/**
	 * <a href="https://easings.net/#easeInBounce">https://easings.net/#easeInBounce</a>
	 */
	public static double easeInBounce(final double x) {
		return 1 - Easings.easeOutBounce(1 - x);
	}
	
	/**
	 * <a href="https://easings.net/#easeOutBounce">https://easings.net/#easeOutBounce</a>
	 */
	public static double easeOutBounce(final double x) {
		final double n1 = 7.5625;
		final double d1 = 2.75;

		double y = x;
		
		if (x < 1 / d1) {
		    return n1 * x * x;
		} 
		else if (x < 2 / d1) {
		    return n1 * (y -= 1.5 / d1) * x + 0.75;
		} 
		else if (x < 2.5 / d1) {
		    return n1 * (y -= 2.25 / d1) * x + 0.9375;
		} 
		else {
		    return n1 * (y -= 2.625 / d1) * x + 0.984375;
		}
	}
	
	/**
	 * <a href="https://easings.net/#easeInOutBounce">https://easings.net/#easeInOutBounce</a>
	 */
	public static double easeInOutBounce(final double x) {
		return x < 0.5
				  ? (1 - easeOutBounce(1 - 2 * x)) / 2
				  : (1 + easeOutBounce(2 * x - 1)) / 2;
	}
}
