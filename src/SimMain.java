import deds.*;
import deds.events.*;
import carwash.*;
import carwash.events.*;


public class SimMain
{
	/**
	 * @param args
	 */
	public static void main( String[] args )
	{

		CarWashState state = new CarWashState();
		state.addObserver( new CarWashView() );

		Simulator simulator = new Simulator(state);
		simulator.addEvent( new StartCarWashEvent());
		simulator.addEvent( new StopEvent( 15.0f ) );
		simulator.run();
	}
}
