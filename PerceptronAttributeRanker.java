import java.io.*;

public class PerceptronAttributeRanker {

	private static float LEARNING_RATE;
	public static double[] weights;

	public static void main(String[] args) throws FileNotFoundException {

		if (args.length != 2) System.exit(-1);

		// filename of the testing data
		String filename = args[0];
		LEARNING_RATE = Float.parseFloat(args[1]);
		csvEventParser cp = new csvEventParser(filename);
		cp.parse();

		Neuron n = new Neuron(LEARNING_RATE, cp.getEvents(), cp.getKeywords(), 0);
		n.trainABS();
		n.outputResults();
	}
}