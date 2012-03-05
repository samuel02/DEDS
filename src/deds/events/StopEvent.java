package deds.events;


import deds.Event;
import deds.State;

public class StopEvent extends Event
{

	protected StopEvent( long time) {
		super("Stop", time);
	}
	
	public void execute(State s) {
		s.stopRunning();
	}
}
