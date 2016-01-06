package mod6.classifier;

import java.io.*;
import java.util.*;

/**
 * Created by Tycho on 12/2/15.
 */
public class Counter {

    private LinkedList<String> documentWords;
    private HashMap<String, Integer> indexes;
    private String words;
    private Set<String> stopwords = new HashSet<>();

    public Counter(LinkedList<String> in) {
        this.documentWords = in;
    }

    public HashMap<String, Integer> countWords(){
        indexes = new HashMap<String, Integer>();
        int count = 0;
        LinkedList<String> frontier = new LinkedList<String>();
        this.loadStopwords(new File("/Users/Tycho/Documents/School/Module\\ 6/AI_Interactive_Learner/InteractiveLearner/src/stopwords.txt"));
        while(this.documentWords.size() > 0) {
            String words =  this.documentWords.getFirst();
            if (!(frontier.contains(words)) && !stopwords.contains(words)) {
                count = Collections.frequency(this.documentWords, words);
                indexes.put(words, count);
                frontier.add(words);
                this.documentWords.removeAll(Collections.singleton(words));
            } else if (stopwords.contains(words)) {
                this.documentWords.removeAll(Collections.singleton(words));
                frontier.add(words);
            }
        }
        return this.indexes;
    }

    public String loadStopwords(File trainingFile) {
        try {
            FileReader fr = new FileReader(trainingFile);
            BufferedReader br = new BufferedReader(fr);
            String document = "";
            String line;
            while ((line = br.readLine()) != null) {
                stopwords.add(line);
            }
            return document;
        } catch(FileNotFoundException e) {
            System.err.println("File not found: "+trainingFile.getAbsolutePath());
            return "";
        } catch (IOException e) {
            System.err.println("IO error on file: "+trainingFile.getAbsolutePath());
            return "";
        }
    }

}
