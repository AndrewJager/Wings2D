package com.wings2d.framework.core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class Grid {
	private class SearchNode {
		public Node node;
		public boolean visited;
		public SearchNode parent;
		public Double globalGoal, localGoal;
		
		public SearchNode(final Node node) {
			this.node = node;
			visited = false;
			globalGoal = Double.MAX_VALUE;
			localGoal = Double.MAX_VALUE;
			parent = null;
		}
		
		public double mDist(final SearchNode nodeEnd) {
			double x = Math.abs(node.getX() - nodeEnd.node.getX());
			double y = Math.abs(node.getY() - nodeEnd.node.getY());
			return x + y;
		}
	}

	private int x, y;
	private Node[][] nodes;
	private Node activeNode = null;
	private Node playerNode;
	private List<Node> path;

	private int cellSize;
	private int gridWidth;
	private int gridHeight;
	private int xPos;
	private int yPos;
	
	private GridEntity test;
	private LevelManager manager;
	
	public Grid(final int x, final int y, final Component panel, final LevelManager manager) {
		this.x = x;
		this.y = y;
		this.manager = manager;

		nodes = new Node[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                nodes[i][j] = new Node(i, j, this);
            }
        }
        nodes[1][1].setPassable(false);
        nodes[2][1].setPassable(false);
        
        nodes[2][3].setPassable(false);
        nodes[2][4].setPassable(false);
        nodes[2][5].setPassable(false);
        
        nodes[3][4].setPassable(false);
        nodes[4][4].setPassable(false);
        nodes[5][4].setPassable(false);
        
        
        // Build connections between nodes
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
            	addNode(nodes[i][j].neighbors, i, j - 1); // North
            	addNode(nodes[i][j].neighbors, i, j + 1); // South 
            	addNode(nodes[i][j].neighbors, i + 1, j); // East
            	addNode(nodes[i][j].neighbors, i - 1, j); // West
            	addNode(nodes[i][j].neighbors, i - 1, j - 1); // Northwest
            	addNode(nodes[i][j].neighbors, i + 1, j - 1); // Northeast
            	addNode(nodes[i][j].neighbors, i - 1, j + 1); // Southwest
            	addNode(nodes[i][j].neighbors, i + 1, j + 1); // Southeast
            }
        }
        
        playerNode = nodes[0][0];

        test = new GridEntity(this, playerNode);
        
        panel.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {}
			public void mouseMoved(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();
				
				activeNode = null;
				for (int i = 0; i < x; i++) {
					int cellMinX = xPos + (i * cellSize);
					int cellMaxX = xPos + (i * cellSize) + cellSize;
					if ((mouseX > cellMinX) && (mouseX < cellMaxX)) {
						for (int j = 0; j < y; j++) {
							int cellMinY = yPos + (j * cellSize);
							int cellMaxY = yPos + (j * cellSize) + cellSize;
							if ((mouseY > cellMinY) && (mouseY < cellMaxY)) {
								activeNode = nodes[i][j];
							}
						}
						break;
					}
				}
			}
        });
        panel.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/* In what must be one of the worst software design decisions I've seen,
				 * the mouseClicked event doesn't fire if the mouse is moved -even by a pixel-
				 * between the press and release. Since the action of pressing the mouse button
				 * will sometimes cause the mouse to move a bit, any application using the
				 * mouseClicked event will miss some of the users clicks. 
				 * 
				 * This event is genuinely unusable because of this.
				 */

			}
			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (activeNode != null) {
//					test.setTarget(getNodeX(activeNode), getNodeY(activeNode), manager.getScale());
					path = aStarPath(playerNode, nodes[activeNode.getX()][activeNode.getY()]);
					if (path != null) {
						test.setPath(path);
					}
//					playerNode = nodes[activeNode.getX()][activeNode.getY()];
				}
				
//				test.setTarget(e.getX(), e.getY());
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
        });
        
        path = aStarPath(playerNode, nodes[1][3]);
	}
	
	public void render(final Graphics2D g2d, final double scale) {
		final int lineWidth = 5;
		g2d.setStroke(new BasicStroke(lineWidth));
		g2d.setColor(Color.GREEN);
		g2d.drawRect(xPos, yPos, gridWidth, gridHeight);
		for (int i = 1; i < x; i++) {
			g2d.drawLine(xPos + cellSize * i, yPos, xPos + cellSize * i, yPos + gridHeight);
		}
		for (int i = 1; i < y; i++) {
			g2d.drawLine(xPos, yPos + cellSize * i, xPos + gridWidth, yPos + cellSize * i);
		}
		
		
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
            	Node node = nodes[i][j];
            	g2d.setColor(Color.RED);
            	if (!node.getPassable()) {
            		g2d.fillRect(xPos + cellSize * node.getX(), yPos + cellSize * node.getY(), cellSize, cellSize);
            	}
            	
            	// Draw connections
//            	g2d.setColor(Color.WHITE);
//            	for (int k = 0; k < nodes[i][j].neighbors.size(); k++) {
//            		Node n = nodes[i][j].neighbors.get(k);
//            		g2d.drawLine(xPos + cellSize * node.getX() + (cellSize / 2), yPos + cellSize * node.getY() + (cellSize / 2),
//            				xPos + cellSize * n.getX() + (cellSize / 2), yPos + cellSize * n.getY() + (cellSize / 2));
//            	}
            }
        }
        
//        for (int i = 0; i < x; i++) {
//            for (int j = 0; j < y; j++) {
//            	Node node = nodes[i][j];
//            	g2d.setColor(Color.WHITE);
//            	Wings2DUtils.translateRender(g2d, new Runnable() {
//            		@Override
//            	    public void run() {
//            			g2d.drawRect(0, 0, 2, 2);
//            	    }
//            	}, this.getNodeX(node), this.getNodeY(node));
//            }
//        }
        
        
        // Draw path
        if (path != null) {
	        g2d.setColor(Color.YELLOW);
	        for (int i = 0; i < path.size(); i++) {
	        	if (i < path.size() - 1) {
	        		Node n1 = path.get(i);
	        		Node n2 = path.get(i + 1);
		        	g2d.drawLine(xPos + cellSize * n1.getX() + (cellSize / 2), yPos + cellSize * n1.getY() + (cellSize / 2),
		    				xPos + cellSize * n2.getX() + (cellSize / 2), yPos + cellSize * n2.getY() + (cellSize / 2));
	        	}
	        }
        }
		
		if (activeNode != null) {
			g2d.setColor(Color.WHITE);
			int nodeX = activeNode.getX();
			int nodeY = activeNode.getY();
			g2d.fillRect(xPos + cellSize * nodeX, yPos + cellSize * nodeY, cellSize, cellSize);
		}
		
		// draw test
		test.render(g2d, scale);
	}
	
	public void update(final double dt) {
		updateEntity(test, dt);
	}
	
	public void recalcGridSize(final JComponent panel) {
		int screenWidth = panel.getWidth();
		int screenHeight = panel.getHeight();
		int cellMaxWidth = screenWidth / x;
		int cellMaxHeight = screenHeight / y;
		cellSize = (cellMaxWidth > cellMaxHeight) ? cellMaxHeight : cellMaxWidth;
		gridWidth = (cellSize * x);
		gridHeight = (cellSize * y);
		xPos = (screenWidth - gridWidth) / 2;
		yPos = (screenHeight - gridHeight) / 2;
//		System.out.println("HI");
//		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
//		for (int i = 0; i < trace.length; i++) {
//			System.out.println(trace[i]);
//		}
	}
	
	public double getNodeX(final Node node) {
		double cellHalf = (double)cellSize / 2;
		
		double result = xPos + ((double)(node.getX() * cellSize)) + cellHalf;
		return result;
	}
	public double getNodeY(final Node node) {
		return yPos + (node.getY() * cellSize) + (cellSize / 2);
	}
	
	/*
	 * Input as a percentage, 0 - 100
	 */
	public double getX(final double xPercent) {
		return xPos + (gridWidth * xPercent);
	}
	public double getY(final double yPercent) {
		return yPos + (gridHeight * yPercent);
	}
	public double getNodePercentX(final Node node) {
		double cellPercent = (double)cellSize / gridWidth;
		return ((double)node.getX() / x) + (cellPercent / 2);
	}
	public double getNodePercentY(final Node node) {
		double cellPercent = (double)cellSize / gridHeight;
		return ((double)node.getY() / y) + (cellPercent / 2);
	}
	
	private void updateEntity(final GridEntity e, final double dt) {
		e.update(dt);
	}
	
	private List<Node> aStarPath(final Node startNode, final Node endNode) {
		SearchNode[][] searchGraph = new SearchNode[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				searchGraph[i][j] = new SearchNode(nodes[i][j]);
			}
		}
		SearchNode start = searchGraph[startNode.getX()][startNode.getY()];
		SearchNode end = searchGraph[endNode.getX()][endNode.getY()];
		start.localGoal = 0.0;
		start.globalGoal = start.mDist(end);
		
		List<SearchNode> open = new ArrayList<SearchNode>();
		open.add(start);
		
		SearchNode currentNode;
		while(!open.isEmpty()) {
			Collections.sort(open, (o1, o2) -> o1.globalGoal.compareTo(o2.globalGoal));
			
			// Remove nodes until we have one that hasn't been visited
			while(!open.isEmpty() && open.get(0).visited) {
				open.remove(0);
			}
			
			if (open.isEmpty()) {
				break;
			}
			
			currentNode = open.get(0);
			currentNode.visited = true;
			List<Node> neighbors = currentNode.node.neighbors;
			for (int i = 0; i < neighbors.size(); i++) {
				Node n = neighbors.get(i);
				SearchNode search = searchGraph[n.getX()][n.getY()];
				if (!search.visited && search.node.getPassable()) {
					open.add(search);
				}
				
				double poss = currentNode.localGoal + currentNode.mDist(search);
				if (poss < search.localGoal) {
					search.parent = currentNode;
					search.localGoal = poss;
					
					search.globalGoal = search.globalGoal + search.mDist(end);
				}
			}
		}
		
		if (end.parent == null) {
			return null; // No path found
		}
		else {
			List<Node> path = new ArrayList<Node>();
			SearchNode p = end;
			while (p != null) {
				path.add(p.node);
				p = p.parent;
			}
			Collections.reverse(path);
			return path;
		}
	}
	
	private void addNode(final List<Node> a, final int x, final int y) {
		if (nodeExists(x, y)) {
			a.add(nodes[x][y]);
		}
	}
	private boolean nodeExists(final int x, final int y) {
		boolean exists = false;
		if (x >= 0 && y >= 0) {
			if (x < nodes.length && y < nodes[0].length) {
				exists = true;
			}
		}
		return exists;
	}
	
	public LevelManager getManager() {
		return manager;
	}
	
	public Node[][] getNodes() {
		return nodes;
	}
	
	public void OnEntityPathComplete(final GridEntity e) {
		playerNode = e.getNode();
	}
	
	public void print() {
//		test.print();
	}
}
