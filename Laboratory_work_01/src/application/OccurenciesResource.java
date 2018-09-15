package application;

import java.io.*;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class OccurenciesResource implements Occurencies {
    static AtomicInteger countSource = new AtomicInteger();

    //разделить вызов метода getOccurencies по потокам

    @Override
    public void getOccurencies(String[] sources, String[] words, String res) {
        clearFile(res);
        HashSet<String> wSet = new HashSet<>(Arrays.asList(words));

        for (String source : sources) {
            startTheSearch(source, wSet);
        }
    }

    private void startTheSearch(String source, HashSet<String> wordsSet) {
        StringBuffer sentenceBuffer = new StringBuffer();
        boolean containsWord = false;
        int pointIndex;

        Scanner scannerSource = getScannerSource(source);
        while (scannerSource.hasNext()) {
            String readWord = scannerSource.next();

            if ((pointIndex = endSentence(readWord)) > 1) {
                String firstWord = readWord.substring(0, pointIndex + 1);
                String secondWord = readWord.substring(pointIndex + 1).trim();
                containsWord = isHaveWord(firstWord, wordsSet);

                sentenceBuffer.append(readWord.substring(0, pointIndex + 1) + " ");

                if (containsWord) {
                    writeToResultFile(sentenceBuffer);
                }

                sentenceBuffer.setLength(0);
                sentenceBuffer.append(secondWord);
            } else {
                sentenceBuffer.append(readWord + " ");
            }
        }
        scannerSource.close();

    }

    /**
     * @param source адрес ресурса
     * @return Scanner c потоком для чтения ресурса
     */
    public Scanner getScannerSource(String source) {
        boolean httpBollean = (source.trim().indexOf("http")) == 0;
        boolean ftpBoollean = (source.trim().indexOf("http")) == 0;
        Scanner sourceReader = null;
        if (httpBollean || ftpBoollean) {
            try {
                sourceReader = new Scanner(new URL(source).openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                sourceReader = new Scanner(new File(source));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return sourceReader;
    }

    /**
     * выполняет очистку файла
     *
     * @param filename имя файла
     */
    private void clearFile(String filename) {
        FileOutputStream writer = null;
        try {
            writer = new FileOutputStream(filename);
            writer.write(("Произведена очистка файла" + "\n").getBytes());
            System.out.println("Произведена очистка файла");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * записывает предложение в результирующий файл
     *
     * @param string записываемое предложение
     */
    public void writeToResultFile(StringBuffer string) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(ReadFromFile.resultFile), true))) {
            writer.write(String.valueOf(string) + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * проверяет строку на наличие символов '.', '!', '?'
     *
     * @param word проверяемая строка
     * @return индекс символа конца предложения
     */
    private int endSentence(String word) {
        int pointIndex;
        if ((pointIndex = word.indexOf(".")) > -1 ||
                (pointIndex = word.indexOf("!")) > -1
                || (pointIndex = word.indexOf("?")) > -1) {
            return pointIndex;
        } else {
            return -1;
        }
    }

    /**
     * @param word проверяемое слово
     * @return true, если слово есть в списке искоемых слов, иначе false
     */
    private boolean isHaveWord(String word, HashSet<String> set) {
        word = word.replaceAll("\\p{Punct}", "").replaceAll("\n", "");
        String lowWord = word.toLowerCase();
        return set.contains(lowWord);
    }


}
