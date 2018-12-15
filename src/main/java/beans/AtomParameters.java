package beans;

/**
 * Created by hitarth.k on 31/07/18.
 */
public class AtomParameters {
    public double alpha;
    public double beta;

    public AtomParameters(double alpha, double beta) {
        this.alpha = alpha;
        this.beta = beta;
    }

    public void combineParameters(AtomParameters atomParameters) {
        this.alpha += atomParameters.alpha;
        this.beta += atomParameters.beta;
    }

    public AtomParameters() {

    }

    @Override
    public String toString() {
        return "AtomParameters{" +
                "alpha=" + alpha +
                ", beta=" + beta +
                ", l2a=" + ((alpha) / (alpha + beta)) +
                '}';
    }
}
