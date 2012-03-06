package carwash.randomstreams;

import java.util.Random;

/**
 * Dynamic object that creates an exponential random stream.
 * 
 * The constructor takes a lambda and a seed to generate
 * the stream.
 * 
 * For more info on exponential distribution see
 * http://en.wikipedia.org/wiki/Exponential_distribution
 * 
 * @author samuelnilsson
 *
 */
public class ExponentialRandomStream {
	
	private Random rand;
	private double lambda;
	  
	public ExponentialRandomStream(double lambda, long seed) {
	  	rand = new Random(seed);
	  	this.lambda = lambda;
	}
	  
	public ExponentialRandomStream(double lambda) {
		rand = new Random();
	    this.lambda = lambda;
	}
	  
	public double next() {
	  	return -Math.log(rand.nextDouble())/lambda;
	}
}

