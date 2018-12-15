package mixturevscombined;

import beans.AtomParameters;
import beans.Group;
import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.util.Incrementor;

import java.util.ArrayList;

/**
 * Created by hitarth.k on 31/07/18.
 */
public class CombinedStrategy {

    public double[] getPercentagesNaive(Group[] groups) {
        double[] percentages = new double[groups.length];

        AtomParameters[] combinedParameters = new AtomParameters[groups.length];

        for (int i = 0; i < combinedParameters.length; i++) {
            AtomParameters atom = new AtomParameters();
            for (AtomParameters internalAtom : groups[i].atoms) {
                atom.combineParameters(internalAtom);
            }
            combinedParameters[i] = atom;
        }


        int ITERATIONS = 10000;

        BetaDistribution[] betaDistributions = new BetaDistribution[combinedParameters.length];
        for (int i = 0; i < betaDistributions.length; i++) {
            betaDistributions[i] = new BetaDistribution(combinedParameters[i].alpha, combinedParameters[i].beta);
        }

        for (int iter = 0; iter < ITERATIONS; iter++) {
            int maxIndex = -1;
            double maxValue = -1;
            for (int j = 0; j < betaDistributions.length; j++) {
                double sampleValue = betaDistributions[j].sample();
                if (sampleValue > maxValue) {
                    maxIndex = j;
                    maxValue = sampleValue;
                }
            }
            percentages[maxIndex]++;
        }

        for (int i = 0; i < betaDistributions.length; i++) {
            percentages[i] /= ITERATIONS/100D;
        }

        return percentages;
    }
}