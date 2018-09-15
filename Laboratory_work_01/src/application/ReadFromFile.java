package application;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadFromFile {

    private static String sourcesFile = "src\\res\\Sources.txt";
    private static String wordsFile = "src\\res\\Words.txt";
    public static final String resultFile = "src\\res\\ResultOfSearch.txt";


    /**
     * @return массив адресов ресурсов
     */
    public static String[] getSources(){
        return getArrayFromFile(sourcesFile);
    }

    /**
     * @return массив слов
     */
    public static String[] getWords(){
        String[] lines = getArrayFromFile(wordsFile);
        ArrayList<String> wordList = new ArrayList<>();
        for (String wordLine : lines){
            wordLine = wordLine.replaceAll("\\p{Punct}", "").replaceAll("\n", "");
            wordLine = wordLine.toLowerCase();
            String[] arrayWords = wordLine.split(" ");
            wordList.addAll(Arrays.asList(arrayWords));
        }
        String[] wordsAray = new String[wordList.size()];
        wordList.toArray(wordsAray);

        return  wordsAray;
    }


    /**
     * @param file имя файла
     * @return массив строк c файла
     */
    private static String[] getArrayFromFile(String file) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader
                (new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] sources = new String[lines.size()];
        lines.toArray(sources);

        return sources;
    }


    /**
     * назначение sourcesFile
     * @param sourcesFile имя файла с описанием адресов ресурсов
     */
    public static void setSourcesFile(String sourcesFile) {
        ReadFromFile.sourcesFile = sourcesFile;
    }

    /**
     * назначение wordsFile
     * @param wordsFile имя файла с искоемыми словами
     */
    public static void setWordsFile(String wordsFile) {
        ReadFromFile.wordsFile = wordsFile;
    }
}
