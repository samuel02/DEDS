package carwash;

import carwash.CarWashState.Car;

public class ArriveEvent extends CarWashEvent{
	
	protected ArriveEvent(long time, Car car) {
		super(car,"Arrive", time);
	}

	@Override
	protected void execute(CarWashState s) {
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
