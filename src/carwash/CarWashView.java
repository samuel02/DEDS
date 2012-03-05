package carwash;

import java.util.Observable;
import deds.SimView;
import carwash.events.CarWashEvent;

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
	
	final String formatter;
	
	/**
	 * Constructor creates a formatter that is used for printing results.
	 */
	public CarWashView() {
		super();
		formatter = "%-5s\t%-4s\t%-4s\t%-2s\t%-7s\t%-8s\t%-9s\t%-9s\t%-8s";
	}
	
	
	/**
	 * The update method receives info from the observed object,
	 * i.e. the state and prints info accordingly.
	 */
	public void update(Observable arg0, Object arg1) {
		super.update(arg0, arg1);
		CarWashState state = (CarWashState) arg0;
		
		if(event.toString() == "Start") {
			printHeader(state);
			printTableHeader();
			printTableRow(state);
		} else if (event.toString() == "Stop") {
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
		System.out.println("Fast machines: "
							+ state.getNumFastMachines());
		System.out.println("Slow machines: " 
							+ state.getNumSlowMachines());
		System.out.println("Fast distribution: " 
							+ state.getFastDistribution());
		System.out.println("Slow distribution: " 
							+ state.getSlowDistribution());
		System.out.println("Exponential distribution with lambda = " 
							+ state.getExponentialDistribution());
		System.out.println("Seed = " 
							+ state.getSeed());
		System.out.println("Max Queue Size: " 
							+ state.getMaxQueueSize());
		printSeparator();
	}
	
	/**
	 * The method prints out the table header.
	 */
	private void printTableHeader() {
		System.out.println(String.format(formatter, 
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
		System.out.println("Total idle machine time:" + state.getMachineIdleTime());
		System.out.println("Total queueing time: " + state.getQueueTime());
		System.out.println("Mean queuing time: " + state.getMeanQueueTime());
		System.out.println("Rejected cars: " + state.getNumRejectedCars());
	}
	
	/**
	 * Method to print each row in the table with information about
	 * the the current event.
	 * 
	 * @param state
	 */
	private void printTableRow(CarWashState state) {
		float time = ((CarWashEvent) event).getTime();
		int fast = state.getNumFastMachinesAvailable();
		int slow = state.getNumSlowMachinesAvailable();
		int id = ((CarWashEvent) event).getCar().getId();
		float idleTime = state.getMachineIdleTime();
		float queueTime = state.getQueueTime();
		int queueSize = state.getQueueSize();
		int rejected = state.getNumRejectedCars();
		
		System.out.println(String.format(formatter, 
				String.format("%.2f", time),
				fast,
				slow,
				id,
				event,
				idleTime,
				queueTime,
				queueSize,
				rejected));
		
	}
	
	/**
	 * Prints a seperator for the layout.
	 */
	private void printSeparator() {
		System.out.println("-------------------------------------");
	}
}
