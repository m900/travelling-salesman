package org.hm.tabuSearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.hm.Graph;

public class TabuSearch {

	private static int TABUTENURE = 0;
	private Graph graph;
	private Queue<Integer> tabuI = new ConcurrentLinkedQueue<Integer>();
	private Queue<Integer> tabuJ = new ConcurrentLinkedQueue<Integer>();
	private ArrayList<Integer> currentSolution = new ArrayList<Integer>();
	private ArrayList<Integer> bestSolution = new ArrayList<Integer>();
	private double bestDouble = -1.0;
	
	public TabuSearch(Graph graph) {
		this.graph = graph;
		TABUTENURE = Double.valueOf(Math.sqrt(graph.size())).intValue();
	}

	public double search(int tenure){
		TABUTENURE = tenure;
		Random random = new Random();
		currentSolution = new ArrayList<Integer>();
		currentSolution = randomSolution();
		int i = random.nextInt(graph.size());
		int j = random.nextInt(graph.size()); // make sure i<j<=N
		double currentLength = computeLength(currentSolution);
		bestDouble= currentLength;
		insertTabu(i,j);
		for (int k =0;k<1000000;k++){//The termination condition may involve completion of a certain number of iterations or exhausting a certain time budget.
			i = random.nextInt(graph.size()-1);
			j = random.nextInt(graph.size()); // make sure i<j<=N
			while(i>=j)
				j = random.nextInt(graph.size()); // make sure i<j<=N
			ArrayList<Integer> nextSolution = new ArrayList<Integer>(currentSolution);

			//swap the two elements 
			Integer swap = nextSolution.get(i);
			nextSolution.set(i, new Integer(nextSolution.get(j)));
			nextSolution.set(j, new Integer(swap));
			
			int si=i+1;
			int sj=j-1;
			while (si<sj){//reverse direction of elements between i and j
				swap = nextSolution.get(si);
				nextSolution.set(si, new Integer(nextSolution.get(sj)));
				nextSolution.set(sj, new Integer(swap));
				si++;
				sj--;	
			}
			double nextLength = computeLength(nextSolution);
			if (nextLength<currentLength){
				if (!(tabuI.contains(i)&&tabuJ.contains(j))){
					currentLength = nextLength;
					currentSolution = new ArrayList<Integer>(nextSolution);
					
					if (nextLength<bestDouble){
						bestDouble = nextLength;
						bestSolution = new ArrayList<Integer>(nextSolution); 
					}
					
					if (tabuI.size() > TABUTENURE){
						tabuI.poll();
						tabuJ.poll();
					}
					tabuI.add(i);
					tabuJ.add(j);
				}
				else
					if (nextLength<bestDouble){//best solution ?
						bestDouble = nextLength;
						currentLength = nextLength;
						currentSolution = new ArrayList<Integer>(nextSolution);  
					}
			}
		}
		return bestDouble;
	}

	private double computeLength(ArrayList<Integer> nextSolution) {
		double d = 0.0;
		for (int i = 1; i <= nextSolution.size()-1; i++)
			d += graph.distance(nextSolution.get(i) , nextSolution.get(i - 1));
		return d;
	}

	private ArrayList<Integer> randomSolution() {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = 0;i<graph.size();i++){
			result.add(new Integer(i));
		}
		Collections.shuffle(result);
		return result;
	}


	private void insertTabu(int i, int j) {
		tabuI.add(new Integer(i));
		tabuJ.add(new Integer(j));
	}

}
