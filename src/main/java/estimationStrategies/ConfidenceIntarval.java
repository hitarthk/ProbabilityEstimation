package estimationStrategies;

/**
 * Created by hitarth.k on 13/02/18.
 */
public class ConfidenceIntarval extends EstimationStrategy {
    double precision;

    public ConfidenceIntarval(int n, double[] alpha, double[] beta, double[] rpc, double precision) {
        super(n, alpha, beta, rpc);
        this.precision = precision;
    }

    public ConfidenceIntarval(int n, double[] alpha, double[] beta, double precision) {
        super(n, alpha, beta);
        this.precision = precision;
    }

    public double[] estimateProbability() {
        double[] perc = new double[n];
        double l = 0;
        double r = 1;
        double[] ubound = new double[n];

        while(r-l>precision){
            double mid = (l+2)/2;

        }
        return perc;
    }
}
