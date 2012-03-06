package deds;

import java.io.PrintStream;

import deds.events.Event;

public class TextBasedSimView  extends SimView {

	protected PrintStream out;
	
	private final String formatter;
	
	/**
	 * 
	 * @param s which it will take state info from
	 * @param out on which it will output data
	 */
	public TextBasedSimView(State s,PrintStream out) {
		super(s);
		this.out = out;
		formatter = "%-5s\t%-7s";
	}
	
	/**
	 * 
	 * prints a row with table columns
	 * 
	 * The row with table columns 
	 * 
	 */
	@Override
	protected void showPreData() {
		printTableHeader();
		
	}

	/**
	 * 
	 * prints a text-based row in table with current state and event info.
	 * Takes row from method getEventRow(Event)
	 * 
	 * @param e
	 *          event which occurred
	 *          
	 */
	@Override
	protected void showEventOccuredData(Event e) {
		printEventRow(e);
		
	}

	/**
	 * Empty; Doesn't show any post data
	 */
	@Override
	protected void showPostData() {
		
	}

	private void printTableHeader() {
		out.println(getTableHeaderLine());
	}
	
	private void printEventRow(Event e) {
	       out.println(getEventRow(e));
	}
	
	
	/**
	 * 
	 * @return a text-based table header
	 */
	protected String getTableHeaderLine() {
		return String.format(formatter,"Time", "Event");
	}
	
	
	/**
	 * 
	 * @param e
	 *          event which occurred
	 *          
	 * @return a text-based row in table with current state and event info
	 */
	protected String getEventRow(Event e) {
		float time = e.getTime();
		String eventName = e.getName();
		
		return String.format(formatter,
				String.format("%.2f", time),
				eventName);
	}
}
