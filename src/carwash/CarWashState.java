package carwash;

import java.util.Vector;

import carwash.randomstreams.*;

import deds.*;
import deds.events.Event;

/**
 * The state of a car wash for a car wash simulation.
 * 
 * @author Sebastian
 * 
 */
public class CarWashState extends State {
	private final int DEFAULT_SEED = 1234;

	private final int DEFAULT_NUM_FAST_MACHINES = 3;
	private final float DEFAULT_FAST_DISTR_MIN = 2.0f;
	private final float DEFAULT_FAST_DISTR_MAX = 4.5f;

	private final int DEFAULT_NUM_SLOW_MACHINES = 3;
	private final float DEFAULT_SLOW_DISTR_MIN = 4.1f;
	private final float DEFAULT_SLOW_DISTR_MAX = 6.7f;

	private final float DEFAULT_EXP_DISTRI_LAMBDA = 2.5f;

	private final int DEFAULT_MAX_QUEUE_SIZE = 3;

	private int seed;

	private int nFastMachines;
	private int nFastAvalible;
	private float fastDistrMin;
	private float fastDistrMax;
	private UniformRandomStream fastRandStream;

	private int nSlowMachines;
	private int nSlowAvalible;
	private float slowDistrMin;
	private float slowDistrMax;
	private UniformRandomStream slowRandStream;

	private float expDistrLambda;
	private ExponentialRandomStream expRandStream;

	private int maxQueueSize;
	private FIFO<Car> carQueue;

	private float machineIdleTime;
	private float queueTime;

	private int nCars;
	private int nRejectedCars;
	private int nextCarId;

	public CarWashState() {
		seed = DEFAULT_SEED;

		nFastMachines = DEFAULT_NUM_FAST_MACHINES;
		nFastAvalible = nFastMachines;
		fastDistrMin = DEFAULT_FAST_DISTR_MIN;
		fastDistrMax = DEFAULT_FAST_DISTR_MAX;
		fastRandStream = new UniformRandomStream(fastDistrMin, fastDistrMax,
				seed);

		nSlowMachines = DEFAULT_NUM_SLOW_MACHINES;
		nSlowAvalible = nSlowMachines;
		slowDistrMin = DEFAULT_SLOW_DISTR_MIN;
		slowDistrMax = DEFAULT_SLOW_DISTR_MAX;
		slowRandStream = new UniformRandomStream(slowDistrMin, slowDistrMax,
				seed);

		expDistrLambda = DEFAULT_EXP_DISTRI_LAMBDA;
		expRandStream = new ExponentialRandomStream(expDistrLambda, seed);

		maxQueueSize = DEFAULT_MAX_QUEUE_SIZE;
		carQueue = new FIFO<Car>();

		machineIdleTime = 0;
		queueTime = 0;

		nCars = 0;
		nRejectedCars = 0;
		nextCarId = 0;
	}

	/**
	 * Gets the number of fast washing machines.
	 * 
	 * @return Number of fast washing machines.
	 */
	public int getNumFastMachines() {
		return nFastMachines;
	}

	/**
	 * Gets the number of fast washing machines available.
	 * 
	 * @return Number of fast washing machines currently available.
	 */
	public int getNumFastMachinesAvailable() {
		return nFastAvalible;
	}

	/**
	 * Gets the number of slow washing machines.
	 * 
	 * @return Number of slow washing machines.
	 */
	public int getNumSlowMachines() {
		return nSlowMachines;
	}

	/**
	 * Gets the number of slow washing machines available.
	 * 
	 * @return Number of slow washing machines currently available.
	 */
	public int getNumSlowMachinesAvailable() {
		return nSlowAvalible;
	}

	/**
	 * Checks if there are any vacant washing machines.
	 * 
	 * @return true if there are one or more machines available (of any kind.)
	 */
	public boolean hasVacantMachines() {
		return nFastAvalible + nSlowAvalible > 0;
	}

	/**
	 * Gets the minimum and maximum distribution values for fast washing
	 * machines.
	 * 
	 * @return Array containing min and max distribution values.
	 */
	public float[] getFastDistribution() {
		float distr[] = new float[2];
		distr[0] = fastDistrMin;
		distr[1] = fastDistrMax;
		return distr;
	}

	/**
	 * Gets the minimum and maximum distribution values for slow washing
	 * machines.
	 * 
	 * @return Array containing min and max distribution values.
	 */
	public float[] getSlowDistribution() {
		float distr[] = new float[2];
		distr[0] = slowDistrMin;
		distr[1] = slowDistrMax;
		return distr;
	}

	/**
	 * Gets the exponential distribution lambda value.
	 * 
	 * @return Exponential distribution lambda value.
	 */
	public float getExponentialDistribution() {
		return expDistrLambda;
	}

	/**
	 * Gets the seed used by the state for random operations.
	 * 
	 * @return Seed number.
	 */
	public int getSeed() {
		return seed;
	}

	/**
	 * Generates the time of the next arrival.
	 * 
	 * @return Time of the next arrival.
	 */
	public float getNewArriveTime() {
		return getLastEvent().getTime() + (float) expRandStream.next();
	}

	/**
	 * Gets the maximum car queue size.
	 * 
	 * @return Maximum car queue size.
	 */
	public int getMaxQueueSize() {
		return maxQueueSize;
	}

	/**
	 * Gets the current length of the car queue.
	 * 
	 * @return Current length of the car queue.
	 */
	public int getQueueSize() {
		return carQueue.size();
	}

	/**
	 * Checks if the car queue is empty.
	 * 
	 * @return true if the car queue is empty.
	 */
	public boolean isQueueEmpty() {
		return getQueueSize() == 0;
	}

	/**
	 * Checks if the car queue is full.
	 * 
	 * @return true if the car queue is full.
	 */
	public boolean isQueueFull() {
		return getQueueSize() == maxQueueSize;
	}

	/**
	 * Gets the total time the washing machines have been idle.
	 * 
	 * @return Idle time.
	 */
	public float getMachineIdleTime() {
		return machineIdleTime;
	}

	/**
	 * Gets the total time cars have spent in the queue.
	 * 
	 * @return Queuing time.
	 */
	public float getQueueTime() {
		return queueTime;
	}

	/**
	 * Gets the mean queuing time.
	 * 
	 * @return Mean queuing time.
	 */
	public float getMeanQueueTime() {
		return queueTime / nCars;
	}
	
	public int getNumCars() {
		return nCars;
	}

	/**
	 * Gets the total number of rejected cars.
	 * 
	 * @return Number of cars rejected.
	 */
	public int getNumRejectedCars() {
		return nRejectedCars;
	}

	/**
	 * Creates a new car.
	 * 
	 * @return The new car.
	 */
	public Car createNewCar() {
		nCars++;
		return new Car(nextCarId++);
	}

	/**
	 * Rejects a car, this method should be called if the car queue is full.
	 * 
	 * @param car
	 *            The car to be rejected.
	 * 
	 * @throws CarNotReadyException
	 *             if car is in the queue, in a wash, is finished or has been
	 *             rejected.
	 */
	public void rejectCar(Car car) {
		if (car.getState() != CarState.READY) {
			throw new CarNotReadyException();
		}

		car.setState(CarState.REJECTED);

		nRejectedCars++;
		nCars--;
	}

	/**
	 * @param car
	 *            The car to serve.
	 * 
	 * @return The time when the wash is done.
	 * 
	 * @throws CarNotReadyException
	 *             if car is in the queue, in a wash, is finished or has been
	 *             rejected.
	 * 
	 * @throws NoAvalibleWashingMachinesException
	 *             if there are no available washing machines.
	 */
	public float serveCar(Car car) {
		if (car.getState() != CarState.READY) {
			throw new CarNotReadyException();
		}

		float time = getLastEvent().getTime();

		if (nFastAvalible > 0) {
			car.setState(CarState.IN_FAST_WASH);
			nFastAvalible--;
			time += (float) fastRandStream.next();
		} else if (nSlowAvalible > 0) {
			car.setState(CarState.IN_SLOW_WASH);
			nSlowAvalible--;
			time += (float) slowRandStream.next();
		} else {
			throw new NoAvalibleWashingMachinesException();
		}

		return time;
	}

	/**
	 * Places a car at the back of the car queue.
	 * 
	 * @param car
	 *            The car to place in the queue.
	 * 
	 * @throws CarNotReadyException
	 *             if car is in the queue, in a wash, is finished or has been
	 *             rejected.
	 * 
	 * @throws CarQueueFullException
	 *             if the car queue is full.
	 */
	public void placeCarInQueue(Car car) {
		if (car.getState() != CarState.READY) {
			throw new CarNotReadyException();
		}

		if (isQueueFull()) {
			throw new CarQueueFullException();
		}

		car.setState(CarState.QUEUED);
		carQueue.pushBack(car);
	}

	/**
	 * Pulls the car at the front from the car queue.
	 * 
	 * @return The pulled car.
	 * 
	 * @throws CarQueueEmptyException
	 *             if the car queue is empty.
	 */
	public Car pullCarFromQueue() {
		if (isQueueEmpty()) {
			throw new CarQueueEmptyException();
		}

		Car car = carQueue.popFront();
		car.setState(CarState.READY);

		return car;
	}

	/**
	 * Removes a car from a washing machine.
	 * 
	 * @param car
	 *            The car to remove.
	 * 
	 * @throws CarNotInWashException
	 *             if the car is not in a wash.
	 */
	public void removeCarFromWash(Car car) {
		switch (car.getState()) {
		case IN_FAST_WASH:
			nFastAvalible++;
			break;
		case IN_SLOW_WASH:
			nSlowAvalible++;
			break;
		default:
			throw new CarNotInWashException();
		}

		car.setState(CarState.FINISHED);
	}

	@Override
	public void beginEvent(Event event) {
		Event lastEvent = getLastEvent();

		float dt;

		if (lastEvent == null) {
			dt = event.getTime();
		} else {
			dt = event.getTime() - lastEvent.getTime();
		}

		machineIdleTime += (nFastAvalible + nSlowAvalible) * dt;
		queueTime += getQueueSize() * dt;

		super.beginEvent(event);
	}

	private enum CarState {
		READY, REJECTED, QUEUED, IN_FAST_WASH, IN_SLOW_WASH, FINISHED,
	}

	/**
	 * A class holding information about a car used by the car wash simulation.
	 * 
	 * @author Sebastian
	 * 
	 */
	public class Car {
		private int id;
		private CarState state;

		private Car(int id) {
			this.id = id;
			state = CarState.READY;
		}

		private CarState getState() {
			return state;
		}

		private void setState(CarState state) {
			this.state = state;
		}

		/**
		 * Gets the id of the car.
		 * 
		 * @return Car id.
		 */
		public int getId() {
			return id;
		}
	}

	public class NoAvalibleWashingMachinesException extends RuntimeException {

		private static final long serialVersionUID = 7982650438299570762L;
	}

	public class CarQueueEmptyException extends RuntimeException {

		private static final long serialVersionUID = -3662676523771613893L;
	}

	public class CarQueueFullException extends RuntimeException {

		private static final long serialVersionUID = -8352726256107153636L;
	}

	public class CarNotReadyException extends RuntimeException {

		private static final long serialVersionUID = 5823079140587725283L;
	}

	public class CarNotInWashException extends RuntimeException {

		private static final long serialVersionUID = -3732622100738630197L;
	}
}

/**
 * A first in first out queue implementation.
 * 
 * @author Sebastian
 * 
 * @param <T>
 */
class FIFO<T> {
	private Vector<T> vector;

	public FIFO() {
		vector = new Vector<T>();
	}

	/**
	 * Gets the size of the queue.
	 * 
	 * @return The number of elements in the FIFO.
	 */
	public int size() {
		return vector.size();
	}

	/**
	 * Adds an item to the back of the queue.
	 * 
	 * @param item
	 *            The item to be added.
	 */
	public void pushBack(T item) {
		vector.add(item);
	}

	/**
	 * Removes the item at the front of the queue and returns it.
	 * 
	 * @return The item that was removed.
	 */
	public T popFront() {
		return vector.remove(0);
	}
}
