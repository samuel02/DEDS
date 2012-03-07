package deds;

import deds.events.Event;

/**
 * 
 * @author Sarar
 * 
 * A DEDS-simulator which execute events on a SimState
 *
 */
public class Simulator {

	private State state;
	private EventQueue eventQueue;
	
	/**
	 * Creates an Simulator instance
	 * 
	 * @param s, a simulator state which the events will occur on.
	 */
	public Simulator(State s) {
		state = s;
		eventQueue = new EventQueue();
	}
	
	
	/**
	 * inserts an event into the simulator
	 * 
	 * @param e, event to insert
	 * 
	 */
	public void addEvent(Event e) {
		eventQueue.addEvent(e);
	}
	
	/**
	 * run's the simulator
	 */
	public void run() {
		do {
			Event nextEvent = eventQueue.removeNextEvent();
			
			if(nextEvent == null) {
				break;
			}
			
			state.beginEvent(nextEvent);
			nextEvent.execute(this,state);
			state.endEvent();
			
		}while (state.isRunning());
	}
}
