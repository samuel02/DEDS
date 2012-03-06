package deds;


import java.util.Observable;
import deds.events.Event;

/**
 * 
 * @author sebvik-0
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
	 * @return true if simulation is running
	 */
	public boolean isRunning()
	{
		return running;
	}

	/**
	 * Stop the simulation
	 */
	public void stopRunning()
	{
		running = false;
	}
	
	/**
	 * Call at the start of an event's execute() method.
	 * 
	 * @param event
	 *            The event that calls the method.
	 */
	public void beginEvent( Event event )
	{
		lastEvent = event;
	}
	
	/**
	 * Call at the end of an event's execute() method.
	 */
	public void endEvent()
	{
		setChanged();
		notifyObservers();
	}
	
	/**
	 * @return The last event to call the update() method.
	 */
	public Event getLastEvent()
	{
		return lastEvent;
	}
}
