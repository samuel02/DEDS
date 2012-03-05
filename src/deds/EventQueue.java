package deds;

import java.util.ArrayList;
import java.util.Collections;

public class EventQueue {
    
    ArrayList<Event> sortedList;
      
    public EventQueue() {
        sortedList = new ArrayList<Event>();
    }
    public void addEvent(Event event) {
         int index = Collections.binarySearch(sortedList, event);
         
         if (index < 0) {
             sortedList.add(-index-1, event);
         }
         else {
             sortedList.add(index,event);
         }
    } 
    
    
    public Event getNextEvent() {
        return sortedList.remove(0);
        
    }
    
}
