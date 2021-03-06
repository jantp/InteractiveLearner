package mod6.utils;

import mod6.classifier.Classifier;
import mod6.classifier.Counter;

import java.io.File;
import java.util.*;

/**
 * Created by Jan on 12/2/2015.
 */
public class DocumentReader {
    /*private TextTokenizer tokenizer = new TextTokenizer();
    private HashMap catSizes = new HashMap<String, Double>();
    private HashMap<String, HashMap<String, Integer>> cats = new HashMap<String, HashMap<String, Integer>>();
    private HashMap<String, HashMap<String, Integer>> voc = new HashMap<String, HashMap<String, Integer>>();
    private String[] catNames = new String[2];

    public DocumentReader (String documentFolder) {
        File trainingDir  = new File(documentFolder+"/training/");
        this.cats.put("total", new HashMap<String, Integer>());
        catSizes.put("total", 0.0);
        System.out.println("Training the classifier");
        int i = 0;
        for (File catDir : trainingDir.listFiles()) {
            if (catDir.isDirectory() && i < catNames.length) {
                String catName = catDir.getName();
                catNames[i] = catName;
                i++;
                catSizes.put(catName, 0.0);
                this.cats.put(catName, new HashMap<String, Integer>());
                for (File trainingFile : catDir.listFiles()) {
                    String trainingFileName = trainingFile.getName();
                    String extension = trainingFileName.substring(trainingFileName.lastIndexOf(".") + 1, trainingFileName.length());
                    if (!trainingFile.isDirectory() && (extension.equals("txt"))) {
                        this.trainClassifier(tokenizer.tokenizeDocument(trainingFile), catName);
                    }
                }
            }
        }
        this.createVocabulary();
        System.out.println("Classifying documents");
        File testingDir = new File(documentFolder+"/testing/");
        classifyFile(testingDir);
        System.out.println("Done");
    }

    public void classifyFile (File testingDir) {
        HashMap<String, HashMap<String, Double>> condProbCats = this.condProb();
        for (File testFile : testingDir.listFiles()) {
            if (!testFile.isDirectory()) {
                Counter counter = new Counter(tokenizer.tokenizeDocument(testFile));
                HashMap<String, Integer> testDoc = counter.countWords();
                Classifier classifier = new Classifier(testDoc);
                String classification = classifier.classify(testDoc, condProbCats.get(catNames[0]), condProbCats.get(catNames[1]), catSizes, catNames);
                System.out.println(testFile.getName()+": "+classification);
            }
        }

    }

    public double getIDF (String term) {
        if (cats.get("total").get(term) == 0) {
            return 0;
        } else {
            return Math.log10((Double) catSizes.get("total") / (double) cats.get("total").get(term));
        }
    }

    public HashMap<String, HashMap<String, Double>> condProb () {
        HashMap<String, HashMap<String, Double>> condProbCats = new HashMap<String, HashMap<String, Double>>();
        Iterator catsIt = voc.entrySet().iterator();
        while (catsIt.hasNext()) {
            Map.Entry pair = (Map.Entry)catsIt.next();
            String catName = (String) pair.getKey();
            condProbCats.put(catName, new HashMap<String, Double>());
            HashMap<String, Integer> currCat = (HashMap<String, Integer>) pair.getValue();
            double countC = 0.0;
            for (int f : currCat.values()) {
                countC += (double)f;
            }
            double voc = currCat.keySet().size();
            Iterator wordsIt = currCat.entrySet().iterator();
            while (wordsIt.hasNext()) {
                Map.Entry wordPair = (Map.Entry)wordsIt.next();
                String word = (String) wordPair.getKey();
                double wordValue = (double) ((Integer) wordPair.getValue()).intValue();
                double wordProb = getIDF(word) * ((wordValue + 1.0) / (countC + voc));
                condProbCats.get(catName).put(word, wordProb);
            }
        }
        return condProbCats;
    }


    public void trainClassifier (LinkedList<String> document, String catName) {
        Counter counter = new Counter(document);
        HashMap<String, Integer> docCounted = counter.countWords();
        Iterator it = docCounted.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if (this.cats.get(catName).get(pair.getKey()) != null) {
                this.cats.get(catName).put((String) pair.getKey(), this.cats.get(catName).get(pair.getKey())+(Integer) pair.getValue());
            } else {
                this.cats.get(catName).put((String) pair.getKey(), (Integer) pair.getValue());
            }
            if (this.cats.get("total").get(pair.getKey()) != null) {
                this.cats.get("total").put((String) pair.getKey(), this.cats.get("total").get(pair.getKey())+1);
            } else {
                this.cats.get("total").put((String) pair.getKey(), 1);
            }

            it.remove();
        }
        catSizes.put(catName, (Double) catSizes.get(catName) + 1);
        catSizes.put("total", (Double) catSizes.get("total") + 1);
    }

    public void createVocabulary () {
        Iterator catsIt = cats.entrySet().iterator();
        while (catsIt.hasNext()) {
            Map.Entry pair = (Map.Entry)catsIt.next();
            String catName = (String) pair.getKey();
            voc.put(catName, new HashMap<String, Integer>());
            Iterator wordsIt = cats.get(catName).entrySet().iterator();
            while (wordsIt.hasNext()) {
                Map.Entry wordPair = (Map.Entry)wordsIt.next();
                if((Integer)wordPair.getValue() > 3) {
                    this.voc.get(catName).put((String)wordPair.getKey(), (Integer)wordPair.getValue());
                }
            }
        }

    }

    public static void main (String[] args) {
        System.out.println("Started");
        if (args.length != 1) {
            System.out.println("Usage: \"DocumentReader.java /path/to/dataset/folder\"");
        } else {
            new DocumentReader(args[0]);
        }
    }*/
}

