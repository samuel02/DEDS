package carwash;

import java.io.PrintStream;

import carwash.CarWashState.Car;
import carwash.events.CarWashEvent;
import deds.TextBasedSimView;
import deds.events.Event;

public class CarWashSimView extends TextBasedSimView {

	private final static String FORMATTER = "%-4s\t%-4s\t%-2s\t%-8s\t%-9s\t%-9s\t%-8s";
	private final static String NO_ID = "-";
	private final static int SEPARATOR_LENGTH = 95;
	
	protected CarWashState cws;
	

	public CarWashSimView(CarWashState s, PrintStream out) {
		super(s,out);
		cws = s;
	}
	
	@Override
	protected void showPreData() {
	      printPreHeaders();
	      super.showPreData();
	}
	
	protected void showPostData() {
		printSeparator();
		out.println("Total idle machine time:" + String.format("%.2f", cws.getMachineIdleTime()));
		out.println("Total queueing time: " + String.format("%.2f", cws.getQueueTime()));
		out.println("Mean queuing time: " + String.format("%.2f", cws.getMeanQueueTime()));
		out.println("Rejected cars: " + cws.getNumRejectedCars());
	}
	

	private void printPreHeaders() {
		float[] fastDist = cws.getFastDistribution();
		float[] slowDist = cws.getSlowDistribution();
		
		out.println("Fast machines: " + cws.getNumFastMachines());
        out.println("Slow machines: " + cws.getNumSlowMachines());
		out.println("Fast distribution: (" + fastDist[0] + ", " + fastDist[1] + ")");
		out.println("Slow distribution: (" + slowDist[0] + ", " + slowDist[1] + ")");
		out.println("Exponential distribution with lambda = " + cws.getExponentialDistribution());
		out.println("Seed = " + cws.getSeed());
		out.println("Max Queue Size: " + cws.getMaxQueueSize());
		printSeparator();
		
	}
	
	private void printSeparator() {
		String separator = "-";
		for(int i = 0; i<SEPARATOR_LENGTH; i++) {
			separator += "-";
		}
		out.println(separator);
	}

	@Override
	protected String getTableHeaderLine() {
		return super.getTableHeaderLine()+"\t"+String.format(FORMATTER, 
				"Fast",
				"Slow",
				"Id",
				"Event",
				"IdleTime",
				"QueueTime",
				"QueueSize",
				"Rejected"
				);
	}
	
	
	@Override
	protected String getEventRow(Event e) { 
		int fast = cws.getNumFastMachinesAvailable();
		int slow = cws.getNumSlowMachinesAvailable();
		float idleTime = cws.getMachineIdleTime();
		float queueTime = cws.getQueueTime();
		int queueSize = cws.getQueueSize();
		int rejected = cws.getNumRejectedCars();
		
		float time = e.getTime();
		String id = NO_ID;
		if(e.getClass() ==  CarWashEvent.class) {
			Car c = ((CarWashEvent) e).getCar();
			if(c != null) {
				id = ""+((CarWashEvent) e).getCar().getId();
			}
		}
		
		return super.getEventRow(e)+"\t"+String.format(FORMATTER,
						fast,
						slow,
						id,
						String.format("%.2f", idleTime),
						String.format("%.2f", queueTime),
						queueSize,
						rejected);
	}
}
