import java.io.*;

public class AdaptiveLearningRatePerceptron {

	public static void main (String[] args) throws FileNotFoundException {

		String FILENAME = args[0];
		float LEARNING_RATE = Float.parseFloat(args[1]);
		int BATCH_SIZE = Integer.parseInt(args[2]);
		float MULTIPLIER = Float.parseFloat(args[3]);

		double BESTRATE = 0;
		double BESTACCU = 0;

		int startIndex = 0;
		csvEventParser cp = new csvEventParser(FILENAME);
		cp.parse();
		int maxBatch = cp.getEvents().length - 1;

		while (startIndex <= maxBatch) {

			Neuron n1 = new Neuron(LEARNING_RATE, cp.getEvents(), cp.getKeywords(), startIndex);
			Neuron n2 = new Neuron(MULTIPLIER * LEARNING_RATE, cp.getEvents(), cp.getKeywords(), startIndex);
			Neuron n3 = new Neuron((1 / MULTIPLIER) * LEARNING_RATE, cp.getEvents(), cp.getKeywords(), startIndex);

			n1.train();
			n2.train();
			n3.train();

			double err1 = n1.accuracy;
			double err2 = n2.accuracy;
			double err3 = n3.accuracy;

			if (err1 >= err2 && err1 >= err3) {
				System.out.println("Ideal learning rate: " + LEARNING_RATE + " accuracy obtained: " + err1); 
				if (err1 >= BESTACCU) {
					BESTACCU = err1;
					BESTRATE = LEARNING_RATE;
				}
			}
			else if (err2 >= err1 && err2 >= err3) {
				System.out.println("Ideal learning rate: " + MULTIPLIER * LEARNING_RATE + " accuracy obtained: " + err1); 
				LEARNING_RATE = MULTIPLIER * LEARNING_RATE;
				if (err2 >= BESTACCU) {
					BESTACCU = err2;
					BESTRATE = LEARNING_RATE;
				}
			}
			else {
				System.out.println("Ideal learning rate: " + (1 / MULTIPLIER) * LEARNING_RATE + " accuracy obtained: " + err1); 
				LEARNING_RATE = (1 / MULTIPLIER) * LEARNING_RATE;
				if (err3 >= BESTACCU) {
					BESTACCU = err3;
					BESTRATE = LEARNING_RATE;
				}
			}

			startIndex += BATCH_SIZE;
		}

		System.out.println("Best accuracy achieved: " + BESTACCU + " using learning rate: " + BESTRATE);

	}
}