package deds;


import java.util.Observable;
import deds.events.Event;

/**
 * An abstract simulator state.
 * 
 * @author Sebastian
 * 
 */
abstract public class State extends Observable
{
	private boolean running;
	private Event   lastEvent;


	public State()
	{
		running   = true;
		lastEvent = null;
	}

	/**
	 * Checks if the current simulation is still running.
	 * 
	 * @return true if simulation is running
	 */
	public boolean isRunning()
	{
		return running;
	}

	/**
	 * Stops the simulation.
	 */
	public void stopRunning()
	{
		running = false;
	}
	
	/**
	 * Called by the simulator prior to calling an event's execute() method.
	 * 
	 * @param event
	 *            The event about to be executed.
	 */
	public void beginEvent( Event event )
	{
		lastEvent = event;
	}
	
	/**
	 * Called by the simulator after a call to an event's execute() method.
	 */
	public void endEvent()
	{
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Gets the last event passed to beginEvent().
	 * 
	 * @return The last event.
	 */
	public Event getLastEvent()
	{
		return lastEvent;
	}
}
