package carwash;

import deds.Event;
import deds.State;

public abstract class CarWashEvent extends Event{

	
	
	protected CarWashEvent(String name, long time) {
		super(name, time);
	}

	public void execute(State s) {
		execute((CarWashState) s);
	}
	
	protected abstract void execute(CarWashState s);
}
