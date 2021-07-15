package library;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/library")
public class LibraryController {
    //@GetMapping
    public String test() throws IOException {
        Book book = new Book(111, "1984", "Дж.Оруэлл", 3);
        Books newBook = new Books();
        return newBook.addBook(book);
    }

    @GetMapping
    public String test1() throws IOException, ParseException {
        LibraryCards cards = new LibraryCards();
        Person human = new Person(1, "Дьякова Александра Андреевна");
        Book book = new Book(116, "Мертвые души", "Н.В.Гоголь", 2);
        //return cards.takeBook(human, book);
        //return cards.returnBook(human, book);
        return cards.renewBook(human, book, 2);
        //return newBook.deleteBook(111);
    }
}
