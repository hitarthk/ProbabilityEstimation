package estimationStrategies;

import java.util.Random;

/**
 * Created by hitarth.k on 10/02/18.
 */
public class Main {
    public static void main(String[] args) {
        boolean isBetaImpressions = true;

        int abound = 1;
        int bbound = 100;
        Random r = new Random(50);
        int TRIALS = 100;
        int n;
        StatsPrinter s1, s2, s3, s4, s5;
        if (!isBetaImpressions) {
            n = 20;
            double[] alpha = new double[n];
            double[] beta = new double[n];
            for (int i = 0; i < n; i++) {
                alpha[i] = r.nextInt(abound) + 1;
                beta[i] = r.nextInt(bbound) + 1;
            }

            System.out.println("L2A are: ");
            for (int i = 0; i < n; i++) {
                System.out.print(100 * (alpha[i] / beta[i]) + " ");
            }

            System.out.println();
            System.out.println("|||||||||||||||||");
            System.out.println("|||||||||||||||||");


            s1 = new StatsPrinter(n, TRIALS, new BadNaive(n, alpha, beta, 50));
            s2 = new StatsPrinter(n, TRIALS, new SampleCombinationStrategy(n, alpha, beta, 50));
            s3 = new StatsPrinter(n, TRIALS, new OneSampleThenProbability(n, alpha, beta, 30));
            s4 = new StatsPrinter(n, TRIALS, new SyntheticSamples(n, alpha, beta, 20));
        } else {
//            double[] alpha = {35.0, 47.0, 63.0, 1618.0};
//            double[] beta = {1999.0, 3287.0, 4975.0, 123100.0};
//            double[] rpc = {0.31981023771132217, 0.2887004432236457, 0.3089192696659946, 0.2986125668303024};

            double[] alpha = {
                    16,
                    104
                    };

            double[] beta = {
                    3210,
                    47741
            };

            double[] rpc = {
                    1,
                    1
            };

            n = alpha.length;

            for (int i = 0; i < n; i++) {
                beta[i] -= alpha[i];
            }

            System.out.println("L2A are: ");
            for (int i = 0; i < n; i++) {
                System.out.print(100 * (alpha[i] / beta[i]) + " ");
            }

            System.out.println();
            System.out.println("|||||||||||||||||");
            System.out.println("|||||||||||||||||");


            s1 = new StatsPrinter(n, TRIALS, new BadNaive(n, alpha, beta, rpc, 50));
            s4 = new StatsPrinter(n, TRIALS, new SyntheticSamples(n, alpha, beta, rpc, 10));
        }

        s1.printStatsForStrategy();
        s4.printStatsForStrategy();

    }
}
