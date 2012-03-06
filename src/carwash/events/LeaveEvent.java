package carwash.events;

import carwash.CarWashState;
import carwash.CarWashState.Car;
import deds.Simulator;

/**
 * 
 * @author Sarar
 * 
 * A leave event doing some actions when its time for a car to leave a wash machine
 *
 */
public class LeaveEvent extends CarWashEvent {
	
	protected LeaveEvent(float time, Car car) {
		super(car,"Leave", time);
	}
	
	@Override
	protected void execute(Simulator sim,CarWashState s) {
		
		//removes car from wash machine
		s.removeCarFromWash(car);
		
		// if there is a car in queue, serve next car in queue.
		if(!s.isQueueEmpty()) {
			Car nextCar = s.pullCarFromQueue();
			float timeCarFinished = s.serveCar(nextCar);
			sim.addEvent(new LeaveEvent(timeCarFinished,nextCar));
		}
	}

}
