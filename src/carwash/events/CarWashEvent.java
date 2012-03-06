package carwash.events;

import carwash.CarWashState;
import carwash.CarWashState.Car;
import deds.Simulator;
import deds.State;
import deds.events.Event;


/**
 * 
 * @author Sarar
 * 
 * A CarWashState specific event
 *
 */
public abstract class CarWashEvent extends Event{

	
	protected Car car;
	
	protected CarWashEvent(Car car, String name, float time) {
		super(name, time);
		this.car = car;
	}
	
	protected CarWashEvent(String name, float time) {
		this(null,name,time);
	}

	//overrides javadoc too:)
	@Override
	public final void execute(Simulator sim,State s) {
		execute(sim,(CarWashState) s);
	}
	
	/**
	 * 
	 * Same as super.execute(Simulator,State)
	 */
	protected abstract void execute(Simulator sim,CarWashState s);
	
	
	/**
	 * 
	 * @return the car associated to the event. null if none.
	 */
	public Car getCar() {
		return car;
	}
	
	
	
}
