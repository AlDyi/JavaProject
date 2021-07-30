package library;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileLibraryCardStorage extends Storage implements LibraryCardStorage {

    FileLibraryCardStorage(String fileName) {
        super(fileName);
    }

    public boolean foundLine(int idHuman, int idBook) throws FileNotFoundException { //eсть ли этот id в файле (cards)
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(Integer.toString(idHuman)) && line.contains(Integer.toString(idBook))) {
                return true;
            }
        }
        scanner.close();
        return false;
    }

    public String[] scannerFileReturnArray(int idHuman, int idBook) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String[] splitLineBook = new String[0];
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(Integer.toString(idBook)) && line.contains((Integer.toString(idHuman)))) {
                splitLineBook = line.split(", ");
            }
        }
        scanner.close();
        return splitLineBook;
    }

    public String scannerFileSkipLine(int idHuman, int idBook) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        StringBuilder writeLine = new StringBuilder();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!(line.contains(Integer.toString(idBook)) && line.contains((Integer.toString(idHuman))))) {
                writeLine.append(line).append('\n');
            }
        }
        scanner.close();
        return writeLine.toString();
    }
}
