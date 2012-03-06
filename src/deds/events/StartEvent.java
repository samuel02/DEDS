package deds.events;

/**
 * 
 * @author Sarar
 *
 * An event that start a simulation.
 */
public abstract class StartEvent extends Event {

	protected StartEvent() {
		super("Start", 0);
	}

}
