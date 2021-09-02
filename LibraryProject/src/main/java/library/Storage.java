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

    boolean foundLine(Person human, Book book) throws FileNotFoundException { //eсть ли этот id в файле
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(Integer.toString(human.getIdPerson())) && line.contains(Integer.toString(book.getId()))) {
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

    String[][] scannerFileReturnBuffer(int id) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String[][] buffer = new String[countLines() - 1][5];
        int j = 0;
        while (scanner.hasNextLine() && j < buffer.length) {
            String line = scanner.nextLine();
            if (!line.contains(Integer.toString(id))) {
                String[] splitLine = line.split(", ");
                System.arraycopy(splitLine, 0, buffer[j], 0, buffer[j].length);
                j++;
            }
        }
        scanner.close();
        return buffer;
    }

    String scannerFileReturnLine(String authorOrName) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        int i = 0;
        StringBuilder findLine = new StringBuilder();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String lowerLine = line.toLowerCase();
            if (lowerLine.contains(authorOrName.toLowerCase())) {
                findLine.append(line).append('\n');
                i++;
            }
        }
        scanner.close();
        if (i == 0) {
            return null;
        } else {
            return findLine.toString();
        }
    }

    String[] scannerFileReturnArray(Book book) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String[] splitLineBook = new String[0];
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(Integer.toString(book.getId()))) {
                splitLineBook = line.split(", ");
            }
        }
        scanner.close();
        return splitLineBook;
    }

    String[] scannerFileReturnArray(Person human, Book book) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String[] splitLineBook = new String[0];
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(Integer.toString(book.getId())) && line.contains(Integer.toString(human.getIdPerson()))) {
                splitLineBook = line.split(", ");
            }
        }
        scanner.close();
        return splitLineBook;
    }

    String scannerFileSkipLine(Person human, Book book) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        StringBuilder writeLine = new StringBuilder();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!(line.contains(Integer.toString(book.getId())) && line.contains((Integer.toString(human.getIdPerson()))))) {
                writeLine.append(line).append('\n');
            }
        }
        scanner.close();
        return writeLine.toString();
    }
}

