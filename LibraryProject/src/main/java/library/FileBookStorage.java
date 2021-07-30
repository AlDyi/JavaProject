package library;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileBookStorage extends Storage implements BookStorage {

    public static final int ID = 0;
    public static final int NAME = 1;
    public static final int AUTHOR = 2;
    public static final int COPIES = 3;
    public static final int ISSUE = 4;

    FileBookStorage(String fileName) {
        super(fileName);
    }


    public String[][] scannerFileReturnBuffer(int id) throws FileNotFoundException { //(books)
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

    @Override
    public ArrayList<Book> scannerFileReturnList(String authorOrName) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        int i = 0;
        ArrayList<Book> foundBooks = new ArrayList<Book>();
        String[] buffer;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String lowerLine = line.toLowerCase();
            if (lowerLine.contains(authorOrName.toLowerCase())) {
                buffer = line.split(", ");
                foundBooks.add(new Book(Integer.parseInt(buffer[ID]), buffer[NAME], buffer[AUTHOR], Integer.parseInt(buffer[COPIES]), Integer.parseInt(buffer[ISSUE])));
                i++;
            }
        }
        scanner.close();
        if (i == 0) {
            return null;
        } else {
            return foundBooks;
        }
    }

//    String scannerFileReturnLine(String authorOrName) throws FileNotFoundException { // (Books)
//        Scanner scanner = new Scanner(file);
//        int i = 0;
//        StringBuilder findLine = new StringBuilder();
//        while (scanner.hasNextLine()) {
//            String line = scanner.nextLine();
//            String lowerLine = line.toLowerCase();
//            if (lowerLine.contains(authorOrName.toLowerCase())) {
//                findLine.append(line).append("<br>");
//                i++;
//            }
//        }
//        scanner.close();
//        if (i == 0) {
//            return null;
//        } else {
//            return findLine.toString();
//        }
//    }

    public String[] scannerFileReturnArray(int idBook) throws FileNotFoundException { //books
        Scanner scanner = new Scanner(file);
        String[] splitLineBook = new String[0];
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(Integer.toString(idBook))) {
                splitLineBook = line.split(", ");
            }
        }
        scanner.close();
        return splitLineBook;
    }

    public void deleteBookFromStorage(Integer id) throws IOException {
        String[][] buffer = scannerFileReturnBuffer(id);
        StringBuilder lineInFile = new StringBuilder();
        for (String[] strings : buffer) {
            for (int l = 0; l < strings.length; l++) {
                if (l == strings.length - 1) {
                    lineInFile.append(strings[l]).append('\n');
                } else {
                    lineInFile.append(strings[l]).append(", ");
                }
            }
        }
        writeToStorage(lineInFile.toString(), false);
    }
}
