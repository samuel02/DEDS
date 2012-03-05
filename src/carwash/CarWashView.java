package carwash;

import java.util.Observable;

/**
 * CarWashView
 * 
 * @author samuelnilsson
 *
 */
public class CarWashView extends deds.SimView{
	
	final String formatter;
	
	public CarWashView() {
		super();
		formatter = "%-5s\t%-4s\t%-4s\t%-2s\t%-7s\t%-8s\t%-9s\t%-9s\t%-8s";
	}
	
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
	
	private void printEnd(CarWashState state) {
		printSeparator();
		System.out.println("Total idle machine time:" + state.getMachineIdleTime());
		System.out.println("Total queueing time: " + state.getQueueTime());
		System.out.println("Mean queuing time: " + state.getMeanQueueTime());
		System.out.println("Rejected cars: " + state.getNumRejectedCars());
	}
	
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
	
	private void printSeparator() {
		System.out.println("-------------------------------------");
	}
}
