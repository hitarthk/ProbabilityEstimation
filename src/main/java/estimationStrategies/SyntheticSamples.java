package estimationStrategies;

import beans.Sample;
import org.apache.commons.math3.distribution.BetaDistribution;

import java.util.*;

/**
 * Created by hitarth.k on 13/02/18.
 */
public class SyntheticSamples extends EstimationStrategy {
    int ITERATIONS;

    public SyntheticSamples(int n, double[] alpha, double[] beta, double[] rpc, int ITERATIONS) {
        super(n, alpha, beta, rpc);
        this.ITERATIONS = ITERATIONS;
    }

    public SyntheticSamples(int n, double[] alpha, double[] beta, int ITERATIONS) {
        super(n, alpha, beta);
        this.ITERATIONS = ITERATIONS;
    }

    public double[] estimateProbability() {
        double[] sampleProbs = new double[ITERATIONS + 1];
        double[] perc = new double[n];
        double incr = 1.0 / ITERATIONS;
        double maxProb = 0.99;
        double minProb = 0.01;
        for (int iter = 1; iter <= ITERATIONS; iter++) {
            sampleProbs[iter] = Math.max(Math.min(sampleProbs[iter - 1] + incr, maxProb), minProb);
        }

        List<Sample> samples = getSamples(sampleProbs);
        double samplesPerArm = (1.0 * samples.size()) / n;
        Collections.sort(samples);
        Map<Integer, Double> map = new HashMap<Integer, Double>(n);
        for (int i = 0; i < n; i++) {
            map.put(i, samplesPerArm);
        }
        double[] favCount = new double[n];
        double curCount = Math.pow(samplesPerArm, n);

        for (Sample sample : samples) {
            favCount[sample.index] += curCount / map.get(sample.index);
            curCount /= map.get(sample.index);
            map.put(sample.index, map.get(sample.index) - 1);
            curCount *= map.get(sample.index);
        }

        double total = Math.pow(samplesPerArm, n);
        for (int i = 0; i < n; i++) {
            perc[i] = (100 * favCount[i]) / total;
        }
        return perc;
    }

    protected List<Sample> getSamples(double[] sampleProbs) {
        List<Sample> samples = new ArrayList<Sample>();
        double intervalLength = sampleProbs[1] - sampleProbs[0];
//        System.out.println("Sample probs: "+Arrays.toString(sampleProbs));
        for (int i = 0; i < n; i++) {
            BetaDistribution distribution = new BetaDistribution(alpha[i], beta[i]);
            for (int iter = 1; iter <= ITERATIONS; iter++) {
                double uniformSample = sampleProbs[iter - 1] + Math.random() * intervalLength;
//                System.out.println(i+" "+uniformSample);
                samples.add(new Sample(distribution.inverseCumulativeProbability(uniformSample) * rpc[i], i));
            }
        }
        return samples;
    }

}
