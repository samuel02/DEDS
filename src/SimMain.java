import deds.*;
import carwash.*;


public class SimMain {

	/**
	 * @param args
	 */
	public static void main( String[] args ) {

		CarWashState state = new CarWashState();
		state.addObserver( new CarWashView() );
		
		Simulator simulator = new Simulator();
		simulator.setState( state );
		simulator.addEvent( new StartEvent( 0.0f ) );
		simulator.addEvent( new StopEvent( 15.0f ) );
		simulator.run();
	}
}
