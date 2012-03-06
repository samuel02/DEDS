package carwash;


import java.util.Vector;

import carwash.randomstreams.*;

import deds.*;
import deds.events.Event;


/**
 * 
 * @author sebvik-0
 * 
 */
public class CarWashState extends State
{
	private final int   DEFAULT_SEED              = 1234;
	
	private final int   DEFAULT_NUM_FAST_MACHINES = 2;
	private final float DEFAULT_FAST_DISTR_MIN    = 2.8f;
	private final float DEFAULT_FAST_DISTR_MAX    = 4.6f;

	private final int   DEFAULT_NUM_SLOW_MACHINES = 2;
	private final float DEFAULT_SLOW_DISTR_MIN    = 3.5f;
	private final float DEFAULT_SLOW_DISTR_MAX    = 6.7f;
	
	private final float DEFAULT_EXP_DISTRI_LAMBDA = 2.0f;

	private final int   DEFAULT_MAX_QUEUE_SIZE    = 5;

	private int   seed;
	
	private int   nFastMachines;
	private int   nFastAvalible;
	private float fastDistrMin;
	private float fastDistrMax;
	private UniformRandomStream fastRandStream;

	private int   nSlowMachines;
	private int   nSlowAvalible;
	private float slowDistrMin;
	private float slowDistrMax;
	private UniformRandomStream slowRandStream;
	
	private float expDistrLambda;
	private ExponentialRandomStream expRandStream;
	
	private int   maxQueueSize;
	private FIFO<Car> carQueue;
	
	private float machineIdleTime;
	private float queueTime;
	
	private int   nCars;
	private int   nRejectedCars;
	private int   nextCarId;


	public CarWashState()
	{
		seed = DEFAULT_SEED;
		
		nFastMachines  = DEFAULT_NUM_FAST_MACHINES;
		nFastAvalible  = nFastMachines;
		fastDistrMin   = DEFAULT_FAST_DISTR_MIN;
		fastDistrMax   = DEFAULT_FAST_DISTR_MAX;
		fastRandStream = new UniformRandomStream( fastDistrMin, fastDistrMax, seed );
		
		nSlowMachines = DEFAULT_NUM_SLOW_MACHINES;
		nSlowAvalible = nSlowMachines;
		slowDistrMin  = DEFAULT_SLOW_DISTR_MIN;
		slowDistrMax  = DEFAULT_SLOW_DISTR_MAX;
		slowRandStream = new UniformRandomStream( slowDistrMin, slowDistrMax, seed );
		
		expDistrLambda = DEFAULT_EXP_DISTRI_LAMBDA;
		expRandStream = new ExponentialRandomStream( expDistrLambda, seed );
		
		maxQueueSize = DEFAULT_MAX_QUEUE_SIZE;
		carQueue     = new FIFO<Car>();
		
		machineIdleTime = 0;
		queueTime       = 0;
		
		nCars         = 0;
		nRejectedCars = 0;
		nextCarId     = 0;
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
	 * Generates the time of the next arrival.
	 * 
	 * @return Time of the next arrival.
	 */
	public float getNewArriveTime()
	{
		return getLastEvent().getTime() + (float) expRandStream.next();
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
	 *            
	 * @return The time when the wash is done.
	 */
	public float serveCar( Car car )
	{
		float time = getLastEvent().getTime();
		
		if ( car.getWMType() == WashingMachineType.NONE )
		{
			if ( nFastAvalible > 0 )
			{
				car.setWMType( WashingMachineType.FAST );
				nFastAvalible--;
				time += (float) fastRandStream.next();
			}
			else if ( nSlowAvalible > 0 )
			{
				car.setWMType( WashingMachineType.SLOW );
				nSlowAvalible--;
				time += (float) slowRandStream.next();
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
		
		return time;
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
	 * Call at the start of an event's execute() method.
	 * 
	 * @param event
	 *            The event that calls the method.
	 */
	@Override
	public void beginEvent( Event event )
	{
		Event lastEvent = getLastEvent();
		
		float dt;
		
		if ( lastEvent == null )
		{
			dt = event.getTime();
		}
		else
		{
			dt = event.getTime() - lastEvent.getTime();
		}
		
		machineIdleTime += ( nFastAvalible + nSlowAvalible ) * dt;
		queueTime       += getQueueSize() * dt;
		
		super.beginEvent( event );
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
	 * Removes the item at the front of the queue and returns it.
	 * 
	 * @return The item that was removed.
	 */
	public T popFront()
	{
		return vector.remove(0);
	}
}
