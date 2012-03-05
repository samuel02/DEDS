package deds;

public class Simulator {

	State state;
	EventQueue eventQueue;
	
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
