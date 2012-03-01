package deds;


import java.util.Observable;


/**
 * 
 * @author sebvik-0
 * 
 */
abstract public class State extends Observable
{
	private boolean running;


	public State()
	{
		running = true;
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
}
