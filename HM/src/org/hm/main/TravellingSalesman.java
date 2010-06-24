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
		TabuSearch tabuSearch = new TabuSearch(theGraph);
		Profiler.enter("tabuSearch");
		System.out.println("Tabu Search   : "+tabuSearch.search(20));
		Profiler.leave("tabuSearch");
		Profiler.print("tabuSearch", "took:");
		Profiler.reset("tabuSearch");
		return 0;
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
