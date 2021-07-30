package library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;


@RestController
@RequestMapping("/library/card")
public class LibraryCardsController {

    LibraryCards card;
    @Autowired //Положи мне сюда, инициализируй ее контестом(инъекция)
    LibraryCardsController(LibraryCards card){
        this.card = card;
    }

    @PostMapping (value = "/personForm", consumes = "application/x-www-form-urlencoded;charset=UTF-8") //Для создания объекта
    public String formPerson(@RequestBody MultiValueMap<String, String> map) throws IOException {
        int id = Integer.parseInt(map.get("id").get(0));
        String name = map.get("name").get(0);
        Person human = new Person(id, name);
        return card.addPerson(human);
    }

    @PostMapping (value = "/takeForm", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public String takeBook(@RequestBody MultiValueMap<String, String> map) throws IOException {
        int idHuman = Integer.parseInt(map.get("idHuman").get(0));
        int idBook = Integer.parseInt(map.get("idBook").get(0));
        return card.takeBook(idHuman, idBook);
    }

    @PostMapping (value = "/returnForm", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public String returnBook(@RequestBody MultiValueMap<String, String> map) throws IOException {
        int idHuman = Integer.parseInt(map.get("idHuman").get(0));
        int idBook = Integer.parseInt(map.get("idBook").get(0));
        return card.returnBook(idHuman, idBook);
    }

    @PostMapping (value = "/renewForm", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public String renewBook(@RequestBody MultiValueMap<String, String> map) throws IOException, ParseException {
        int idHuman = Integer.parseInt(map.get("idHuman").get(0));
        int idBook = Integer.parseInt(map.get("idBook").get(0));
        int month = Integer.parseInt(map.get("month").get(0));
        return card.renewBook(idHuman, idBook, month);
    }
}
