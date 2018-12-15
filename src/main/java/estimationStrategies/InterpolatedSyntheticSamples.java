package estimationStrategies;

import beans.Sample;
import org.apache.commons.math3.distribution.BetaDistribution;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hitarth.k on 22/02/18.
 */
public class InterpolatedSyntheticSamples extends SyntheticSamples {

    public InterpolatedSyntheticSamples(int n, double[] alpha, double[] beta, double[] rpc, int ITERATIONS) {
        super(n, alpha, beta, rpc, ITERATIONS);
    }

    public InterpolatedSyntheticSamples(int n, double[] alpha, double[] beta, int ITERATIONS) {
        super(n, alpha, beta, ITERATIONS);
    }

    @Override
    protected List<Sample> getSamples(double[] sampleProbs) {
        List<Sample> samples = new ArrayList<Sample>();
        double interpolatingSamples = 30;
        for (int i = 0; i < n; i++) {
            BetaDistribution distribution = new BetaDistribution(alpha[i], beta[i]);
            double[] tempSamlpes = new double[sampleProbs.length];
            for (int iter = 0; iter <= ITERATIONS; iter++) {
                tempSamlpes[iter] = distribution.inverseCumulativeProbability(sampleProbs[iter]) * rpc[i];
            }
            for (int iter = 0; iter < ITERATIONS; iter++) {
                double incr = (tempSamlpes[iter + 1] - tempSamlpes[iter]) / interpolatingSamples;
                double cur = tempSamlpes[iter];
                while (cur < tempSamlpes[iter + 1]){
                    samples.add(new Sample(cur, i));
                    cur += incr;
                }
            }
        }
        return samples;
    }
}
