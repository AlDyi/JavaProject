package library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

//netstat -a -n -o
// filelibrarycardsstorage  и filebookstorage интерфейсы, bookstorage и librarycardsstorage будут имплементировать интерфейсы
//и extend и implement
// bookstorage = fileBookStorage, те интерфейс будет равен классу

@RestController
@RequestMapping("/library/book")
public class BooksController {

    Books newBooks;
    @Autowired //Положи мне сюда, инициализируй ее контестом(инъекция)
    BooksController(Books newBooks){
        this.newBooks = newBooks;
    }

    @DeleteMapping ("/delete/{id}") //Для удаления объекта
    public String deleteBook(@PathVariable int id) throws IOException {
        return newBooks.deleteBook(id);
    }

    @PostMapping (value = "/addBook", consumes = "application/x-www-form-urlencoded;charset=UTF-8") //Для создания объекта
    public String formBook(@RequestBody MultiValueMap<String, String> map) throws IOException {
        int id = Integer.parseInt(map.get("id").get(0));
        String author = map.get("author").get(0);
        String name = map.get("name").get(0);
        int numberOfCopies = Integer.parseInt(map.get("numberOfCopies").get(0));
        Book book = new Book(id, name, author, numberOfCopies);
        return newBooks.addBook(book);
    }

    @GetMapping ("/findBook/{authorOrName}") //+
    public String findBook(@PathVariable String authorOrName) throws FileNotFoundException {
        ArrayList<Book> foundBooks = newBooks.findBookAuthorOrName(authorOrName);
        return foundBooks.stream().map(this::bookToString).collect(Collectors.joining("<br>"));
    }

    private String bookToString(Book book) {
        String foundRows = book.getId() + ", " +
                book.getName() + ", " +
                book.getAuthor() + ", " +
                book.getNumberOfCopies() + ", " +
                book.getNumberOfIssue();
        return foundRows;
    }

    @PostMapping (value = "/editBook", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public String editBook(@RequestBody MultiValueMap<String, String> map) throws IOException {
        int id = Integer.parseInt(map.get("id").get(0));
        String author = map.get("author").get(0);
        String name = map.get("name").get(0);
        int numberOfCopies = Integer.parseInt(map.get("numberOfCopies").get(0));
        int numberOfIssue = Integer.parseInt(map.get("numberOfIssue").get(0));
        Book book = new Book(id, name, author, numberOfCopies, numberOfIssue);
        return newBooks.editBook(book);
    }
}
