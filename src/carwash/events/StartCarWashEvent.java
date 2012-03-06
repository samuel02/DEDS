package carwash.events;

import carwash.CarWashState;
import carwash.CarWashState.Car;
import deds.Simulator;

/**
 * 
 * @author Sarar
 *
 */
public class StartCarWashEvent extends  CarWashEvent
{

	/**
	 * Creates an instance of this class
	 */
	public StartCarWashEvent() {
		super( "Start", 0);
	}

	
	
	//overrides javadoc too:)
	@Override
	public void execute(Simulator sim, CarWashState s) {
		Car newCar = s.createNewCar();
		sim.addEvent(new ArriveEvent(s.getNewArriveTime(),newCar));
		
	}

}
