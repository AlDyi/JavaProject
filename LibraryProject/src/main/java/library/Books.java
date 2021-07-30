package library;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;

@Service //это бин
public class Books {
    private final FileBookStorage fileBooks = new FileBookStorage("FileBooks");

    String addBook(Book book) throws IOException { //Добавление книги
        fileBooks.writeToStorage(book.getId() + ", " + book.getName() + ", " + book.getAuthor() + ", " + book.getNumberOfCopies() + ", " + book.getNumberOfIssue() + '\n', true);
        return "Данные добавлены";
    }

    String deleteBook(Integer id) throws IOException { //Удаление книги
        if (!fileBooks.foundLine(id)) { //бизнес логика findRow лучше
            return "Данная книга не найдена";
        }
        fileBooks.deleteBookFromStorage(id);
        return "Данные удалены";
    }


    ArrayList<Book> findBookAuthorOrName(String authorOrName) throws FileNotFoundException { //Найти книгу по автору или названию
        ArrayList<Book> foundBooks;
        foundBooks = fileBooks.scannerFileReturnList(authorOrName);
            return foundBooks;
    }

    String editBook(Book book) throws IOException {
        if (fileBooks.foundLine(book.getId())) {
            deleteBook(book.getId());
            addBook(book);
            return "Книга изменена";
        } else {
            return "Книга не найдена";
        }
    }
}
