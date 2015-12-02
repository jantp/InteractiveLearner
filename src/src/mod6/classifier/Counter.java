package mod6.classifier;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Tycho on 12/2/15.
 */
public class Counter {

    private LinkedList<String> documentWords;
    private HashMap<String, Integer> indexes;

    public Counter(LinkedList<String> in) {
        this.documentWords = in;
    }

    public HashMap<String, Integer> countWords(){
        indexes = new HashMap<>();
        int count = 0;
        LinkedList<String> frontier = new LinkedList<>();
        while(this.documentWords.size() > 0) {
            for(String words : this.documentWords) {
                if (!(frontier.contains(words))) {
                    count = Collections.frequency(this.documentWords, words);
                    indexes.put(words, count);
                    System.out.println(this.indexes.toString());
                    frontier.add(words);
                }
            }
        }
        return this.indexes;
    }

}
