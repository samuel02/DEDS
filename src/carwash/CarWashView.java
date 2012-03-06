package carwash;

import java.util.Observable;
import deds.SimView;
import deds.events.*;
import carwash.events.*;
import carwash.CarWashState.Car;


/**
 * CarWashView extends SimView and is a view for the
 * Car Wash Simulator.
 * 
 * The view receives updates from the state and prints
 * results from via the update method.
 * 
 * @author samuelnilsson
 *
 */
public class CarWashView extends SimView{
	
	private final static String FORMATTER = "%-5s\t%-4s\t%-4s\t%-2s\t%-7s\t%-8s\t%-9s\t%-9s\t%-8s";
	private final static String NO_ID = "-";
	private final static int SEPARATOR_LENGTH = 95;
	
	public CarWashView() {
		super();
	}
	
	
	/**
	 * The update method receives info from the observed object,
	 * i.e. the state and prints info accordingly.
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		super.update(arg0, arg1);
		CarWashState state = (CarWashState) arg0;
		
		if(event instanceof StartCarWashEvent) {
			printHeader(state);
			printTableHeader();
			printTableRow(state);
		} else if (event instanceof StopEvent) {
			printTableRow(state);
			printEnd(state);
		} else {
			printTableRow(state);
		}
	}
	
	/**
	 * The method prints out the header for the print-out
	 * with some general info about the execution.
	 * 
	 * @param state
	 */
	private void printHeader(CarWashState state) {
		float[] fastDist = state.getFastDistribution();
		float[] slowDist = state.getSlowDistribution();
		
		System.out.println("Fast machines: " + state.getNumFastMachines());
		System.out.println("Slow machines: " + state.getNumSlowMachines());
		System.out.println("Fast distribution: (" + fastDist[0] + ", " + fastDist[1] + ")");
		System.out.println("Slow distribution: (" + slowDist[0] + ", " + slowDist[1] + ")");
		System.out.println("Exponential distribution with lambda = " + state.getExponentialDistribution());
		System.out.println("Seed = " + state.getSeed());
		System.out.println("Max Queue Size: " + state.getMaxQueueSize());
		printSeparator();
	}
	
	/**
	 * The method prints out the table header.
	 */
	private void printTableHeader() {
		System.out.println(String.format(FORMATTER, 
				"Time",
				"Fast",
				"Slow",
				"Id",
				"Event",
				"IdleTime",
				"QueueTime",
				"QueueSize",
				"Rejected"));
	}
	
	/**
	 * Prints out some general information after the simulation
	 * is done.
	 * 
	 * @param state
	 */
	private void printEnd(CarWashState state) {
		printSeparator();
		System.out.println("Total idle machine time:" + String.format("%.2f", state.getMachineIdleTime()));
		System.out.println("Total queueing time: " + String.format("%.2f", state.getQueueTime()));
		System.out.println("Mean queuing time: " + String.format("%.2f", state.getMeanQueueTime()));
		System.out.println("Rejected cars: " + state.getNumRejectedCars());
	}
	
	/**
	 * Method to print each row in the table with information about
	 * the the current event.
	 * 
	 * @param state
	 */
	private void printTableRow(CarWashState state) {
		
		int fast = state.getNumFastMachinesAvailable();
		int slow = state.getNumSlowMachinesAvailable();
		float idleTime = state.getMachineIdleTime();
		float queueTime = state.getQueueTime();
		int queueSize = state.getQueueSize();
		int rejected = state.getNumRejectedCars();
		
		float time = event.getTime();
		String id = NO_ID;
		
		try {
			CarWashEvent carWashEvent = (CarWashEvent) event;
			Car car = carWashEvent.getCar();
			id = ( car == null ? NO_ID : "" + car.getId());
		} catch( ClassCastException e ) {
			id = NO_ID;
		}
		
		System.out.println(String.format(FORMATTER, 
				String.format("%.2f", time),
				fast,
				slow,
				id,
				event,
				String.format("%.2f", idleTime),
				String.format("%.2f", queueTime),
				queueSize,
				rejected));
	}
	
	/**
	 * Prints a separator for the layout.
	 */
	private void printSeparator() {
		String separator = "-";
		for(int i = 0; i<SEPARATOR_LENGTH; i++) {
			separator += "-";
		}
		System.out.println(separator);
	}
}
