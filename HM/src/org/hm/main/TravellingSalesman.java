package org.hm.main;

import org.hm.Graph;
import org.hm.PlaneGraph;
import org.hm.Points;
import org.hm.backtracking.Backtracking;
import org.hm.neighborhoodSearch.NeighborhoodSearch;
import org.hm.simulatedAnnealing.SimulatedAnnealing;
import org.hm.tabuSearch.TabuSearch;


public class TravellingSalesman {
	public static void main(String[] args) {
		int noPoints = 8;

		simulate(noPoints, true);
		
		noPoints = 10;

		simulate(noPoints, true);

		noPoints = 50;

		simulate(noPoints, false);

		noPoints = 100;

		simulate(noPoints, false);
	}

	private static void simulate(int noPoints, boolean backtrack){
		System.out.println("No points:"+noPoints);

		if (backtrack){
			backtracking(noPoints);
		}

		neighborhoodSearch(noPoints);

		simmulatedAnnealing(noPoints);
		
		System.out.println("Nu points:"+noPoints);
		
		tabuSearch(noPoints);

		System.out.println("---------------------");
	}

	private static double backtracking(int numberOfNodes) {
		Profiler.enter("backtrack");
		Points points = readPoints(numberOfNodes);
		Graph theGraph = new PlaneGraph(points);
		Backtracking backtracking = new Backtracking(theGraph);
		backtracking.backtrack();
		Profiler.leave("backtrack");
		Profiler.print("backtrack", "took:");
		Profiler.reset("backtrack");
		System.out.println("Backtracking:        "+backtracking.getShortestPath());
		return backtracking.getShortestPath();
	}
	
	private static double tabuSearch(int numberOfNodes) {
		Points points = readPoints(numberOfNodes);
		Graph theGraph = new PlaneGraph(points);
		
		runTabu(theGraph, 1);
		
		
		runTabu(theGraph, 0.8);
		

		runTabu(theGraph, 1.2);
		

		return 0;
	}

	private static void runTabu(Graph theGraph, double percentageTenure) {
		TabuSearch tabuSearch = new TabuSearch(theGraph, percentageTenure);
		Profiler.enter("tabuSearch");
		double bestPath = Double.MAX_VALUE;
		for (int i=0;i<10;i++) {
			double path = tabuSearch.search();
			if (path < bestPath)
				bestPath = path;
		}
		System.out.println("Tabu Search   : "+bestPath);
		System.out.println("Tenure:"+tabuSearch.getTABUTENURE());
		Profiler.leave("tabuSearch");
		Profiler.print("tabuSearch", "took:");
		Profiler.reset("tabuSearch");
	}

	private static double neighborhoodSearch(int numberOfNodes) {

		Points points = readPoints(numberOfNodes);
		Graph theGraph = new PlaneGraph(points);
		NeighborhoodSearch neighborhoodSearch = new NeighborhoodSearch(theGraph);
		Profiler.enter("neighborhoodSearch");
		neighborhoodSearch.findPath();
		Profiler.leave("neighborhoodSearch");
		Profiler.print("neighborhoodSearch", "took:");
		Profiler.reset("neighborhoodSearch");
		System.out.println("Neighborhood search: "+neighborhoodSearch.length());
		return neighborhoodSearch.length();
	}

	private static double simmulatedAnnealing(int numberOfNodes) {
		
		Points points = readPoints(numberOfNodes);
		Graph theGraph = new PlaneGraph(points);
		SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(15000, 0.99, numberOfNodes, theGraph);
		Profiler.enter("simmulatedAnnealing");
		simulatedAnnealing.findPath();
		int[] order = simulatedAnnealing.getMinimalOrder();
		Profiler.leave("simmulatedAnnealing");
		Profiler.print("simmulatedAnnealing", "took:");
		Profiler.reset("simmulatedAnnealing");
		return simulatedAnnealing.getCount();
	}

	private static Points readPoints(int numberOfNodes) {
		Points p = new Points(numberOfNodes, "input"+numberOfNodes+".txt");
		return p;
	}
}
