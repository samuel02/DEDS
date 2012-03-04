package carwash;

import java.util.Observable;
import java.util.Formatter;

/**
 * CarWashView
 * 
 * @author samuelnilsson
 *
 */
public class CarWashView extends deds.SimView{
	
	Formatter tableRow;
	
	public CarWashView() {
		super();
		
		tableRow = new Formatter();
		tableRow.format("", "Time", 
				 "Fast",
				 "Slow",
				 "Id",
				 "Event",
				 "IdleTime",
				 "QueueTime",
				 "QueueSize",
				 "Rejected");		
		
		printHeader();
		printTableHeader();
	}
	
	public void update(Observable arg0, Object arg1) {
		if (!simNotFinished()) {
			printTableRow(arg0);
		} else {
			printEnd();
		}
	
	}
	
	private void printHeader() {
		System.out.println("Fast machines: " + fastMachines);
		System.out.println("Slow machines: " + slowMachines);
		System.out.println("Fast distribution: " + fastDistribution);
		System.out.println("Slow distribution: " + slowDistribution);
		System.out.println("Exponential distribution with lambda = " + expWithLambda);
		System.out.println("Seed = " + seed);
		System.out.println("Max Queue Size: " + maxQueueSize);
		printSeparator();
	}
	
	private void printTableHeader() {
		
		System.out.println(tableHeader);
	}
	
	private void printEnd() {
		printSeparator();
		System.out.println("Total idle machine time:" + totalIdleTime);
		System.out.println("Total queueing time: " + totalQueueTime);
		System.out.println("Mean queuing time: " + meanQueueTime);
		System.out.prinln("Rejected cars: " + rejectedCars)
	}
	
	private void printTableRow(Object arg0) {
		String time = arg1.time;
		String fast = arg1.fast;
		String slow = arg1.slow;
		String id = arg1.id;
		String event = arg1.event;
		String idleTime = arg1.idletime;
		String queueTime = arg1.queueTime;
		String queueSize = arg1.queueSize;
		String rejected = arg1.rejected;
		
		
	}
	
	private void printSeparator() {
		System.out.println("-------------------------------------");
	}
}
