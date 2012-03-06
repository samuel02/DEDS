package carwash.randomstreams;

import java.util.Random;

/**
 * Dynamic object that creates a uniform random stream of numbers.
 * 
 * The constructor takes lower and upper bound as well as
 * a seed to generate the stream.
 * 
 * For more info on uniform distribution see 
 * http://en.wikipedia.org/wiki/Uniform_distribution_%28continuous%29#Sampling_from_a_uniform_distribution
 * 
 * @author samuelnilsson
 *
 */
public class UniformRandomStream {

	private Random rand;
	private double lower, width;
	
	public UniformRandomStream(double lower, double upper, long seed) {
		rand = new Random(seed);
		this.lower = lower;
		this.width = upper-lower;
	}
	
	public UniformRandomStream(double lower, double upper) {
		rand = new Random();
	    this.lower = lower;
	    this.width = upper-lower;
	}
	
	public double next() {
	    return lower+rand.nextDouble()*width;
	}
}

