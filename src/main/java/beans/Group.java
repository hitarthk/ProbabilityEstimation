package beans;

import java.util.ArrayList;

/**
 * Created by hitarth.k on 31/07/18.
 */
public class Group {
    public ArrayList<AtomParameters> atoms;

    public Group() {
        atoms = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Group{" +
                "atoms=" + atoms +
                " meanl2a=" + getMeanL2a() +
                '}';
    }

    private double getMeanL2a() {
        double alpha = 0;
        double beta = 0;
        for (AtomParameters atom : atoms) {
            alpha += atom.alpha;
            beta += atom.beta;
        }
        return alpha / (alpha + beta);
    }

}
