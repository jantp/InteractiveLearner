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
    private String words;

    public Counter(LinkedList<String> in) {
        this.documentWords = in;
    }

    public HashMap<String, Integer> countWords(){
        indexes = new HashMap<String, Integer>();
        int count = 0;
        LinkedList<String> frontier = new LinkedList<String>();
        while(this.documentWords.size() > 0) {
            String words =  this.documentWords.getFirst();
            if (!(frontier.contains(words))) {
                count = Collections.frequency(this.documentWords, words);
                indexes.put(words, count);
                frontier.add(words);
                this.documentWords.removeAll(Collections.singleton(words));
            }
        }
        System.out.println(this.indexes.toString());
        return this.indexes;
    }

}
