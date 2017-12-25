
public class NeuralNet {

	private double theta = 0.5;
	private int iterations;
	private int numPerceps;

	public static void main(String[] args) {
		System.out.println("Hola Amigo");
		iterations = (int)(Math.random() * 5000 + 208);
		numPerceps = (int)(Math.random()*7 + 3); 

		//calculate ranges
		int[][] ranges = new int[numPerceps][2];

		double[] weights = new double[numPerceps];
		double[][] inputs = new double[iterations][numPerceps];

		int outputs = new int[iterations];

		//produce the different sets
		for (int i = 0; i < iterations/2; i++) {
			for (int x = 0; x < numPerceps; x++) {
				inputs[i][x] = genDouble(ranges[x][0], ranges[x][1]);
			}
		}
	}

	private static double genDouble(double min, double max) {
		return (Math.random()*max + min);
	}

	private static int genInt(int min, int max) {
		return (int)(Math.random()*max + min);
	}

	private double sigmoid(double input) {
		double toReturn = 1.0 + Math.exp(input * -1.0);
		toReturn = 1.0/toReturn;
		return toReturn;
	}

	//Single Layer Output calculation
	private int calculateOutput(double[] weights, double[] inputs) {
		double totalSum = 0;
		for (int i = 0; i < weights.length; i++) {
			totalSum += weights[i] * inputs[i];
		}
		return (sigmoid(totalSum) < 0.5) ? 0 : 1;
	}


}