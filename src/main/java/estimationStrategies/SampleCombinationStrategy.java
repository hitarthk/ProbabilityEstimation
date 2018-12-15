package estimationStrategies;

import beans.Sample;
import org.apache.commons.math3.distribution.BetaDistribution;

import java.util.*;

/**
 * Created by hitarth.k on 10/02/18.
 */
public class SampleCombinationStrategy extends EstimationStrategy {
    int ITERATIONS;
    public SampleCombinationStrategy(int n, double[] alpha, double[] beta, double[] rpc, int ITERATIONS) {
        super(n, alpha, beta, rpc);
        this.ITERATIONS = ITERATIONS;
    }

    public SampleCombinationStrategy(int n, double[] alpha, double[] beta, int ITERATIONS) {
        super(n, alpha, beta);
        this.ITERATIONS = ITERATIONS;
    }

    public double[] estimateProbability() {
        List<Sample> samples = new ArrayList<Sample>();
        double[] perc = new double[n];
        for (int i = 0; i < n; i++) {
            BetaDistribution distribution = new BetaDistribution(alpha[i], beta[i]);
            for (int iter = 0; iter < ITERATIONS; iter++) {
                samples.add(new Sample(distribution.sample()*rpc[i], i));
            }
        }

        Collections.sort(samples);
        Map<Integer, Double> map = new HashMap<Integer, Double>(n);
        for (int i = 0; i < n; i++) {
            map.put(i, 1.0 * ITERATIONS);
        }
        double[] favCount = new double[n];
        double curCount = Math.pow(ITERATIONS, n);

        for (Sample sample : samples) {
            favCount[sample.index] += curCount / map.get(sample.index);
            curCount /= map.get(sample.index);
            map.put(sample.index, map.get(sample.index) - 1);
            curCount *= map.get(sample.index);
        }


        double total = Math.pow(ITERATIONS, n);
        for (int i = 0; i < n; i++) {
            perc[i] = (100 * favCount[i]) / total;
        }
        return perc;
    }
}
