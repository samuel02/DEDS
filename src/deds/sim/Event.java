package deds.sim;

public abstract class Event implements Comparable<Event> {
	
Long time;
    
    @Override
    public int compareTo(Event o) {
        return time.compareTo(o.time);
    }
    
    public abstract void execute();

}
