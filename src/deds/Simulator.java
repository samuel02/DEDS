package deds;

public class Simulator {

	State state;
	EventQueue eventQueue;
	public void run() {
		do {
			Event next;
			state.beginEvent(nextEvent);
			nextEvent.execute(state);
			state.endEvent(nextEvent);
			
		}while (state.isRunning());
	}
}
