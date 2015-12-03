package mod6.utils;

import mod6.classifier.Counter;

import java.io.File;
import java.util.*;

/**
 * Created by Jan on 12/3/2015.
 */
public class DocumentLoader {
    private Trainer trainer;
    private TextTokenizer tokenizer;
    public DocumentLoader() {
        trainer = new Trainer();
        tokenizer = new TextTokenizer();
    }

    public void loadTrainingDirs () {
        File trainingDir  = new File("C://bit/mod6/training/");
        for (File catDir : trainingDir.listFiles()) {
            if (catDir.isDirectory()) {
                String catName = catDir.getName();
                trainer.addCat(catName);
                loadTrainingFiles(catDir);
            }
        }

    }

    public void loadTrainingFiles (File catDir) {
        LinkedList<String> currCat = new LinkedList<String>();
        for (File trainingFile : catDir.listFiles()) {
            String trainingFileName = trainingFile.getName();
            String extension = trainingFileName.substring(trainingFileName.lastIndexOf(".") + 1, trainingFileName.length());
            if (!trainingFile.isDirectory() && (extension.equals("txt"))) {
                currCat.addAll(tokenizer.tokenizeDocument(trainingFile));
            }
        }
        Counter counter = new Counter(currCat);
        counter.countWords();
    }

    public void countWords () {
        HashMap<String, List<String>> cats = trainer.getCats();
        Iterator catsIt = cats.entrySet().iterator();
        while (catsIt.hasNext()) {
            Map.Entry cat = (Map.Entry) catsIt.next();
            String catName = (String) cat.getKey();

        }
    }
}
