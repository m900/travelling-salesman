package org.hm.simulatedAnnealing;


public interface ISimulatedAnnealing {
	public int getCount();
	public int getCycles();
	public double getError(int i,int j);
	public double getStartingTemperature();
	public double getDelta();
	public void findPath();
	public int[] getOrder();
}
