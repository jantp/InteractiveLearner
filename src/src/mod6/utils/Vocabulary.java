package mod6.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jan on 12/31/2015.
 */
public class Vocabulary {
    private HashMap<String, Integer> occurences;
    private Integer documents;
    private String name;
    private List<HashMap<String, Integer>> documentList = new ArrayList<HashMap<String, Integer>>();

    public Vocabulary (String name) {
        this.name = name;
        this.documents = 0;
        this.occurences = new HashMap<String, Integer>();
    }

    public void addWord (String word, Integer wordCount) {
        if (occurences.keySet().contains(word)) {
            occurences.put(word, occurences.get(word) + wordCount);
        } else {
            occurences.put(word, wordCount);
        }
    }

    public String getName () {
        return this.name;
    }

    public void addDocument (HashMap<String, Integer> doc) {
        documents++;
        documentList.add(doc);
        for (Map.Entry<String, Integer> pair : doc.entrySet()) {
            addWord(pair.getKey(), pair.getValue());
        }
    }

    public Integer getCount (String word) {
        return (this.occurences.keySet().contains(word) ? this.occurences.get(word) : 0);
    }

    public List<HashMap<String, Integer>> getDocumentList () {
        return this.documentList;
    }

    public HashMap<String, Integer> getOccurences () {
        return occurences;
    }

    public Integer getDocuments () {
        return documents;
    }
}
