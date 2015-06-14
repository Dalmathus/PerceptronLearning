import java.util.*;

public class Neuron {
	
	public double[] weights;
	public double accuracy;

	private float LEARNING_RATE;
	private int values;
	private String[] keywords;
	private String[][] events;
	private int maxIterations;
	private int instances;
	private int BIAS;
	private double totalSucc = 0;
	private double totalFail = 0;	
	private int startIndex;

	public Neuron(float lr, String[][] e, String[] k, int i) {
		events = e;
		keywords = k;
		maxIterations = 10;
		weights = new double[events[0].length];
		initializeWeights(weights);
		instances = events.length;
		values = weights.length - 1;		
		LEARNING_RATE = lr;
		BIAS = weights.length - 1;
		startIndex = i;
	}

	/**
	//	runs the neuron through its learning algorithm with its current parameters
	**/
	public void train() {
		
		int[] output = new int[instances];

		System.out.println("Number of inputs: " + instances);
		System.out.println("Learning rate: " + LEARNING_RATE);
		System.out.println("Iterations: " + maxIterations);

		for (int i = 0; i < instances; i++) {
			output[i] = Integer.parseInt(events[i][values - 1]);
		}
		
		double localError = 0;
		double classification = 0;			
		
		int iterations = 0;
		do {
			iterations++;
			int success = 0, fail = 0;
			for (int i = startIndex; i < instances; i++) {
				String[] row = events[i];
				int target = Integer.parseInt(row[row.length - 1]); 
				classification = activationFunction(weights, row);
				double err = output[i] - classification;
				double deriv = derivativeFunction(classification);

				if ((classification >= 0.5 && target == 1) || (classification < 0.5 && target == 0)) success++;
				else fail++;

				if (target == 1) {
					for (int k = 0; k < weights.length - 1; k++) {
						weights[k] += LEARNING_RATE * deriv * Float.parseFloat(row[k]);
					}
					weights[weights.length - 1] += LEARNING_RATE * deriv * -1;
				}
				else {
					for (int k = 0; k < weights.length - 1; k++) {
						weights[k] -= LEARNING_RATE * deriv * Float.parseFloat(row[k]);
					}
					weights[weights.length - 1] -= LEARNING_RATE * deriv * -1;
				}
			}

			// track total classifications
			totalSucc += success;
			totalFail += fail;

		} while (iterations < maxIterations);

		double total = totalSucc + totalFail;
		accuracy = (totalSucc / total) * 100;
		System.out.println("Accuracy: " + accuracy);
	}

	/**
	//	runs the neuron through its learning algorithm with its current parameters for part 2
	**/
	public void trainABS() {
		
		int[] output = new int[instances];

		System.out.println("Number of inputs: " + instances);
		System.out.println("Learning rate: " + LEARNING_RATE);
		System.out.println("Iterations: " + maxIterations);

		for (int i = 0; i < instances; i++) {
			output[i] = Integer.parseInt(events[i][values - 1]);
		}
		
		double localError = 0;
		double classification = 0;			
		
		int iterations = 0;
		do {
			iterations++;
			int success = 0, fail = 0;
			for (int i = startIndex; i < instances; i++) {
				String[] row = events[i];
				int target = Integer.parseInt(row[row.length - 1]); 
				classification = activationFunction(weights, row);
				double err = output[i] - classification;
				double deriv = derivativeFunction(classification);

				if ((classification >= 0.5 && target == 1) || (classification < 0.5 && target == 0)) success++;
				else fail++;

				if (target == 1) {
					for (int k = 0; k < weights.length - 1; k++) {
						weights[k] += LEARNING_RATE * deriv * Float.parseFloat(row[k]);
					}
					weights[weights.length - 1] += LEARNING_RATE * deriv * -1;
				}
				else {
					for (int k = 0; k < weights.length - 1; k++) {
						weights[k] -= LEARNING_RATE * deriv * Float.parseFloat(row[k]);
					}
					weights[weights.length - 1] -= LEARNING_RATE * deriv * -1;
				}

				int highestWIndex = 0;
				for (int p = 0; p < weights.length - 1; p++) {
					if (Math.abs(weights[p]) >= weights[highestWIndex]) highestWIndex = p;
				}

				weights[highestWIndex] = 0;
				System.out.println(keywords[highestWIndex]);

				for (int p = 0; p <instances; p++) {
					events[p][highestWIndex] = "0";
				}

			}

			// track total classifications
			totalSucc += success;
			totalFail += fail;

		} while (iterations < maxIterations);

		double total = totalSucc + totalFail;
		accuracy = (totalSucc / total) * 100;
		System.out.println("Accuracy: " + accuracy);
	}

	public void outputResults() {

		double total = totalSucc + totalFail;
		double prob = (totalSucc / total) * 100;

		System.out.print("BIAS " + weights[BIAS] + ", ");
		for (int i = 0; i < values; i++) {
			System.out.print(keywords[i] + " " + weights[i]);
			if (i < values - 1) System.out.print(", ");
		}
		
		System.out.println('\n' + "Classification accuracy = " + prob + " Total Success: " + totalSucc + " Total Failures: " + totalFail);
	}

	/**
	//	Set all weights to 0 including the bias weight
	**/
	private void initializeWeights(double[] w) {
		Random ran = new Random();
		for (int i = 0; i < w.length; i++) {
			w[i] = 0;
		}
		// bais weight to -1
		w[BIAS] = 0;
	}

	/**
	//	sigmoid activation function
	**/
	private double activationFunction(double[] weights, String[] values) {

		double sum = 0;
		for (int i = 0; i < weights.length - 1; i++) {
			sum += (weights[i] * Integer.parseInt(values[i]));
		}
		sum += weights[weights.length - 1] * (-1);

		sum = (1/( 1 + Math.pow(Math.E,(-1*sum))));
		return sum;
	}

	/**
	//	sigmoid derivitive function
	**/
	private double derivativeFunction(double x) {
		return x * (1.0 - x);
	}

}