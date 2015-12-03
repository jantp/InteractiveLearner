package mod6.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jan on 12/3/2015.
 */
public class Trainer {
    private HashMap<String, List<String>> cats;
    private HashMap<String, List<String>> countedCats;
    private Vocab vocab;
    public Trainer() {
        cats = new HashMap<String, List<String>>();
        vocab = new Vocab();
    }

    public void addCat (String catName) {
        cats.put(catName, new ArrayList<String>());
    }

    public void addToTrainer (List<String> document, String cat) {
        document.addAll(cats.get(cat));
        cats.put(cat, document);
    }

    public HashMap<String, List<String>> getCats () {
        return cats;
    }
}
