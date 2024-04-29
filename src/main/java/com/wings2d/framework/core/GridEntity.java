package com.wings2d.framework.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import com.wings2d.framework.core.LevelManager.Coord;
import com.wings2d.framework.core.LevelManager.Location;


public class GridEntity {
	private class GridCoord {
		public double xPercent, yPercent;
		
		public GridCoord(final double xPercent, final double yPercent) {
			this.xPercent = xPercent;
			this.yPercent = yPercent;
		}
	}
	
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
	protected Location loc; // Current location
	protected GridCoord targetLoc; // Location to move to
	protected Location startLoc; // Starting location of current movement
	protected Location dist; // Distance between startLoc and targetLoc (kind of hacky to use Location class like this)
	protected EntityState state;
	protected Dir xDir, yDir;
	protected List<Node> path;
	protected int curNode;
	protected Node node;
	protected double movedAmt; // 0 - 1, with 0 being at start loc, 1 being at end loc
	
	private double speed = 0.2;
	
	public GridEntity(final Grid grid, final Node playerNode) {
		this.grid = grid;
		this.node = playerNode;
		loc = grid.getManager().makeLocation();
		loc.setNode(playerNode);
		startLoc = grid.getManager().makeLocation();
		targetLoc = new GridCoord(0, 0);
		dist = grid.getManager().makeLocation();

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
	
	public void setTarget(final Node n) {
		System.out.println(grid.getNodePercentX(n));
		targetLoc.xPercent = grid.getNodePercentX(n);
		targetLoc.yPercent = grid.getNodePercentY(n);
		dist.getX().setUnscaled(grid.getX(targetLoc.xPercent) - loc.getX().getUnscaled());
		dist.getY().setUnscaled(grid.getY(targetLoc.yPercent) - loc.getY().getUnscaled());
		startLoc.getX().setUnscaled(loc.getX().getUnscaled());
		startLoc.getY().setUnscaled(loc.getY().getUnscaled());

		this.movedAmt = 0;
		
		if (loc.getX().getUnscaled() > grid.getX(targetLoc.xPercent)) {
			this.xDir = Dir.NEG;
		}
		else if(loc.getX().getUnscaled() < grid.getX(targetLoc.xPercent)) {
			this.xDir = Dir.POS;
		}
		else {
			this.xDir = Dir.HOLD;
		}
		
		if (loc.getY().getUnscaled() > grid.getY(targetLoc.yPercent)) {
			this.yDir = Dir.NEG;
		}
		else if(loc.getY().getUnscaled() < grid.getY(targetLoc.yPercent)) {
			this.yDir = Dir.POS;
		}
		else {
			this.yDir = Dir.HOLD;
		}
		this.state = EntityState.MOVING;
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
				loc.getX().setUnscaled(startLoc.getX().getUnscaled() + (dist.getX().getUnscaled() * amt));
				if (loc.getX().getUnscaled() >= grid.getX(targetLoc.xPercent)) {
					loc.getX().setUnscaled(grid.getX(targetLoc.xPercent));
					xDir = Dir.HOLD;
				}
			}
			case NEG -> {
				loc.getX().setUnscaled(startLoc.getX().getUnscaled() + (dist.getX().getUnscaled() * amt));
				if (loc.getX().getUnscaled() <= grid.getX(targetLoc.xPercent)) {
					loc.getX().setUnscaled(grid.getX(targetLoc.xPercent));
					xDir = Dir.HOLD;
				}
			}
			case HOLD -> {}
			}
			
			switch(yDir) {
			case POS -> {
				loc.getY().setUnscaled(startLoc.getY().getUnscaled() + (dist.getY().getUnscaled() * amt));
				if (loc.getY().getUnscaled() >= grid.getY(targetLoc.yPercent)) {
					loc.getY().setUnscaled(grid.getY(targetLoc.yPercent));
					yDir = Dir.HOLD;
				}
			}
			case NEG -> {
				loc.getY().setUnscaled(startLoc.getY().getUnscaled() + (dist.getY().getUnscaled() * amt));
				if (loc.getY().getUnscaled() <= grid.getY(targetLoc.yPercent)) {
					loc.getY().setUnscaled(grid.getY(targetLoc.yPercent));
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
		double size = 20;
		double xPos = loc.getX().getUnscaled() - ((size / 2) * scale);
		double yPos = loc.getY().getUnscaled() - ((size / 2) * scale);
		
		g2d.setColor(Color.BLUE);
		Wings2DUtils.translateRender(g2d, new Runnable() {
    		@Override
    	    public void run() {
    			g2d.fillRect(0, 0, (int)(size * scale), (int)(size * scale));
    	    }
    	}, xPos, yPos);

		double sx = startLoc.getX().getUnscaled();
		double sy = startLoc.getY().getUnscaled();
		g2d.setColor(Color.WHITE);
//		Wings2DUtils.translateRender(g2d, new Runnable() {
//    		@Override
//    	    public void run() {
//    			g2d.fillRect(0, 0, 8, 8);
//    			g2d.drawString("start (unscaled)", 0, 20);
//    	    }
//    	}, sx, sy);
//		
		double ex = grid.getX(targetLoc.xPercent);
		double ey = grid.getY(targetLoc.yPercent);
		Wings2DUtils.translateRender(g2d, new Runnable() {
    		@Override
    	    public void run() {
    			g2d.fillRect(0, 0, 8, 8);
    			g2d.drawString("end (unscaled)", 0, 20);
    	    }
    	}, ex, ey);
		
		
		double sx2 = startLoc.getX().getScaled();
		double sy2 = startLoc.getY().getScaled();
		g2d.setColor(Color.CYAN);
//		Wings2DUtils.translateRender(g2d, new Runnable() {
//    		@Override
//    	    public void run() {
//    			g2d.fillRect(0, 0, 8, 8);
//    			g2d.drawString("start (scaled)", 0, 20);
//    	    }
//    	}, sx2, sy2);
		
//		double ex2 = targetLoc.getX().getScaled();
//		double ey2 = targetLoc.getY().getScaled();
//		Wings2DUtils.translateRender(g2d, new Runnable() {
//    		@Override
//    	    public void run() {
//    			g2d.fillRect(0, 0, 8, 8);
//    			g2d.drawString("end (scaled)", 0, 20);
//    	    }
//    	}, ex2, ey2);
		
		g2d.setColor(Color.WHITE);
		g2d.drawString("Unscaled s X: " + sx, 10, 300);
		g2d.drawString("Scaled s X: " + sx2, 10, 320);
		g2d.drawString("Unscaled e X: " + ex, 10, 340);
//		g2d.drawString("Scaled e X: " + ex2, 10, 360);
		
		double z = grid.getX(0.8);
		double z1 = grid.getY(0.8);
		Wings2DUtils.translateRender(g2d, new Runnable() {
    		@Override
    	    public void run() {
    			g2d.fillRect(-4, -4, 8, 8);
    	    }
    	}, z, z1);
		
		Node n = grid.getNodes()[3][2];
		double a = grid.getX(grid.getNodePercentX(n));
		double aa = grid.getY(grid.getNodePercentY(n));
		
		Wings2DUtils.translateRender(g2d, new Runnable() {
    		@Override
    	    public void run() {
    			g2d.fillRect(-4, -4, 8, 8);
    	    }
    	}, a, aa);
	}
	
	public Node getNode() {
		return node;
	}
}
