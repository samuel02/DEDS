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
	 * @param time when this event should occur
	 */
	public StopEvent( long time) {
		super("Stop", time);
	}
	
	//overrides javadoc ^^
	@Override
	public void execute(Simulator sim,State s) {
		s.stopRunning();
	}
}
