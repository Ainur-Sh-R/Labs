package application;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String sources1 = "E:\\Обучение\\Java\\Иннополис курс\\Test\\test\\testSet\\less1GB\\";
        String sources2 = "E:\\Обучение\\Java\\Иннополис курс\\Test\\test\\testSet\\more1GB\\";
        String sources3 = "E:\\Обучение\\Java\\Иннополис курс\\Test\\test\\testSet\\myField\\";

        /** Назначение папки с ресурсами*/
        ReadFromFile.writeToSourcesFile(sources1);

        Occurencies occurencies = new OccurenciesResource();
        try {
            occurencies.getOccurencies(ReadFromFile.getSources(), ReadFromFile.getWords(), ReadFromFile.resultFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
