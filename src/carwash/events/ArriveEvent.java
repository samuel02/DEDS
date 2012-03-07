package carwash.events;

import carwash.CarWashState;
import deds.Simulator;

/**
 * 
 * @author Sarar
 * 
 * An arrive event doing some actions when a car arrived to car wash
 *
 */
class ArriveEvent extends CarWashEvent{
	
	protected ArriveEvent(float time) {
		super(null,"Arrive", time);
	}

	@Override
	protected void execute(Simulator sim,CarWashState s) {
		
		car = s.createNewCar();
		
		// this block does the necessary actions for the arrived car
		{
			//if there is a free machine and there is no car in queue
			if(s.isQueueEmpty() && s.hasVacantMachines() ) {
				
				//serve car
				float timeCarFinished = s.serveCar(car);
				//adds an event telling when the car will leave
				sim.addEvent(new LeaveEvent(timeCarFinished,car));
				
			}
			//else if the queue for machines are not full
			else if(!s.isQueueFull()){
				//places car in queue
				s.placeCarInQueue(car);
				
			}
			//else the car will not be washed
			else  {
				//tells state that a car was rejected
				s.rejectCar(car);
				
			}
			
		}
		
		
		//this block does necessary actions on the simulator so the simulation will keep going on
		{
			//inserts a new arrive event into queue
			sim.addEvent(new ArriveEvent(s.getNewArriveTime()));
		}
	}

}
