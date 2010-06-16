package org.hm.main;

import org.hm.Graph;
import org.hm.NeighborhoodSearch;
import org.hm.PlaneGraph;
import org.hm.Points;
import org.hm.simulatedAnnealing.ISimulatedAnnealing;
import org.hm.simulatedAnnealing.SimulatedAnnealing;


public class TravellingSalesman {
	public static void main(String[] args) {
		int noPoints = 8;
		
		System.out.println(neighborhoodSearch(noPoints));
		
		simmulatedAnnealing(noPoints);
		
		noPoints = 10;
		
		System.out.println(neighborhoodSearch(noPoints));
		
		simmulatedAnnealing(noPoints);
		
		noPoints = 50;
		
		System.out.println(neighborhoodSearch(noPoints));
		
		noPoints = 100;
		
		System.out.println(neighborhoodSearch(noPoints));
	}
	
	private static void backtracking(int numberOfNodes) {
		Points p = readPoints(numberOfNodes);
	}
	
	private static double neighborhoodSearch(int numberOfNodes) {
		Points points = readPoints(numberOfNodes);
		Graph theGraph = new PlaneGraph(points);
		NeighborhoodSearch neighborhoodSearch = new NeighborhoodSearch(theGraph);
		neighborhoodSearch.findPath();
		return neighborhoodSearch.length();
	}
	
	private static void simmulatedAnnealing(int numberOfNodes) {
		Points points = readPoints(numberOfNodes);
		Graph theGraph = new PlaneGraph(points);
		ISimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(100, 0.99, numberOfNodes, theGraph);
		simulatedAnnealing.findPath();
	}
	
	private static void tabusearch(int numberOfNodes) {
		Points p = readPoints(numberOfNodes);
	}
	
	
	private static Points readPoints(int numberOfNodes) {
		Points p = new Points(numberOfNodes, "input"+numberOfNodes+".txt");
		return p;
	}
}
