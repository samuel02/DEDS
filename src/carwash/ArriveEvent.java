package carwash;

import carwash.CarWashState.Car;

public class ArriveEvent extends CarWashEvent{
	
	Car car;
	protected ArriveEvent(long time, Car car) {
		super("Arrive", time);
		this.car = car;
	}

	@Override
	protected void execute(CarWashState s) {
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
		//TODO: add as lastEvent
		//TODO: create new car
		//TODO: create new ARRIVE and LEAVE EVENT
	}

}
