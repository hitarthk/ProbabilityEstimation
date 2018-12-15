package beans;

/**
 * Created by hitarth.k on 10/02/18.
 */
public class Sample implements Comparable<Sample> {
    public double sampleValue;
    public int index;


    public Sample(double sampleValue, int index) {
        this.sampleValue = sampleValue;
        this.index = index;
    }

    public int compareTo(Sample that) {
        return -Double.compare(this.sampleValue, that.sampleValue);
    }


    @Override
    public String toString() {
        return "Sample{" +
                "sampleValue=" + sampleValue +
                ", index=" + index +
                '}';
    }
}