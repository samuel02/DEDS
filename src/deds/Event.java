package deds;

public abstract class Event implements Comparable<Event>
{ 
	final Float time;
	String name;
	
	protected Event(String name,float time) {
		this.name = name;
		this.time = time;
	}
	
	
	public int compareTo(Event e) {
		return time.compareTo(e.time);
	}
	
	public final float getTime() {
		return time;
	}
	
	public abstract void execute(State s);
	
	public final String toString() {
		return name;
	}

}
