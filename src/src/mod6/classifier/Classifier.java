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
        Double[] probs = new Double[vocs.length];
        Double prob;
        for (int i = 0; i < vocs.length; i++) {
            System.out.println(i);
            probs[i] = 0.0;
            for (Map.Entry<String, Integer> pair : doc.entrySet()) {
                Integer countCat = vocs[i].getCount(pair.getKey());
                Integer countBoth = countTotal(vocs, pair.getKey());
                //TODO: add feature selection
                prob = (countCat + smoothing)/(countBoth + vocs[i].getDocuments());
                probs[i] += (Math.log10(prob) / Math.log(2));
            }
            probs[i] += (Math.log10(((double) vocs[i].getDocuments() / (getTotalDocs(vocs))))/Math.log(2));
        }
        return getMax(probs, vocs);
    }

    public static int countTotal (Vocabulary[] vocs, String key) {
        int totalCount = 0;
        for (int i = 0; i < vocs.length; i++) {
            totalCount += vocs[i].getCount(key);
        }
        return totalCount;
    }

    public static int getTotalDocs (Vocabulary[] vocs) {
        int totalCount = 0;
        for (int i = 0; i < vocs.length; i++) {
            totalCount += vocs[i].getDocuments();
        }
        return totalCount;

    }

    public static String getMax (Double[] probs, Vocabulary[] vocs) {
        //System.out.println(vocs[0].getName()+": "+probs[0] +" -- " +vocs[1].getName()+": "+probs[1] +" -- " +vocs[2].getName()+": "+probs[2] +" -- " );
        int max = 0;
        for (int i = 0; i < probs.length; i++) {
            if (probs[max] < probs[i]) max = i;
        }
        return vocs[max].getName();
    }

    public static int tfCalc() {
        return 0;
    }

    public static int idfCalc(Vocabulary[] vocs) {

        return 0;
    }
}
