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
			Profiler.enter("Backtracting for "+noPoints+" nodes");
			System.out.println("Backtracking:        "+backtracking(noPoints));
			Profiler.leave("Backtracting for "+noPoints+" nodes");
			Profiler.print("Backtracting for "+noPoints+" nodes", "took:");
		}

		System.out.println("Neighborhood search: "+neighborhoodSearch(noPoints));

		simmulatedAnnealing(noPoints);

		System.out.println("---------------------");
	}

	private static double backtracking(int numberOfNodes) {
		Points points = readPoints(numberOfNodes);
		Graph theGraph = new PlaneGraph(points);
		Backtracking backtracking = new Backtracking(theGraph);
		backtracking.backtrack();
		return backtracking.getShortestPath();
	}
	private static double tabuSearch(int numberOfNodes) {
		Points points = readPoints(numberOfNodes);
		Graph theGraph = new PlaneGraph(points);
		TabuSearch tabuSearch = new TabuSearch(theGraph);
		System.out.println("tabu search de 10 "+tabuSearch.search(20));
		return 0;
	}

	private static double neighborhoodSearch(int numberOfNodes) {
		Points points = readPoints(numberOfNodes);
		Graph theGraph = new PlaneGraph(points);
		NeighborhoodSearch neighborhoodSearch = new NeighborhoodSearch(theGraph);
		neighborhoodSearch.findPath();
		return neighborhoodSearch.length();
	}

	private static double simmulatedAnnealing(int numberOfNodes) {
		Points points = readPoints(numberOfNodes);
		Graph theGraph = new PlaneGraph(points);
		SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(15000, 0.99, numberOfNodes, theGraph);
		simulatedAnnealing.findPath();
		int[] order = simulatedAnnealing.getMinimalOrder();
		return simulatedAnnealing.getCount();
	}

	private static void tabusearch(int numberOfNodes) {
		Points p = readPoints(numberOfNodes);
	}


	private static Points readPoints(int numberOfNodes) {
		Points p = new Points(numberOfNodes, "input"+numberOfNodes+".txt");
		return p;
	}
}
