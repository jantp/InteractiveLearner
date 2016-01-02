package mod6.classifier;

import mod6.utils.Vocabulary;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jan on 12/2/2015.
 */
public class Classifier {
    public static final double smoothing = 1.0;

    public static String testDocument (Vocabulary[] vocs, HashMap<String, Integer> doc) {
        Double[] probs = new Double[2];
        Double prob;
        for (int i = 1; i < vocs.length; i++) {
            probs[i-1] = 0.0;
            for (Map.Entry<String, Integer> pair : doc.entrySet()) {
                Integer countCat = vocs[i].getCount(pair.getKey());
                Integer countBoth = vocs[1].getCount(pair.getKey()) + vocs[2].getCount(pair.getKey());
                //TODO: add feature selection
                prob = (countCat + smoothing)/(countBoth + vocs[i].getDocuments());
                probs[i-1] += (Math.log10(prob)/Math.log(2));
            }
            probs[i-1] += (Math.log10(((double) vocs[i].getDocuments() / (vocs[1].getDocuments() + vocs[2].getDocuments())))/Math.log(2));
        }
        return ((probs[0] > probs[1] ? vocs[1].getName() : vocs[2].getName()));
    }
}
