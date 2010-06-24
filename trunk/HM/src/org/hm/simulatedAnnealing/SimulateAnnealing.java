package org.hm.simulatedAnnealing;


public class SimulateAnnealing {

	/**
	 * The current temperature.
	 */
	private double temperature;

	/**
	 * The length of the current path.
	 */
	private double pathlength;

	/**
	 * The length of the best path.
	 */
	private double minimallength;

	/**
	 * The current order of cities.
	 */
	private int order[];

	/**
	 * The best order of cities.
	 */
	private int minimalorder[];

	private ISimulatedAnnealing owner;

	/**
	 * Constructor
	 *
	 * @param owner The TravelingSalesman class that owns this object.
	 */
	SimulateAnnealing(ISimulatedAnnealing owner){
		this.owner = owner;
		order = new int[owner.getCount()];
		minimalorder = new int[owner.getCount()];
	}

	/**
	 * Called to determine if annealing should take place.
	 *
	 * @param d The distance.
	 * @return True if annealing should take place.
	 */
	public boolean anneal(double d){
		if (temperature < 1.0E-4) {
			if (d > 0.0)
				return true;
			else
				return false;
		}
		if (Math.random() < Math.exp(d / temperature))
			return true;
		else
			return false;
	}



	/**
	 * Used to ensure that the passed in integer is within thr city range.
	 *
	 * @param i A city index.
	 * @return A city index that will be less than CITY_COUNT
	 */
	public int mod(int i){
		return i % owner.getCount();
	}


	public double findPath(){
		try{
		int cycle = 1;
		int sameCount = 0;
		temperature = owner.getStartingTemperature();

		initorder(order);
		initorder(minimalorder);

		pathlength = length();
		minimallength = pathlength;

		while (sameCount<500) {
			//System.out.println("Cycle=" + cycle + ",Length=" + minimallength + ",Temp=" + temperature );
			// make adjustments to city order(annealing)
			for (int j2 = 0; j2 < owner.getCount() * owner.getCount(); j2++) {
				int i1 = (int)Math.floor((double)owner.getCount() * Math.random());
				int j1 = (int)Math.floor((double)owner.getCount() * Math.random());
				double d = owner.getError(i1, i1 + 1) + owner.getError(j1, j1 + 1) - owner.getError(i1, j1) - owner.getError(i1 + 1, j1 + 1);
				if (anneal(d)) {
					if (j1 < i1) {
						int k1 = i1;
						i1 = j1;
						j1 = k1;
					}
					for (; j1 > i1; j1--) {
						int i2 = order[i1 + 1];
						order[i1 + 1] = order[j1];
						order[j1] = i2;
						i1++;
					}
				}
			}
			// See if this improved anything
			pathlength = length();
			if (pathlength < minimallength) {
				minimallength = pathlength;
				for (int k2 = 0; k2 < owner.getCount(); k2++){
					minimalorder[k2] = new Integer(order[k2]);
				}
				sameCount=0;
			} else
				sameCount++;
			temperature = owner.getDelta() * temperature;
			cycle++;
		}
		// we're done
		//System.out.println("Simulated anealing:  "+minimallength);
		return minimallength;
		}
		catch (Throwable e){
			e.printStackTrace();
			return minimallength;
		}
	}

	/**
	 * Return the length of the current path through
	 * the cities.
	 *
	 * @return The length of the current path through the cities.
	 */
	public double length(){
		double d = 0.0;
		for (int i = 1; i <= owner.getCount()-1; i++)
			d += owner.getError(i, i-1);
		return d;
	}
	
	public double currentMinimalLength(){
		double d = 0.0;
		for (int i = 1; i <= owner.getCount()-1; i++)
			d += owner.getGraph().distance(minimalorder[i], minimalorder[i - 1]);
		return d;
	}

	/**
	 * Set the specified array to have a list of the cities in
	 * order.
	 *
	 * @param an An array to hold the cities.
	 */
	public void initorder(int an[]){
		for (int i = 0; i < owner.getCount(); i++)
			an[i] = i;
	}

	public int[] getOrder() {
		return order;
	}
	
	public int[] getMinimalOrder() {
		return minimalorder;
	}
}