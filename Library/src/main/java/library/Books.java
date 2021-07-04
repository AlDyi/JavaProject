package library;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Books {
    Map<Integer, List<Object>> map1 = new HashMap<Integer, List<Object>>();
    Integer i = 0;

    void AddBook(String name, String author, Integer number_of_copies, Integer number_of_issue) { //Добавление книги
        map1.put(i++, Arrays.<Object>asList(name, author, number_of_copies, number_of_issue));
        System.out.println("Данные добавлены");
    }

    void DeleteBook() { //Удаление книги
        map1.remove(0);
        System.out.println("Данные удалены");
    }

    void PrintBook() { //Вывести данные о последней книге
        System.out.println(map1.get(i-1));
    }

    void FindBookAuthor(String author) { //Найти книгу по автору
        for (int i = 0; i < map1.size(); i++) {
            List<Object> mylist = map1.get(i);
            if (mylist.get(1) == author)
                System.out.println(map1.get(i));
            else
                System.out.println("Автор не найден");
        }
    }

    void FindBookName(String name) { //Найти книгу по названию 
        for (int i = 0; i < map1.size(); i++) {
            List<Object> mylist = map1.get(i);
            if (mylist.get(0) == name)
                System.out.println(map1.get(i));
            else
                System.out.println("Книга не найдена");
        }
    }
}
