package deds.events;


import deds.Simulator;
import deds.State;

public class StopEvent extends Event
{

	protected StopEvent( long time) {
		super("Stop", time);
	}
	
	public void execute(Simulator sim,State s) {
		s.stopRunning();
	}
}
