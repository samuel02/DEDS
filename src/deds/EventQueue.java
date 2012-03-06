package deds;

import java.util.ArrayList;
import java.util.Collections;

import deds.events.Event;
/**
 * 
 * @author Sarar
 * 
 * A Queue with events sorted by there time in ascending order.
 *
 */
public class EventQueue {
    
    ArrayList<Event> sortedList;
      
    /**
     * init's queue
     */
    public EventQueue() {
        sortedList = new ArrayList<Event>();
    }
    
    
    /**
     * 
     * inserts an event into queue
     *  
     * @param event, the event to insert
     */
    public void addEvent(Event event) {
         int index = Collections.binarySearch(sortedList, event);
         
         if (index < 0) { 
             sortedList.add(-index-1, event);
         }
         else { 
             sortedList.add(index,event);
         }
    } 
    
    /**
     * 
     * Removes next event from queue head
     * 
     * @return the removed event
     */
    public Event removeNextEvent() {
        return sortedList.remove(0);
        
    }
    
    /**
     * 
     * @return the next event in queue
     */
    public Event getNextEvent() {
    	return sortedList.get(0);
    }
    
}
