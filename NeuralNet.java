
public class NeuralNet {

	private double theta = 0.5;
	private int numParticles;
	private int numPerceps;
	private double learningRate = 0.1;

	public static void main(String[] args) {
		System.out.println("Hola Amigo");
		(new NeuralNet()).mainExecute();
	}

	private void mainExecute() {
		numParticles = (int)(Math.random() * 1000 + 208);
		numPerceps = (int)(Math.random()*7 + 3); 
		int iterations = (int)(Math.random() * 150 + 50);
		double[] weights = new double[numPerceps];
		double[][] inputs = new double[numParticles][numPerceps];
		int[] outputs = new int[numParticles];
		int[][] ranges = new int[numPerceps][4];


		//calculate random ranges
		for (int i = 0; i < numPerceps; i++) {
			int neg = (genInt(1,10)%2 == 0) ? -1:1;
			ranges[i][0] = genInt(0,100) * neg;
			ranges[i][1] = ranges[i][0] + genInt(1,50);
			ranges[i][2] = ranges[i][1] + genInt(1,50);
			ranges[i][3] = ranges[i][2] + genInt(1,50);
		}

		//produce the different sets of points for the two classes 
		for (int i = 0; i < numParticles/2; i++) {
			System.out.println("Particle " + i + ": ");
			for (int x = 0; x < numPerceps - 1; x++) {
				inputs[i][x] = genDouble(ranges[x][0], ranges[x][1]);
				System.out.print("X"+x + ": " + inputs[i][x] + " ");
			}
			inputs[i][numPerceps - 1] = 1;
			System.out.print("X"+(numPerceps-1) + ": " + inputs[i][numPerceps-1] + " ");
			outputs[i] = 1;
			System.out.println("= Output: 1");
		}

		//two for loops are used in order to guarantee linear seperability
		for (int i = numParticles/2; i < numParticles; i++) {
			System.out.println("Particle " + i + ": ");
			for (int x = 0; x < numPerceps - 1; x++) {
				inputs[i][x] = genDouble(ranges[x][2], ranges[x][3]);
				System.out.print("X"+x + ": " + inputs[i][x] + " ");
			}
			inputs[i][numPerceps - 1] = 1;
			System.out.print("X"+(numPerceps-1) + ": " + inputs[i][numPerceps-1] + " ");
			outputs[i] = 0;
			System.out.println("= Output: 0");
		}

		//set up random weights
		for (int x = 0; x < numPerceps; x++) {
				weights[x] = genDouble(0, 1);
		}

		
		//Start of actual Neural Net
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

		//print the hyperplane
		int j = 0;
		for (double part: weights) {
			j++;
			System.out.print("X" + j+ " * " + part + " + ");
		}
		System.out.println("0 = 0");
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