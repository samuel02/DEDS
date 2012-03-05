package carwash.events;

import carwash.CarWashState;
import carwash.CarWashState.Car;
import deds.Simulator;
import deds.State;
import deds.events.StartEvent;

public class StartCarWashEvent extends  CarWashEvent
{

	protected StartCarWashEvent() {
		super( "Start", 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Simulator sim, CarWashState s) {
		Car newCar = s.createNewCar();
		sim.addEvent(new ArriveEvent(s.getNewArrivalTime(),newCar));
		
	}

}
