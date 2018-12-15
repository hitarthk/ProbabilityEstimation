package mixturevscombined;

import java.util.Arrays;

/**
 * Created by hitarth.k on 31/07/18.
 */
public class GInput {
    double[] impressions;
    double[] conversions;


    public void setImpressions(double[] impressions) {
        this.impressions = impressions;
    }

    public void setConversions(double[] conversions) {
        this.conversions = conversions;
    }

    public double[] getImpressions() {

        return impressions;
    }

    public double[] getConversions() {
        return conversions;
    }

    public GInput(double[] impressions, double[] conversions) {

        this.impressions = impressions;
        this.conversions = conversions;
    }

    @Override
    public String toString() {
        return "GInput{" +
                "impressions=" + Arrays.toString(impressions) +
                ", conversions=" + Arrays.toString(conversions) +
                '}';
    }
}
