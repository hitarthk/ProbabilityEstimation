package estimationStrategies;

import java.util.Arrays;

/**
 * Created by hitarth.k on 10/02/18.
 */
public class StatsPrinter {
    int TRIALS;
    EstimationStrategy estimationStrategy;
    int n;
    double[] avgPerc;
    long avgTime;
    double[] minPerc;
    double[] maxPerc;
    double[] diff;
    double[] var;
    double[] avgSqPerc;
    double[] std;

    public StatsPrinter(int n, int TRIALS, EstimationStrategy estimationStrategy) {
        this.TRIALS = TRIALS;
        this.estimationStrategy = estimationStrategy;
        this.n = n;
        avgPerc = new double[n];
        avgTime = 0l;
        minPerc = new double[n];
        Arrays.fill(minPerc, 100000);
        maxPerc = new double[n];
        diff = new double[n];
        var = new double[n];
        std = new double[n];
        avgSqPerc = new double[n];
    }

    public void printStatsForStrategy() {
        System.out.println("___________________");
        for (int trial = 0; trial < TRIALS; trial++) {
            long t1 = System.currentTimeMillis();
            double[] perc = estimationStrategy.estimateProbability();
            long t2 = System.currentTimeMillis();
            avgTime += t2-t1;
            for(int i = 0;i<n;i++){
                avgPerc[i] += perc[i];
                minPerc[i] = Math.min(minPerc[i], perc[i]);
                maxPerc[i] = Math.max(maxPerc[i], perc[i]);
                avgSqPerc[i] += perc[i]*perc[i];
            }
        }
        avgTime /= TRIALS;
        for(int i = 0;i<n;i++){
            avgPerc[i] /= TRIALS;
            avgSqPerc[i] /= TRIALS;
            var[i] = avgSqPerc[i] - avgPerc[i]*avgPerc[i];
            std[i] = Math.sqrt(var[i]);
            diff[i] = maxPerc[i] - minPerc[i];
        }

        System.out.println(estimationStrategy.getClass()+" results: ");
        System.out.println("AvgTime: " + avgTime);
        System.out.println("AvgPerc: " + Arrays.toString(avgPerc));
        System.out.println("MinPerc: " + Arrays.toString(minPerc));
        System.out.println("MaxPerc: " + Arrays.toString(maxPerc));
        System.out.println("Diff:  " + Arrays.toString(diff));
        System.out.println("Variance: " + Arrays.toString(var));
        System.out.println("Standard deviation: " + Arrays.toString(std));
        System.out.println("___________________");
    }
}
