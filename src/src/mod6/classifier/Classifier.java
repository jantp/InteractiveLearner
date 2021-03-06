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
            probs[i] = 0.0;
            for (Map.Entry<String, Integer> pair : doc.entrySet()) {
                Integer countCat = vocs[i].getCount(pair.getKey());
                Integer countBoth = countTotal(vocs, pair.getKey());
                //tf
                double totalTerms = 0.0;
                for (double f : doc.values()) {
                    totalTerms += f;
                }
                double tf = pair.getValue() / totalTerms;
                // idf
                int totalDocs = 1;
                for(HashMap<String, Integer> tindoc : vocs[i].getDocumentList()) {
                    if (tindoc.containsKey(pair.getKey())){
                        totalDocs++;
                    }
                }
                double idf = Math.log(getTotalDocs(vocs) / totalDocs);
                double tfidf = tf*idf;
                // end of feature selection
                prob = ((countCat + smoothing)/(countBoth + vocs[i].getDocuments()));
                probs[i] += (Math.log10(prob) / Math.log(2)) * tfidf;
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
        int max = 0;
        for (int i = 0; i < probs.length; i++) {
            if (probs[max] < probs[i]) max = i;
        }
        return vocs[max].getName();
    }
}
