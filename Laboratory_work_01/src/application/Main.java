package application;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;

public class Main {


    public static void main(String[] args) {
        Occurencies occurencies = new OccurenciesResource();
        occurencies.getOccurencies(ReadFromFile.getSources(), ReadFromFile.getWords(), ReadFromFile.resultFile);


    }

}
