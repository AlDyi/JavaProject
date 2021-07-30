package library;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface BookStorage {
    public String[][] scannerFileReturnBuffer(int id) throws FileNotFoundException;
    public ArrayList<Book> scannerFileReturnList(String authorOrName) throws FileNotFoundException;
    public String[] scannerFileReturnArray(int idBook) throws FileNotFoundException;
    public void deleteBookFromStorage(Integer id) throws IOException;
}
