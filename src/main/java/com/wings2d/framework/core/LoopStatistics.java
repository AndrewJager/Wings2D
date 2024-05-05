package com.wings2d.framework.core;

public class LoopStatistics {
	private int fps;
	private double deltaSum;
	
	public LoopStatistics() {
		this.fps = 0;
		this.deltaSum = 0;
	}
	
	public int getFps() {return fps;}
	public double getDeltaSum() {return deltaSum;};
	
	public void setFps(final int fps) {
		this.fps = fps;
	}
	public void setDeltaSum(final double deltaSum) {
		this.deltaSum = deltaSum;
	}
}