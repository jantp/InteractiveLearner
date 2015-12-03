package mod6.utils;

import java.util.ArrayList;

/**
 * Created by Jan on 12/3/2015.
 */
public class Vocab {
    private ArrayList<String> vocabList = new ArrayList<String>();
    public Vocab () {

    }

    public void addToVocab (String word) {
        vocabList.add(word);
    }
}
