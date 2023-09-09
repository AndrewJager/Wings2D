package com.wings2d.framework.core;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private int x, y;
	private Grid grid;
	public List<Node> neighbors;
	private boolean passable = true;
	
	public Node(final int x, final int y, final Grid grid) {
		this.x = x;
		this.y = y;
		this.grid = grid;
		
		neighbors = new ArrayList<Node>();
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Grid getGrid() {
		return grid;
	}
	
	public void setPassable(final boolean p) {
		this.passable = p;
	}
	public boolean getPassable() {
		return passable;
	}
}
