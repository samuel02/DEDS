package carwash.events;

import carwash.CarWashState;
import carwash.CarWashState.Car;
import deds.Simulator;
import deds.State;
import deds.events.Event;

public abstract class CarWashEvent extends Event{

	
	protected Car car;
	protected CarWashEvent(Car car, String name, float time) {
		super(name, time);
		this.car = car;
	}
	
	protected CarWashEvent(String name, float time) {
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
