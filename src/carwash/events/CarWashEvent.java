package carwash.events;

import carwash.CarWashState;
import carwash.CarWashState.Car;
import deds.Event;
import deds.Simulator;
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

	public final void execute(Simulator sim,State s) {
		execute(sim,(CarWashState) s);
	}
	
	public Car getCar() {
		return car;
	}
	protected abstract void execute(Simulator sim,CarWashState s);
}
