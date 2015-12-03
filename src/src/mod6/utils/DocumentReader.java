package mod6.utils;

import mod6.classifier.Counter;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by Jan on 12/2/2015.
 */
public class DocumentReader {
    private TextTokenizer tokenizer = new TextTokenizer();

    public DocumentReader () {
        File trainingData = new File("C://bit/mod6/training/1/");
        for (File trainingFile : trainingData.listFiles()) {
            String trainingFileName = trainingFile.getName();
            String extension = trainingFileName.substring(trainingFileName.lastIndexOf(".") + 1, trainingFileName.length());
            System.out.println(trainingFileName);

            if (!trainingFile.isDirectory() && (extension.equals("txt"))) {
                this.activateTrainer(tokenizer.tokenizeDocument(trainingFile), 1);
            }
        }
    }

    public void activateTrainer (LinkedList<String> document, int group) {
        Counter counter = new Counter(document);
        counter.countWords();
        //System.out.println(counter.countWords().toString());
    }

    public static void main (String[] args) {
        new DocumentReader();
    }
}

