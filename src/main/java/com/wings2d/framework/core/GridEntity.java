package com.wings2d.framework.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import com.wings2d.framework.core.LevelManager.Coord;
import com.wings2d.framework.core.LevelManager.Location;
import com.wings2d.framework.misc.Easings;

public class GridEntity {
	public enum EntityState {
		IDLE,
		MOVING,
	}
	public enum Dir { // Increase or decrease x/y position
		POS,
		NEG,
		HOLD,
	}
	
	protected Grid grid;
	protected Location loc;
	protected double targetX, targetY;
	protected EntityState state;
	protected Dir xDir, yDir;
	protected List<Node> path;
	protected int curNode;
	protected Node node;
	protected double startX, startY;
	protected double xDist, yDist; // Distance from start to target location
	protected double movedAmt; // 0 - 1, with 0 being at start loc, 1 being at end loc
	
	private double speed = 2;
	
	public GridEntity(final Grid grid, final Node playerNode) {
		this.grid = grid;
		this.node = playerNode;
		loc = grid.getManager().makeLocation();
		loc.setNode(playerNode);

		state = EntityState.IDLE;
		xDir = Dir.HOLD;
		yDir = Dir.HOLD;
	}
	
	public Location getLocation() {
		return loc;
	}

	public void setX(double x) {
		loc.getX().setUnscaled(x);
	}

	public void setY(double y) {
		loc.getY().setUnscaled(y);
	}
	
	public void setPath(List<Node> path) {
		this.path = path;
		curNode = 0;
		node = path.get(curNode);
		setTarget(node);
	}
	
	public void setTarget(final double x, final double y) {
		this.targetX = x;
		this.targetY = y;
		this.xDist = this.targetX - loc.getX().getUnscaled();
		this.yDist = this.targetY - loc.getY().getUnscaled();
		this.startX = loc.getX().getUnscaled();
		this.startY = loc.getY().getUnscaled();
		this.movedAmt = 0;
		
		if (loc.getX().getUnscaled() > this.targetX) {
			this.xDir = Dir.NEG;
		}
		else if(loc.getX().getUnscaled() < this.targetX) {
			this.xDir = Dir.POS;
		}
		else {
			this.xDir = Dir.HOLD;
		}
		
		if (loc.getY().getUnscaled() > this.targetY) {
			this.yDir = Dir.NEG;
		}
		else if(loc.getY().getUnscaled() < this.targetY) {
			this.yDir = Dir.POS;
		}
		else {
			this.yDir = Dir.HOLD;
		}
		this.state = EntityState.MOVING;
	}
	
	public void setTarget(final Node n) {
		this.setTarget(grid.getNodeX(n), grid.getNodeY(n));
	}
	
	public void update(final double dt) {
		switch(state) {
		case IDLE -> {}
		case MOVING -> {
			this.movedAmt = this.movedAmt + (dt * speed);
			double amt = this.movedAmt;
//			double amt = Easings.easeInCubic(movedAmt);
			switch(xDir) {
			case POS -> {
				loc.getX().setUnscaled(this.startX + (this.xDist * amt));
				if (loc.getX().getUnscaled() >= this.targetX) {
					loc.getX().setUnscaled(this.targetX);
					xDir = Dir.HOLD;
				}
			}
			case NEG -> {
				loc.getX().setUnscaled(this.startX + (this.xDist * amt));
				if (loc.getX().getUnscaled() <= this.targetX) {
					loc.getX().setUnscaled(this.targetX);
					xDir = Dir.HOLD;
				}
			}
			case HOLD -> {}
			}
			
			switch(yDir) {
			case POS -> {
				loc.getY().setUnscaled(this.startY + (this.yDist * amt));
				if (loc.getY().getUnscaled() >= this.targetY) {
					loc.getY().setUnscaled(this.targetY);
					yDir = Dir.HOLD;
				}
			}
			case NEG -> {
				loc.getY().setUnscaled(this.startY + (this.yDist * amt));
				if (loc.getY().getUnscaled() <= this.targetY) {
					loc.getY().setUnscaled(this.targetY);
					yDir = Dir.HOLD;
				}
			}
			case HOLD -> {}
			}
			
			if ((xDir == Dir.HOLD) && (yDir == Dir.HOLD)) {
				loc.setNode(path.get(curNode));
				if (curNode < path.size() - 1) {
					curNode++;
					node = path.get(curNode);
					setTarget(path.get(curNode));
				}
				else {
					this.state = EntityState.IDLE;
					grid.OnEntityPathComplete(this);
				}
			}
		}
		}
	}
	
	public void render(final Graphics2D g2d, final double scale) {
		g2d.setColor(Color.BLUE);
		double size = 20;
		double xAmt = loc.getX().getUnscaled() - ((size / 2) * scale);
		double yAmt = loc.getY().getUnscaled() - ((size / 2) * scale);
		g2d.translate(xAmt, yAmt);
		
		g2d.fillRect(0, 0, (int)(size * scale), (int)(size * scale));
		g2d.translate(-xAmt, -yAmt);
	}
	
	public Node getNode() {
		return node;
	}
}
