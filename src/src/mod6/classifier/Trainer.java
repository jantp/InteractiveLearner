package mod6.classifier;

import mod6.utils.TextTokenizer;
import mod6.utils.Vocabulary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * Created by Jan on 12/31/2015.
 */
public class Trainer extends Observable implements Runnable {
    /*
        An array of vocabularies;
        voc[0] = the total vocabulary,
        voc[1] = the first vocabulary,
        voc[2] = the second vocabulary
     */

    private Vocabulary[] vocs = new Vocabulary[2];
    private String trainingdir;
    private Integer smoothing = 1;

    public Trainer (String trainingdir) {
        this.trainingdir = trainingdir;
        //this.readDocuments(this.trainingdir);
    }

    public void run () {
        this.readDocuments();
    }

    public void readDocuments () {
        try {
            setChanged();
            notifyObservers("progress -//- Reading documents");
            File trainingDir  = new File(this.trainingdir+"/training/");
            File testingDir = new File(this.trainingdir+"/testing/");
            if (!trainingDir.isDirectory() || !testingDir.isDirectory()){
                throw new FileNotFoundException();
            }
            vocs = new Vocabulary[this.getNumDirs(trainingDir)];
            int i = 0;
            for (File catDir : trainingDir.listFiles()) {
                if (catDir.isDirectory() && i < vocs.length) {
                    String catName = catDir.getName();
                    setChanged();
                    notifyObservers("progress -//- Training "+catName);
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
            File[] files = testingDir.listFiles();
            //classifyFile(testingDir);
            setChanged();
            notifyObservers("progress -//- Classifying files");
            setChanged();
            this.notifyObservers(files);
            //System.out.println("Done");
        } catch (FileNotFoundException e) {
            setChanged();
            notifyObservers("progress -//- The directory is invalid ");
        }

    }

    public void classifyFile (File testFile) {
        System.out.println("classifying 2");

        if (!testFile.isDirectory()) {
            System.out.println("classifying 3");

            Counter counter = new Counter(TextTokenizer.tokenizeDocument(testFile));
            HashMap<String, Integer> testDoc = counter.countWords();
            String classification = Classifier.testDocument(this.vocs, testDoc);
            setChanged();
            notifyObservers("class -//- " + classification);
        }
    }

    public void addDocument (File trainingFile, int i) {
        HashMap<String, Integer> wordCounter = new Counter(TextTokenizer.tokenizeDocument(trainingFile)).countWords();
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
        return (getIndex(name) == 1 ? 1 : 0);
    }

    public int getNumDirs (File dir) {
        int numSubs = 0;
        for (File sub : dir.listFiles()) {
            if (sub.isDirectory()) numSubs++;
        }
        return numSubs;
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
