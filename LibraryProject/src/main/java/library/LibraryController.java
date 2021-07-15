package library;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
    public String test1() throws IOException {
        Books newBook = new Books();
        return newBook.findBookAuthorOrName("Булгаков");
        //return newBook.deleteBook(111);
    }
}
