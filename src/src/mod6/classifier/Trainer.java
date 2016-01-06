package mod6.classifier;

import mod6.utils.TextTokenizer;
import mod6.utils.Vocabulary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
    private String[] classes;

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
            notifyObservers("progress -//- Reading documents -//- 5");
            File trainingDir  = new File(this.trainingdir+"/training/");
            File testingDir = new File(this.trainingdir+"/testing/");
            if (!trainingDir.isDirectory() || !testingDir.isDirectory()){
                throw new FileNotFoundException();
            } else {
                this.classes = this.getDirs(trainingDir);
                vocs = new Vocabulary[classes.length];
                int i = 0;
                int progress = Math.round(90.0f / classes.length);
                for (File catDir : trainingDir.listFiles()) {
                    if (catDir.isDirectory() && i < vocs.length) {
                        String catName = catDir.getName();
                        setChanged();
                        notifyObservers("progress -//- Training " + catName + " -//- " + progress);
                        this.vocs[i] = new Vocabulary(catName);
                        for (File trainingFile : catDir.listFiles()) {
                            String trainingFileName = trainingFile.getName();
                            String extension = trainingFileName.substring(trainingFileName.lastIndexOf(".") + 1, trainingFileName.length());
                            if (!trainingFile.isDirectory() && (extension.equals("txt"))) {
                                this.addDocument(trainingFile, i);
                            }
                        }
                        i++;
                    }
                }
                File[] files = testingDir.listFiles();
                setChanged();
                notifyObservers("progress -//- Classifying files -//- 0");
                setChanged();
                this.notifyObservers(files);
            }
        } catch (FileNotFoundException e) {
            setChanged();
            notifyObservers("progress -//- The directory is invalid -//- 0");
        }

    }

    public String[] getClasses () {
        return this.classes;
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

    public String[] getDirs (File dir) {
        List<String> dirs = new ArrayList<String>();
        for (File sub : dir.listFiles()) {
            if (sub.isDirectory()) dirs.add(sub.getName());
        }
        String[] dirArr = new String[dirs.size()];
        dirArr = dirs.toArray(dirArr);
        return dirArr;
    }

    public Vocabulary[] getVocs () {
        return this.vocs;
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
