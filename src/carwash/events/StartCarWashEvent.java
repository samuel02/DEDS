package carwash.events;

import carwash.CarWashState;
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
	 * Starts the simulation
	 * 
	 * @param sim, the simulator to place events in
	 * @param s, the state to start
	 */
	@Override
	public void execute(Simulator sim, State s) {
		CarWashState cws = (CarWashState) s;
		sim.addEvent(new ArriveEvent(cws.getNewArriveTime()));
	}

}
