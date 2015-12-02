package mod6.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextTokenizer {
    public TextTokenizer () {

    }

    public String loadDocument (File trainingFile) {
        try {
            FileReader fr = new FileReader(trainingFile);
            BufferedReader br = new BufferedReader(fr);
            String document = "";
            while (br.readLine() != null) {
                document += br.readLine();
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
    public LinkedList<String> tokenizeDocument (File trainingFile) {
        String document = loadDocument(trainingFile).toLowerCase();
        String[] words = document.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        LinkedList<String> tokenizedDoc = new LinkedList<String>(Arrays.asList(words));
        return tokenizedDoc;
    }
}

