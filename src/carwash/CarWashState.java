package carwash;


import deds.*;


/**
 * 
 * @author sebvik-0
 * 
 */
abstract public class CarWashState extends State
{
	public CarWashState()
	{
	}

	/**
	 * @return Number of fast washing machines.
	 */
	abstract public int getNumFastMachines();

	/**
	 * @return Number of fast washing machines currently available.
	 */
	abstract public int getNumFastMachinesAvailable();

	/**
	 * @return Number of slow washing machines.
	 */
	abstract public int getNumSlowMachines();

	/**
	 * @return Number of slow washing machines currently available.
	 */
	abstract public int getNumSlowMachinesAvailable();

	/**
	 * @return true if there are one or more machines available (of any kind.)
	 */
	abstract public boolean hasVacantMachines();

	/**
	 * @return Array containing min and max distribution values for fast washing
	 *         machines.
	 */
	abstract public float[] getFastDistribution();

	/**
	 * @return Array containing min and max distribution values for slow washing
	 *         machines.
	 */
	abstract public float[] getSlowDistribution();

	/**
	 * @return Exponential distribution lambda value.
	 */
	abstract public float getExponentialDistribution();

	/**
	 * @return Seed used by the state for random operations.
	 */
	abstract public int getSeed();

	/**
	 * @return Maximum car queue size.
	 */
	abstract public int getMaxQueueSize();

	/**
	 * @return Current length of the car queue.
	 */
	abstract public int getQueueSize();

	/**
	 * @return true if the car queue is empty.
	 */
	abstract public boolean isQueueEmpty();

	/**
	 * @return true if the car queue is full.
	 */
	abstract public boolean isQueueFull();

	/**
	 * @return Total time the washing machines have been idle.
	 */
	abstract public float getMachineIdleTime();

	/**
	 * @return Total time cars have spent in the queue.
	 */
	abstract public float getQueueTime();

	/**
	 * @return Total number of cars rejected.
	 */
	abstract public int getNumRejectedCars();

	/**
	 * Creates a new car.
	 * 
	 * @return The new car.
	 */
	abstract public Car createNewCar();

	/**
	 * @param car
	 *            The car to serve.
	 * 
	 * @return Serving time.
	 */
	abstract public float serveCar( Car car );

	/**
	 * Places a car at the back of the car queue.
	 * 
	 * @param car
	 *            The car to place in the queue.
	 */
	abstract public void placeCarInQueue( Car car );

	/**
	 * Pulls the car at the front from the car queue.
	 * 
	 * @return The pulled car.
	 */
	abstract public Car pullCarFromQueue();

	/**
	 * Removes a car from a washing machine.
	 * 
	 * @param car
	 *            The car to remove.
	 */
	abstract public void removeCarFromWash( Car car );

	/**
	 * Updates state variables such as queuing and idle times and then notifies
	 * observers.
	 * 
	 * @param event
	 *            The event that calls the method.
	 */
	abstract public void update( Event event );


	public class Car
	{
		private int id;


		Car( int id )
		{
			this.id = id;
		}

		/**
		 * @return Car id.
		 */
		public int getId()
		{
			return id;
		}
	}
}
