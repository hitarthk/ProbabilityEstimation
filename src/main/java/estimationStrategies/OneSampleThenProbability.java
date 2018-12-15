package estimationStrategies;

/**
 * Created by hitarth.k on 10/02/18.
 */
public class OneSampleThenProbability extends EstimationStrategy {
    public int ITERATIONS;

    public OneSampleThenProbability(int n, double[] alpha, double[] beta, double[] rpc, int ITERATIONS) {
        super(n, alpha, beta, rpc);
        this.ITERATIONS = ITERATIONS;
    }

    public OneSampleThenProbability(int n, double[] alpha, double[] beta, int ITERATIONS) {
        super(n, alpha, beta);
        this.ITERATIONS = ITERATIONS;
    }

    public double[] estimateProbability() {
        double[] perc = new double[n];
        for (int i = 0; i < n; i++) {
            for (int iter = 0; iter < ITERATIONS; iter++) {
                double sample = distributions[i].sample() * rpc[i];
                double prob = 1;
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        prob *= distributions[j].cumulativeProbability(sample/rpc[j]);
                    }
                }
                perc[i] += prob;
            }
            perc[i] = (perc[i]*100)/ITERATIONS;
        }
        return perc;
    }
}
