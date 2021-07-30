package library;

import java.io.FileNotFoundException;

public interface LibraryCardStorage {
    public boolean foundLine(int idHuman, int idBook) throws FileNotFoundException;
    public String[] scannerFileReturnArray(int idHuman, int idBook) throws FileNotFoundException;
    public String scannerFileSkipLine(int idHuman, int idBook) throws FileNotFoundException;

}
