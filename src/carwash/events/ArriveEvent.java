package carwash.events;

import carwash.CarWashState;
import carwash.CarWashState.Car;
import deds.Simulator;

public class ArriveEvent extends CarWashEvent{
	
	protected ArriveEvent(long time, Car car) {
		super(car,"Arrive", time);
	}

	@Override
	protected void execute(Simulator sim,CarWashState s) {
		s.beginEvent(this);
		if(!s.isQueueFull()) {
			if(s.isQueueEmpty()) {
				s.serveCar(car);
			}
			else {
				s.placeCarInQueue(car);
			}
		}
		else {
			s.rejectCar();
		}
		s.endEvent();
		//TODO: add as lastEvent
		//TODO: create new car
		//TODO: create new ARRIVE and LEAVE EVENT
	}

}
