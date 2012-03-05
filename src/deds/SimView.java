package deds;

import java.util.Observer;
import java.util.Observable;

/**
 * Abstract class for view.
 * 
 * @author samuelnilsson
 *
 */
public abstract class SimView implements Observer{
	
	Event event;
	
	public SimView() {
	
	}
	
	public void update(Observable arg0, Object arg1) {
		State state = (State) arg0;
		event = state.lastEvent();
	}
	

}
