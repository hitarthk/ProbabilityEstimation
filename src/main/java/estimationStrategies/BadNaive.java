package estimationStrategies;

import org.apache.commons.math3.distribution.BetaDistribution;

/**
 * Created by hitarth.k on 13/02/18.
 */
public class BadNaive extends EstimationStrategy {
    int ITERATIONS;

    public BadNaive(int n, double[] alpha, double[] beta, double[] rpc, int ITERATIONS) {
        super(n, alpha, beta, rpc);
        this.ITERATIONS = ITERATIONS;
    }

    public BadNaive(int n, double[] alpha, double[] beta, int ITERATIONS) {
        super(n, alpha, beta);
        this.ITERATIONS = ITERATIONS;
    }

    @Override
    public double[] estimateProbability() {
        double[] perc = new double[n];
        for (int iter = 0; iter < ITERATIONS; iter++) {
            int maxIndex = -1;
            double maxScore = -1;
            for (int i = 0; i < n; i++) {
                double sample = new BetaDistribution(alpha[i], beta[i]).sample()*rpc[i];
                if (sample > maxScore) {
                    maxScore = sample;
                    maxIndex = i;
                }
            }
            perc[maxIndex]++;
        }
        for (int i = 0; i < n; i++) {
            perc[i] /= ITERATIONS/100D;
        }
        return perc;
    }
}
