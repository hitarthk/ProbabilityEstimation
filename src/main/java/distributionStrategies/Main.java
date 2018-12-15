package distributionStrategies;

import beans.Sample;
import org.apache.commons.math3.distribution.BetaDistribution;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        int arms = 3;
        int sets = 2;
        double[][] l2a = {{0.01, 0.02, 0.03}, {0.001, 0.002, 0.003}};
        double[] impressions = {100, 100, 100};
        int ITERATIONS = 1000;
        for (int s = 0; s < sets; s++) {
            double[] alpha = new double[arms];
            double[] beta = new double[arms];
            for (int a = 0; a < arms; a++) {
                double talpha = l2a[s][a] * impressions[a];
                double tbeta = impressions[a] - talpha;
                alpha[a] = talpha;
                beta[a] = tbeta;
            }

            double[] perc = distributePercentage(alpha, beta);
            System.out.println(Arrays.toString(perc));
            perc = distributePercentageWithScaledCTR(alpha, beta);
            System.out.println(Arrays.toString(perc));
            System.out.println("|||||||||||");
        }
    }

    static double[] distributePercentage(double[] alpha, double[] beta) {
        int ITERATIONS = 1000;
        int arms = alpha.length;
        double[] perc = new double[arms];
        for (int iter = 0; iter < ITERATIONS; iter++) {
            double max = -1;
            int maxindex = -1;
            for (int a = 0; a < arms; a++) {
                double sample = new BetaDistribution(alpha[a], beta[a]).sample();
                if (sample > max) {
                    max = sample;
                    maxindex = a;
                }
            }
            perc[maxindex]++;
        }
        for (int a = 0; a < arms; a++) {
            perc[a] /= ITERATIONS / 100D;
        }
        return perc;
    }

    static double[] distributePercentageWithScaledCTR(double[] alpha, double[] beta) {
        int ITERATIONS = 100;
        int arms = alpha.length;
        double[][] samples = new double[arms][ITERATIONS];
        for (int a = 0; a < arms; a++) {
            BetaDistribution betaDistribution = new BetaDistribution(alpha[a], beta[a]);
            double curp = 0;
            double incr = 1.0 / ITERATIONS;
            for (int iter = 0; iter < ITERATIONS; iter++) {
                double sampleValue = betaDistribution.sample();
                samples[a][iter] = sampleValue;
            }
        }
        for (int iter = 0; iter < ITERATIONS; iter++) {
            double avg = 0;
            for (int a = 0; a < arms; a++) {
                avg += samples[a][iter];
            }
            avg /= arms;
            for (int a = 0; a < arms; a++) {
                samples[a][iter] /= avg;
            }
        }
        List<Sample> combinedSamples = new ArrayList<>();
        for (int a = 0; a < arms; a++) {
            for (int iter = 0; iter < ITERATIONS; iter++) {
//                combinedSamples.addAll(Arrays.stream(samples[a]).map(samplevalue -> new Sample(samplevalue, 1)).collect(Collectors.toList()));
                combinedSamples.add(new Sample(samples[a][iter], a));
            }
        }
        Collections.sort(combinedSamples);

        double totalCount = Math.pow(ITERATIONS, arms);
        double curCount = totalCount;
        double[] perc = new double[arms];
        Map<Integer, Integer> sampleCount = new HashMap<>();
        for (int a = 0; a < arms; a++) {
            sampleCount.put(a, ITERATIONS);
        }

        for (Sample sample : combinedSamples) {
            curCount /= sampleCount.get(sample.index);
            perc[sample.index] += curCount;
            sampleCount.put(sample.index, sampleCount.get(sample.index) - 1);
            curCount *= sampleCount.get(sample.index);
        }

        for (int a = 0; a < arms; a++) {
            perc[a] /= totalCount/100D;
        }
        return perc;
    }
}
