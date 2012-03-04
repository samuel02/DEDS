package carwash;


import java.util.Vector;

import deds.*;


/**
 * 
 * @author sebvik-0
 * 
 */
public class CarWashState extends State
{
	private final int   DEFAULT_NUM_FAST_MACHINES = 2;
	private final float DEFAULT_FAST_DISTR_MIN    = 2.8f;
	private final float DEFAULT_FAST_DISTR_MAX    = 4.6f;

	private final int   DEFAULT_NUM_SLOW_MACHINES = 2;
	private final float DEFAULT_SLOW_DISTR_MIN    = 3.5f;
	private final float DEFAULT_SLOW_DISTR_MAX    = 6.7f;
	
	private final float DEFAULT_EXP_DISTRI_LAMBDA = 2.0f;
	
	private final int   DEFAULT_SEED              = 1234;
	
	private final int   DEFAULT_MAX_QUEUE_SIZE    = 5;

	private int   nFastMachines;
	private int   nFastAvalible;
	private float fastDistrMin;
	private float fastDistrMax;

	private int   nSlowMachines;
	private int   nSlowAvalible;
	private float slowDistrMin;
	private float slowDistrMax;
	
	private float expDistrLambda;
	
	private int   seed;
	
	private int   maxQueueSize;
	private FIFO<Car> carQueue;
	
	private float machineIdleTime;
	private float queueTime;
	
	private int   nCars;
	private int   nRejectedCars;
	private int   nextCarId;
	
	private Event lastEvent;


	public CarWashState()
	{
		nFastMachines = DEFAULT_NUM_FAST_MACHINES;
		nFastAvalible = nFastMachines;
		fastDistrMin  = DEFAULT_FAST_DISTR_MIN;
		fastDistrMax  = DEFAULT_FAST_DISTR_MAX;

		nSlowMachines = DEFAULT_NUM_SLOW_MACHINES;
		nSlowAvalible = nSlowMachines;
		slowDistrMin  = DEFAULT_SLOW_DISTR_MIN;
		slowDistrMax  = DEFAULT_SLOW_DISTR_MAX;
		
		expDistrLambda = DEFAULT_EXP_DISTRI_LAMBDA;
		
		seed = DEFAULT_SEED;
		
		maxQueueSize = DEFAULT_MAX_QUEUE_SIZE;
		carQueue     = new FIFO<Car>();
		
		machineIdleTime = 0;
		queueTime       = 0;
		
		nCars         = 0;
		nRejectedCars = 0;
		nextCarId     = 0;
		
		lastEvent = null;
	}

	/**
	 * @return Number of fast washing machines.
	 */
	public int getNumFastMachines()
	{
		return nFastMachines;
	}

	/**
	 * @return Number of fast washing machines currently available.
	 */
	public int getNumFastMachinesAvailable()
	{
		return nFastAvalible;
	}

	/**
	 * @return Number of slow washing machines.
	 */
	public int getNumSlowMachines()
	{
		return nSlowMachines;
	}

	/**
	 * @return Number of slow washing machines currently available.
	 */
	public int getNumSlowMachinesAvailable()
	{
		return nSlowAvalible;
	}

	/**
	 * @return true if there are one or more machines available (of any kind.)
	 */
	public boolean hasVacantMachines()
	{
		return nFastAvalible + nSlowAvalible > 0;
	}

	/**
	 * @return Array containing min and max distribution values for fast washing
	 *         machines.
	 */
	public float[] getFastDistribution()
	{
		float distr[] = new float[2];
		distr[0] = fastDistrMin;
		distr[1] = fastDistrMax;
		return distr;
	}

	/**
	 * @return Array containing min and max distribution values for slow washing
	 *         machines.
	 */
	public float[] getSlowDistribution()
	{
		float distr[] = new float[2];
		distr[0] = slowDistrMin;
		distr[1] = slowDistrMax;
		return distr;
	}

	/**
	 * @return Exponential distribution lambda value.
	 */
	public float getExponentialDistribution()
	{
		return expDistrLambda;
	}

	/**
	 * @return Seed used by the state for random operations.
	 */
	public int getSeed()
	{
		return seed;
	}

	/**
	 * @return Maximum car queue size.
	 */
	public int getMaxQueueSize()
	{
		return maxQueueSize;
	}

	/**
	 * @return Current length of the car queue.
	 */
	public int getQueueSize()
	{
		return carQueue.size();
	}

	/**
	 * @return true if the car queue is empty.
	 */
	public boolean isQueueEmpty()
	{
		return getQueueSize() == 0;
	}

	/**
	 * @return true if the car queue is full.
	 */
	public boolean isQueueFull()
	{
		return getQueueSize() == maxQueueSize;
	}

	/**
	 * @return Total time the washing machines have been idle.
	 */
	public float getMachineIdleTime()
	{
		return machineIdleTime;
	}

	/**
	 * @return Total time cars have spent in the queue.
	 */
	public float getQueueTime()
	{
		return queueTime;
	}
	
	/**
	 * @return Mean queuing time.
	 */
	public float getMeanQueueTime()
	{
		return queueTime / nCars;
	}

	/**
	 * @return Total number of cars rejected.
	 */
	public int getNumRejectedCars()
	{
		return nRejectedCars;
	}

	/**
	 * Creates a new car.
	 * 
	 * @return The new car.
	 */
	public Car createNewCar()
	{
		nCars++;
		return new Car( nextCarId++ );
	}

	/**
	 * Rejects a car, this method should be called if the car queue is full.
	 */
	public void rejectCar()
	{
		nRejectedCars++;
		nCars--;
	}
	
	/**
	 * @param car
	 *            The car to serve.
	 */
	public void serveCar( Car car )
	{
		if ( car.getWMType() == WashingMachineType.NONE )
		{
			if ( nFastAvalible > 0 )
			{
				car.setWMType( WashingMachineType.FAST );
				nFastAvalible--;
			}
			else if ( nSlowAvalible > 0 )
			{
				car.setWMType( WashingMachineType.SLOW );
				nSlowAvalible--;
			}
			else
			{
				// TODO: Throw an exception!!
			}
		}
		else
		{
			// TODO: Throw an exception!!
		}
	}

	/**
	 * Places a car at the back of the car queue.
	 * 
	 * @param car
	 *            The car to place in the queue.
	 */
	public void placeCarInQueue( Car car )
	{
		if ( isQueueFull() )
		{
			// TODO: Throw an exception!!
		}
		
		carQueue.pushBack( car );
	}

	/**
	 * Pulls the car at the front from the car queue.
	 * 
	 * @return The pulled car.
	 */
	public Car pullCarFromQueue()
	{
		if ( isQueueEmpty() )
		{
			// TODO: Throw an exception!!
		}
		
		return carQueue.popFront();
	}

	/**
	 * Removes a car from a washing machine.
	 * 
	 * @param car
	 *            The car to remove.
	 */
	public void removeCarFromWash( Car car )
	{
		switch ( car.getWMType() )
		{
		case NONE:
			// TODO: Throw an exception!!
			break;
		case FAST:
			nFastAvalible++;
			break;
		case SLOW:
			nSlowAvalible++;
			break;
		}
		
		car.setWMType( WashingMachineType.NONE );
	}

	/**
	 * @return The last event to call the update() method.
	 */
	public Event getLastEvent()
	{
		return lastEvent;
	}
	
	/**
	 * Updates queuing and idle times and then notifies observers.
	 * 
	 * @param event
	 *            The event that calls the method.
	 */
	public void update( Event event )
	{
		float dt = event.getTime() - lastEvent.getTime();
		
		machineIdleTime += ( nFastAvalible + nSlowAvalible ) * dt;
		queueTime       += getQueueSize() * dt;
		
		lastEvent = event;
		
		setChanged();
		notifyObservers();
	}

	
	private enum WashingMachineType
	{
		NONE,
		FAST,
		SLOW,
	}
	

	public class Car
	{
		private int id;
		private WashingMachineType wmType;


		Car( int id )
		{
			this.id = id;
			wmType = WashingMachineType.NONE;
		}
		
		WashingMachineType getWMType()
		{
			return wmType;
		}
		
		void setWMType( WashingMachineType wmType )
		{
			this.wmType = wmType;
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


class FIFO<T>
{
	private Vector<T> vector;
	
	
	public FIFO()
	{
		vector = new Vector<T>();
	}
	
	/**
	 * @return The number of elements in the FIFO.
	 */
	public int size()
	{
		return vector.size();
	}
	
	/**
	 * @param item
	 *             Adds an item to the back of the queue.
	 */
	public void pushBack( T item )
	{
		vector.add( item );
	}
	
	/**
	 * Removes the item at the front of the queue.
	 * 
	 * @return The item that was removed.
	 */
	public T popFront()
	{
		return vector.remove(0);
	}
}
