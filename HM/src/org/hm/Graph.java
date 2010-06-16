package org.hm;

public class Graph{	
	private int n;
	private double M[][];
	
	public Graph (int n){
		this.n = n;
		M = new double[n][n];
		int i,j;
		// initially disconnect all points
		for (i=0; i<n; i++)
			for (j=0; j<n; j++)
				if (i!=j) 
					connect(i,j,Double.POSITIVE_INFINITY);
				else connect(i,j,0);
	}
	
	public void connect(int i, int j, double x) {
		M[i][j]=x; 
	}
	
	public double distance(int i, int j) {
		return M[i][j]; 
	}
	
	public int size(){
		return n;
	}
}
