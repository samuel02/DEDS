package deds;

import deds.events.Event;

public class Simulator {

	State state;
	EventQueue eventQueue;
	
	public Simulator(State s) {
		state = s;
		eventQueue = new EventQueue();
	}
	
	public void addEvent(Event e) {
		eventQueue.addEvent(e);
	}
	public void run() {
		do {
			Event nextEvent = eventQueue.getNextEvent();
			if(nextEvent != null) {
				state.beginEvent(nextEvent);
				nextEvent.execute(this,state);
				state.endEvent();
			}
			
		}while (state.isRunning());
	}
}
