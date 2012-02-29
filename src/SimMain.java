
public class SimMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		carwash.State state = new carwash.State();
		state.addView( new carwash.View() );
		
		deds.Simulator simulator = new deds.Simulator();
		simulator.setState( state );
		simulator.addEvent( new carwash.event.Start( 0.0f ) );
		simulator.addEvent( new deds.event.Stop( 15.0f ) );
		simulator.run();
	}

}
