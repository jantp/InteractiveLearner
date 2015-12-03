package mod6.classifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Jan on 12/2/2015.
 */
public class Classifier {

    private HashMap<String, Integer> content;
    private double a = 1.0;
    /**
     * The constructor of the class used to classify documents
     */
    public Classifier (HashMap<String, Integer> in) {
        this.content = in;
    }

    public String classify (HashMap<String, Integer> document,
                            HashMap<String, Double> cat1,
                            HashMap<String, Double> cat2,
                             HashMap<String, Double> catSizes) {
        double cat1Val = (double) catSizes.get("1") / (double) catSizes.get("total");
        double cat2Val = (double) catSizes.get("2") / (double) catSizes.get("total");
        double p1 = cat1Val;
        double p2 = cat2Val;
        Iterator docIt = document.entrySet().iterator();
        while (docIt.hasNext()) {
            Map.Entry pair = (Map.Entry)docIt.next();
            if (cat1.get(pair.getKey()) != null && cat1.get(pair.getKey()) > 0.01) {

                //p1 += ((double)cat1.get(pair.getKey()) + a) / (sum1 + a * (double)cat1.size());
                //p1 = p1 + (double)Math.pow((double) (double)cat1.get(pair.getKey()), (double)(int)(Integer) pair.getValue());
                p1 = p1 + cat1.get(pair.getKey()) * (double)(int)(Integer) pair.getValue();
            }
            if (cat2.get(pair.getKey()) != null && cat2.get(pair.getKey()) > 0.01) {
                //p2 += ((double)cat2.get(pair.getKey()) + a) / (sum2 + a * (double)cat2.size());
                //p2 = p2 + Math.pow(cat2.get(pair.getKey()), (double)(int)(Integer) pair.getValue());
                p2 = p2 + cat2.get(pair.getKey()) * (double)(int)(Integer) pair.getValue();
            }
            //System.out.println("-----------P1: "+p1);
            //System.out.println("-----------P2: "+p2);
        }
        return (p1 > p2 ? "F" : "M");
    }
}
