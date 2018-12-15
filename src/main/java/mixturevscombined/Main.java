package mixturevscombined;

import beans.AtomParameters;
import beans.Group;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.Reader;
import java.util.Arrays;

/**
 * Created by hitarth.k on 31/07/18.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Group[] groups = getGroupsFromJSON();
        System.out.println(Arrays.toString(groups));

        double[] perc = new CombinedStrategy().getPercentagesNaive(groups);
        System.out.println("Combined strategy: ");
        System.out.println(Arrays.toString(perc));

        System.out.println("Mixture Strategy: ");
        perc = new MixtureStrategy().getPercentagesNaive(groups);
        System.out.println(Arrays.toString(perc));
    }

    static Group[] getGroupsFromJSON() throws Exception {
        String path = "/Users/hitarth.k/IdeaProjectsAgain/ProbabilityEstimation/src/main/java/mixturevscombined/input.txt";
        Reader reader = new FileReader(path);
        Gson gson = new Gson();
        GInput[] gInputs = gson.fromJson(reader, GInput[].class);
        Group[] groups = new Group[gInputs.length];
        for (int i = 0; i < gInputs.length; i++) {
            if (gInputs[i].impressions.length != gInputs[i].conversions.length) {
                throw new Exception("Impressions and conversions length should be same!!");
            }
            groups[i] = new Group();
            for (int j = 0; j < gInputs[i].impressions.length; j++) {
                double alpha = gInputs[i].conversions[j];
                double beta = gInputs[i].impressions[j] - alpha;
                AtomParameters atom = new AtomParameters(alpha, beta);
                groups[i].atoms.add(atom);
            }
        }
        return groups;
    }

}
