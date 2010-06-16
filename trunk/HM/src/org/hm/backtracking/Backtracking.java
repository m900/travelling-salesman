package org.hm.backtracking;

import org.hm.Graph;

public class Backtracking {

	private final int numCitites;
	private final Graph graph;
	private int[] st;
	private double shortestPath = Double.MAX_VALUE;

	public Backtracking(Graph graph) {
		this.numCitites = graph.size();
		this.graph = graph;
		st = new int[numCitites];
	}

	private void init(int k){
		st[k] = -1;
	}

	private boolean successor(int k){
		if (st[k]<numCitites-1){
			st[k] = st[k] +1;
			return true;
		}
		return false;
	}
	
	private boolean valid(int k){
		for (int i=0;i<k;i++)
			if (st[i] == st[k]) return false;
		return true;
	}
	
	private boolean solutie(int k){
		return k == numCitites;
	}

	private void tipar(){
		double path = 0;
		for (int i = 0; i < st.length - 1; i++) {
			path += graph.distance(st[i], st[i+1]);
		}
		if (path < shortestPath)
			shortestPath = path;
	}
	
	private void back(int k){
		if (solutie(k))
			tipar();
		else {
			init(k);
			while (successor(k)){
				if (valid(k))
					back(k+1);
			}
		}
	}
	
	public double getShortestPath() {
		return shortestPath;
	}

	public void backtrack() {
		back(0);
	}

	/*public static void main(String[] args) {
		Backtracking backtracking = new Backtracking(null);
		backtracking.backtrack();
	}*/
}
