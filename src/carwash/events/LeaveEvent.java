package carwash.events;

import carwash.CarWashState;
import carwash.CarWashState.Car;
import deds.Simulator;

public class LeaveEvent extends CarWashEvent {
	
	protected LeaveEvent(float time, Car car) {
		super(car,"Leave", time);
	}
	@Override
	protected void execute(Simulator sim,CarWashState s) {
		
		s.removeCarFromWash(car);
		
		if(!s.isQueueEmpty()) {
			Car nextCar = s.pullCarFromQueue();
			float timeCarFinished = s.serveCar(nextCar);
			sim.addEvent(new LeaveEvent(timeCarFinished,nextCar));
		}
	}

}
