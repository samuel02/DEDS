package carwash.events;

import carwash.CarWashState;
import carwash.CarWashState.Car;
import deds.Simulator;
import deds.State;
import deds.events.StartEvent;

/**
 * 
 * @author Sarar
 *
 */
public class StartCarWashEvent extends  StartEvent
{

	/**
	 * Creates an instance of this class
	 */
	public StartCarWashEvent() {
	}

	
	
	//overrides javadoc too:)
	@Override
	public void execute(Simulator sim, State s) {
		CarWashState cws = (CarWashState) s;
		Car newCar = cws.createNewCar();
		sim.addEvent(new ArriveEvent(cws.getNewArriveTime(),newCar));
		
	}

}
