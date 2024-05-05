package com.wings2d.framework.core;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Loop {
	private long lastLoopTime = 0;
	private long curLoopTime = 0;
	private long lastFpsTime = 0;
	private double deltaSum = 0;
	private int framesInSecond = 0;
	private boolean loopInitalized = false;
	
	private ScheduledExecutorService service;
	private Consumer<Double> loopMethod;
	private LoopStatistics stats;
	
	public Loop(final Consumer<Double> loopMethod, final int targetFps, final LoopStatistics stats) {
		this.loopMethod = loopMethod;
		this.stats = stats;
		
		final long OPTIMAL_TIME = 1000000000 / targetFps; 
		service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(this::onLoop, 0, OPTIMAL_TIME, TimeUnit.NANOSECONDS);   
	}
	public Loop(final Consumer<Double> loopMethod, final int targetFps) {
		this(loopMethod, targetFps, null);
	}
	
	private void onLoop() {
		lastLoopTime = curLoopTime; // Must be before setting of lastLoopTime in init
		if (!loopInitalized)
		{
			lastLoopTime = System.nanoTime();
			loopInitalized = true;
		}
		
		// Calculate time delta
		long now = System.nanoTime();
		curLoopTime = now;
		long updateLength = (curLoopTime - lastLoopTime);
		double delta = updateLength / 1000000000.0;
		deltaSum += delta;
		lastFpsTime += updateLength;
		framesInSecond++;
	
		// Update debug info every second
		if (lastFpsTime >= 1000000000)
		{
			if (stats != null) {
				stats.setFps(framesInSecond);
				stats.setDeltaSum(deltaSum);
			}
			lastFpsTime = 0;
			framesInSecond = 0;
			deltaSum = 0;
		}
		try {
			loopMethod.accept(delta);
		}
		catch (Exception e) {
			// Unsure why I have to do this, but I had an exception that was crashing the thread, but not printing to the console.
			// This let's me debug that.
			e.printStackTrace();
		}
	}
	
	public void shutdown() {
		service.shutdown();
	}
}
