package org.hm.simulatedAnnealing;


import org.hm.Graph;

public class SimulatedAnnealing implements ISimulatedAnnealing{

	private final double startingTemperature;

	private final double delta;

	private final Graph graph;

	public SimulatedAnnealing(double startingTemperature, double delta, int numCitites, Graph graph) {
		this.startingTemperature = startingTemperature;
		this.delta = delta;
		this.graph = graph;
		worker = new SimulateAnnealing(this);
	}
	
	/**
	 * The simulated annealing worker class.
	 */
	private SimulateAnnealing worker;


	/**
	 * Called to get the number of cities.
	 *
	 * @return The number of cities.
	 */
	public int getCount(){
		return graph.size();
	}

	/**
	 * Returns the distance between two cities.
	 *
	 * @param i The first city.
	 * @param j The second city.
	 * @return The distance between the two cities.
	 */
	public double getError(int i, int j){
		int c1 = worker.getOrder()[i % graph.size()];
	    int c2 = worker.getOrder()[j % graph.size()];
	    //return cities[c1].proximity(cities[c2]);
		return graph.distance(c1, c2);
	}

	/**
	 * Returns the starting temperature for the
	 * annealing process.
	 *
	 * @return The starting temperature for the annealing process.
	 */
	public double getStartingTemperature(){
		return startingTemperature;
	}

	/**
	 * Called to determine if annealing should take place.
	 *
	 * @param d The distance.
	 * @return True if annealing should take place.
	 */
	public double getDelta(){
		return delta;
	}

	/**
	 * Start the background thread.
	 */
	public void findPath(){
		worker.findPath();
	}

	@Override
	public int getCycles() {
		return 0;
	}

	@Override
	public int[] getOrder() {
		return worker.getOrder();
	}
	
	public int[] getMinimalOrder(){
		return worker.getMinimalOrder();
	}

	public Graph getGraph() {
		return graph;
	}

}
