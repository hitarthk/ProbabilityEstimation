package estimationStrategies;

import org.apache.commons.math3.distribution.BetaDistribution;

import java.util.Arrays;

/**
 * Created by hitarth.k on 10/02/18.
 */
public abstract class EstimationStrategy {
    int n;
    double[] alpha;
    double[] beta;
    double[] rpc;
    BetaDistribution[] distributions;

    public EstimationStrategy(int n, double[] alpha, double[] beta, double[] rpc) {
        this.n = n;
        this.alpha = alpha;
        this.beta = beta;
        this.rpc = rpc;
        distributions = new BetaDistribution[n];
        for (int i = 0; i < n; i++) {
            distributions[i] = new BetaDistribution(alpha[i], beta[i]);
        }
    }

    public EstimationStrategy(int n, double[] alpha, double[] beta) {
        this(n, alpha, beta, new double[n]);
        Arrays.fill(rpc, 1);
    }

    public abstract double[] estimateProbability();
}
