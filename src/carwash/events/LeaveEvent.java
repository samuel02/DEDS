package carwash.events;

import carwash.CarWashState;
import carwash.CarWashState.Car;
import deds.Simulator;

public class LeaveEvent extends CarWashEvent {
	
	protected LeaveEvent(long time, Car car) {
		super(car,"Leave", time);
	}
	@Override
	protected void execute(Simulator sim,CarWashState s) {
		s.beginEvent(this);
		s.removeCarFromWash(car);
		s.endEvent();
	}

}
