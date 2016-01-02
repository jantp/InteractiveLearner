package mod6.classifier;

import mod6.utils.TextTokenizer;
import mod6.utils.Vocabulary;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * Created by Jan on 12/31/2015.
 */
public class Trainer extends Observable {
    /*
        An array of vocabularies;
        voc[0] = the total vocabulary,
        voc[1] = the first vocabulary,
        voc[2] = the second vocabulary
     */

    private Vocabulary[] vocs = new Vocabulary[3];
    private String trainingdir;
    private Integer smoothing = 1;

    public Trainer (String trainingdir) {
        this.vocs[0] = new Vocabulary("total");
        this.vocs[1] = new Vocabulary("one.getName");
        this.vocs[2] = new Vocabulary("two.getName");

        this.trainingdir = trainingdir;
        //this.readDocuments(this.trainingdir);
    }

    public void readDocuments () {
        setChanged();
        notifyObservers("progress");
        File trainingDir  = new File(this.trainingdir+"/training/");
        System.out.println("Training the classifier");
        int i = 1;
        setChanged();
        notifyObservers("progress");
        for (File catDir : trainingDir.listFiles()) {
            if (catDir.isDirectory() && i < vocs.length) {
                String catName = catDir.getName();
                this.vocs[i] = new Vocabulary(catName);
                for (File trainingFile : catDir.listFiles()) {
                    String trainingFileName = trainingFile.getName();
                    String extension = trainingFileName.substring(trainingFileName.lastIndexOf(".") + 1, trainingFileName.length());
                    if (!trainingFile.isDirectory() && (extension.equals("txt"))) {
                        this.addDocument(trainingFile, i);
                    }
                }
            }
            i++;
        }
        System.out.println("Classifying documents");
        File testingDir = new File(this.trainingdir+"/testing/");
        File[] files = testingDir.listFiles();
        //classifyFile(testingDir);
        setChanged();
        this.notifyObservers(files);
        System.out.println("Done");
    }

    public void classifyFile (File testFile) {
        if (!testFile.isDirectory()) {
            Counter counter = new Counter(TextTokenizer.tokenizeDocument(testFile));
            HashMap<String, Integer> testDoc = counter.countWords();
            String classification = Classifier.testDocument(this.vocs, testDoc);
            setChanged();
            notifyObservers("class -//- " + classification);
        }
    }

    public void addDocument (File trainingFile, int i) {
        HashMap<String, Integer> wordCounter = new Counter(TextTokenizer.tokenizeDocument(trainingFile)).countWords();
        this.vocs[0].addDocument(wordCounter);
        this.vocs[i].addDocument(wordCounter);
    }

    public void addDocument (File trainingFile, String name) {
        this.addDocument(trainingFile, this.getIndex(name));
    }

    public int getIndex (String name) {
        for (int i = 0; i < this.vocs.length; i++) {
            if (this.vocs[i].getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public int getOpp (String name) {
        return (getIndex(name) == 1 ? 2 : 1);
    }

    public static void main (String[] args) {
        System.out.println("Started");
        if (args.length != 1) {
            System.out.println("Usage: \"Trainer.java /path/to/dataset/folder\"");
        } else {
            new Trainer(args[0]);
        }
    }
}
