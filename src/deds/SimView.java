package deds;

import java.util.Observer;
import java.util.Observable;
import deds.events.*;

/**
 * Abstract class for view implements Observer.
 * 
 * The class has reference to the current event
 * by getting it from the state.
 * 
 * @author samuelnilsson
 *
 */
public abstract class SimView {
	protected State s;
	private SimStateListener stateChangedListener;
	
	public SimView(State s) {
		this.s = s;
		stateChangedListener = new SimStateListener();
		s.addObserver(stateChangedListener);
		
	}
	
	
	
	
	
	/**
	 * Method to show some information about the simulator state before the simulation
	 * 
	 */
	protected abstract void showPreData();
	
	
	/**
	 * 
	 * Method to show information when an event occurred
	 * 
	 * @param e, event which occurred
	 */
	protected abstract void showEventOccuredData(Event e);
	
	/**
	 * Method to show some information after the simulation
	 * is done.
	 * 
	 */
	protected abstract void showPostData();
	
	
	
	
	/**
	 * Listens for changes in state and calls necessary methods when an event occurred
	 *
	 */
	private class SimStateListener implements Observer {
		@Override
		
		public void update(Observable arg0, Object arg1) {
			State state = (State) arg0;
			Event event = state.getLastEvent();
			
			if(event.getClass() == StartEvent.class) {
				showPreData();
			}
			showEventOccuredData(event);
			
			if(event.getClass() == StopEvent.class) {
				showPostData();
			}
	}
	}
	

}
