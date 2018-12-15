package mixturevscombined;

import beans.Group;
import org.apache.commons.math3.distribution.BetaDistribution;

/**
 * Created by hitarth.k on 31/07/18.
 */
public class MixtureStrategy {
    public double[] getPercentagesNaive(Group[] groups) {
        double[] percentages = new double[groups.length];

        int ITERATIONS = 10000;

        for (int iter = 0; iter < ITERATIONS; iter++) {
            int maxIndex = -1;
            double maxValue = -1;
            for (int i = 0; i < groups.length; i++) {
                int j = selectTemplateFromGroup(groups[i]);
                BetaDistribution distribution = new BetaDistribution(groups[i].atoms.get(j).alpha, groups[i].atoms.get(j).beta);
                double sample = distribution.sample();
                if (sample > maxValue) {
                    maxValue = sample;
                    maxIndex = i;
                }
            }
            percentages[maxIndex]++;
        }

        for (int i = 0; i < percentages.length; i++) {
            percentages[i] /= ITERATIONS/100D;
        }

        return percentages;
    }

    private int selectTemplateFromGroup(Group group) {
        double[] p = new double[group.atoms.size()];
        double normaliser = 0;
        for (int i = 0; i < p.length; i++) {
            p[i] = group.atoms.get(i).alpha + group.atoms.get(i).beta;
            normaliser += p[i];
        }
        for (int i = 0; i < p.length; i++) {
            p[i] /= normaliser;
        }

        double r = Math.random();
        double sum = 0;
        for (int i = 0; i < p.length; i++) {
            sum += p[i];
            if (r < sum) {
                return i;
            }
        }
        return p.length - 1;
    }

}
