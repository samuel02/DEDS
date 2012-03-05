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
		if(!s.isQueueFull()) {
			if(s.isQueueEmpty() && s.hasVacantMachines() ) {
				float timeCarFinished = s.serveCar(car);
				sim.addEvent(new LeaveEvent(timeCarFinished,car));
			}
			else {
				s.placeCarInQueue(car);
			}
		}
		else {
			s.rejectCar();
		}
		Car newCar = s.createNewCar();
		sim.addEvent(new ArriveEvent(s.getNewArrivalTime(),newCar));
	}

}
