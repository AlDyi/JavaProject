package library;

import java.io.*;
import java.util.Scanner;

public class Storage {
    private File file;
    private String fileName;

    Storage (String fileName) {
        file = new File(fileName);
        this.fileName = fileName;
    }

    File getFile() {
        return file;
    }

    String getFileName() {
        return fileName;
    }

    void writeToStorage(String writeLine, boolean append) throws IOException { //запись в файл
        FileWriter fileWriter = new FileWriter(file, append);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(writeLine);
        bufferedWriter.close();
    }

    int countLines() throws FileNotFoundException { //посчитать количество строк в файле
        Scanner scanner = new Scanner(file);
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
                i++;
        }
        scanner.close();
        return i;
    }

    boolean foundLine(int id) throws FileNotFoundException { //eсть ли этот id в файле
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(Integer.toString(id))) {
                return true;
            }
        }
        scanner.close();
        return false;
    }

    String returnFoundLine(int id) throws FileNotFoundException { //eсть ли этот id в файле
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(Integer.toString(id))) {
                return line;
            }
        }
        scanner.close();
        return null;
    }
}
