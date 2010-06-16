package org.hm.neighborhoodSearch;

import java.util.Random;

import org.hm.Graph;

public class NeighborhoodSearch{
	private Graph g;
	private int numberOfNodes;
	private double pathLength;
	private int From[];
	private int To[];

	public NeighborhoodSearch (Graph g){	
		numberOfNodes = g.size();
		this.g = g;
		From = new int[numberOfNodes];
		To = new int[numberOfNodes];
	}

	/**
	 * random path
	 */
	private void random (){
		Random r = new Random();
		for (int i=0; i<numberOfNodes; i++) 
			To[i]=-1;
		int k, j0, i, j, i0;
		for (i0 = i = 0; i < numberOfNodes-1; i++){
			j=(int)(Math.abs(r.nextLong())%(numberOfNodes-i));
			To[i0]=0;

			for (j0 = k =0; k<j; k++){
				j0++;
				while (To[j0] != -1) 
					j0++;
			}
			while (To[j0]!=-1) 
				j0++;
			To[i0]=j0; From[j0]=i0;
			i0=j0;
		}
		To[i0]=0; From[0]=i0;
		getlength();
	}

	public double length () {
		return pathLength;
	}

	/**
	 * try to find another path with shorter length using removals of points j and inserting i,j,i+1
	 */
	private boolean improve (){
		int i,j,h;
		double d1,d2;
		double H[] = new double[numberOfNodes];
		for (i = 0;i < numberOfNodes; i++)
			H[i] = -g.distance(From[i],i) - g.distance(i,To[i]) + g.distance(From[i],To[i]);
		for (i=0; i<numberOfNodes; i++){
			d1 = -g.distance(i,To[i]);
			j=To[To[i]];
			while (j != i){
				d2 = H[j] + g.distance(i,j) + g.distance(j,To[i])+d1;
				if (d2 < -1e-10){
					h=From[j];
					To[h]=To[j]; 
					From[To[j]]=h;
					h=To[i]; 
					To[i]=j; 
					To[j]=h;
					From[h]=j; 
					From[j]=i;
					pathLength+=d2;
					return true;
				}
				j=To[j];
			}
		}
		return false;
	}

	/**
	 * improve the path locally, using replacements of i,i+1 and j,j+1 with i,j and i+1,j+1
	 */
	public boolean improvecross (){
		int i,j,h,h1,hj;
		double d1,d2,d;
		for (i=0; i<numberOfNodes; i++){
			d1 = -g.distance(i,To[i]);
			j = To[To[i]];
			d2 = 0;
			d = 0;
			while (To[j]!=i){
				d += g.distance(j,From[j]) - g.distance(From[j],j);
				d2 = d1 + g.distance(i,j) + d + g.distance(To[i],To[j]) - g.distance(j,To[j]);
				if (d2 < -1e-10){
					h=To[i]; h1=To[j];
					To[i]=j;
					To[h]=h1; From[h1]=h;
					hj=i;
					while (j!=h){
						h1=From[j];
						To[j]=h1;
						From[j]=hj;
						hj=j;
						j=h1;
					}
					From[j]=hj;
					pathLength+=d2;
					return true;
				}
				j=To[j];
			}
		}
		return false;
	}

	/**
	 * compute the length of the path
	 */
	public void getlength (){
		pathLength=0;
		for (int i=0; i < numberOfNodes; i++){
			pathLength += g.distance(i,To[i]);
		}
	}

	/**
	 * find a local optimum starting from this path
	 */
	public void findPath(){
		random();
		do{	
			while (improve());
		} 
		while (improvecross());
	}

	public int[] getFrom() {
		return From;
	}

	public int[] getTo() {
		return To;
	}
}
