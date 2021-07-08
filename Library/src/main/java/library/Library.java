package library;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class Library {
    public static void main(String[] args) throws IOException, ParseException {
        //Book oneBook = new Book(34, "1984", "Дж.Оруэлл", 2);
        Books books = new Books();

        //books.addBook(oneBook);

//        Person iAm = new Person(1, "Дьякова Александра Андреевна");
        LibraryCards card = new LibraryCards();
//
//        card.addPerson(iAm);
        //card.takeBook(1, 6);
        //books.deleteBook(11);
        //card.returnBook(1, 6);
        //card.returnBook(2, 4);
        card.renewBook(1, 6, 2);
    }
}
