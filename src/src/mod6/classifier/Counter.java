package mod6.classifier;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Tycho on 12/2/15.
 */
public class Counter {

    private LinkedList<String> documentWords;
    private HashMap<Integer, String> map;

    public Counter(LinkedList<String> in) {
        this.documentWords = in;
    }

    public HashMap<Integer, String> countWords(){
        int count = 0;
        while(this.documentWords.size() > 0) {
            for(String words : this.documentWords) {
                count = Collections.frequency(this.documentWords, words);
                this.documentWords.removeAll(Collections.singleton(words));
                map.put(count, words);
            }
        }
        return this.map;
    }

}
