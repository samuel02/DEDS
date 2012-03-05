package carwash.events;

import carwash.CarWashState;
import carwash.CarWashState.Car;
import deds.Event;
import deds.State;

public abstract class CarWashEvent extends Event{

	
	protected Car car;
	protected CarWashEvent(Car car, String name, long time) {
		super(name, time);
		this.car = car;
	}
	
	protected CarWashEvent(String name, long time) {
		this(null,name,time);
	}

	public void execute(State s) {
		execute((CarWashState) s);
	}
	
	public Car getCar() {
		return car;
	}
	protected abstract void execute(CarWashState s);
}
