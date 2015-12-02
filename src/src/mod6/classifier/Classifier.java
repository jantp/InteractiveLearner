package mod6.classifier;

import java.util.HashMap;

/**
 * Created by Jan on 12/2/2015.
 */
public class Classifier {

    private HashMap<Integer, String> content;
    /**
     * The constructor of the class used to classify documents
     */
    public Classifier (HashMap<Integer, String> in) {
        this.content = in;
    }
}
