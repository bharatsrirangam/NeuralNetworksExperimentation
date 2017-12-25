
public class NeuralNet {

	private double theta = 0.5;
	private int numParticles;
	private int numPerceps;
	private double learningRate;

	public static void main(String[] args) {
		System.out.println("Hola Amigo");
		(new NeuralNet()).mainExecute();
	}

	private void mainExecute() {
		numParticles = (int)(Math.random() * 1000 + 208);
		numPerceps = (int)(Math.random()*7 + 3); 
		int iterations = (int)(Math.random() * 150 + 50);

		//calculate ranges
		int[][] ranges = new int[numPerceps][4];
		for (int i = 0; i < numPerceps; i++) {
			int neg = (genInt(1,10)%2 == 0) ? -1:1;
			ranges[i][0] = genInt(0,100) * neg;
			ranges[i][1] = ranges[i][0] + genInt(1,50);
			ranges[i][2] = ranges[i][1] + genInt(1,50);
			ranges[i][3] = ranges[i][2] + genInt(1,50);
		}

		double[] weights = new double[numPerceps];
		double[][] inputs = new double[numParticles][numPerceps];

		int[] outputs = new int[numParticles];

		//produce the different sets
		for (int i = 0; i < numParticles/2; i++) {
			for (int x = 0; x < numPerceps - 1; x++) {
				inputs[i][x] = genDouble(ranges[x][0], ranges[x][1]);
			}
			inputs[i][numPerceps - 1] = 1;
			outputs[i] = 1;
		}

		for (int i = numParticles/2; i < numParticles; i++) {
			for (int x = 0; x < numPerceps - 1; x++) {
				inputs[i][x] = genDouble(ranges[x][2], ranges[x][3]);
			}
			inputs[i][numPerceps - 1] = 1;
			outputs[i] = 0;
		}

		//set up random weights
		for (int x = 0; x < numPerceps; x++) {
				weights[x] = genDouble(0, 1);
		}

		int globalErrorSum = 0;
		int output = 0;
		int num = 0; 
		do {
			num++;
			globalErrorSum = 0;
			int localError = 0; 

			for (int i = 0; i < numParticles; i++) {
				output = calculateOutput(weights, inputs[i]);
				localError = outputs[i] - output;

				globalErrorSum += localError*localError;
				for (int x = 0; x < weights.length; x++) {
					weights[x] += learningRate*localError*inputs[i][x];
				}
			}
		} while (num < iterations && globalErrorSum != 0);

		//TODO
		//print the hyperplane
	}
	
	private static double genDouble(double min, double max) {
		max -= 1;
		return (Math.random()*(max-min) + min);
	}

	private static int genInt(int min, int max) {
		max -= 1;
		return (int)(Math.random()*(max-min) + min);
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
		return (sigmoid(totalSum) < theta) ? 0 : 1;
	}


}