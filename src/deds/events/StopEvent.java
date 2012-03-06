package deds.events;


import deds.Simulator;
import deds.State;

/**
 * 
 * @author Sarar
 *
 * An stop event that stops simulator
 */
public class StopEvent extends Event
{

	/**
	 * 
	 *  Creates a stop event
	 *  
	 * @param time when this event should occur
	 */
	public StopEvent( float time) {
		super("Stop", time);
	}
	
	/**
	 * 
	 * Stops the simulator
	 * @param sim, the simulator to place events in. Not used in this case.
	 * @param s, the state to stop
	 */
	@Override
	public void execute(Simulator sim,State s) {
		s.stopRunning();
	}
}
