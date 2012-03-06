package deds.events;

import deds.Simulator;
import deds.State;

/**
 * 
 * @author Sarar
 *
 * A event which will be executed on a simulator
 * 
 */
public abstract class Event implements Comparable<Event>
{ 
	private final Float time;
	private final String name;
	
	/**
	 * 
	 * @param name of event
	 * @param time when the event should occur
	 */
	protected Event(String name,float time) {
		this.name = name;
		this.time = time;
	}
	
	
	//javadoc override
	@Override
	public int compareTo(Event e) {
		return time.compareTo(e.time);
	}
	
	/**
	 * 
	 * @return the time when event occurs
	 */
	public final float getTime() {
		return time;
	}
	
	
	/**
	 * 
	 * Executes the event one a simulator-state and trigger other events in simulator
	 * 
	 * @param sim, the simulator the event will add other events into
	 * @param s, the state on which the event will occur on
	 */
	public abstract void execute(Simulator sim,State s);
	
	
	/**
	 * 
	 * @return name of event
	 */
	public final String getName() {
		return name;
	}
	
	/**
	 * @return the name of the event
	 */
	public String toString() {
		return getName();
	}

}
