package deds;

import java.util.Observer;
import java.util.Observable;

/**
 * Abstract class for view implements Observer.
 * 
 * The class has reference to the current event
 * by getting it from the state.
 * 
 * @author samuelnilsson
 *
 */
public abstract class SimView implements Observer{
	
	protected Event event;
	
	public SimView() {
	
	}
	
	public void update(Observable arg0, Object arg1) {
		State state = (State) arg0;
		event = state.getLastEvent();
	}
	

}
