package library.db;

import library.db.BooksTableStorage;
import library.db.PersonsTableStorage;
import library.db.RentsTableStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class StockExchangeDB {
    // Таблицы СУБД
    @Autowired
    BooksTableStorage books;
    @Autowired
    PersonsTableStorage persons;
    @Autowired
    RentsTableStorage rents;

    // Получить новое соединение с БД
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(DB_URL, USER, PASS);
//    }


    // Создание всех таблиц и ключей между ними
    @EventListener(classes = ContextStartedEvent.class)
    @Transactional
    public void createTablesAndForeignKeys() throws SQLException {
        books.createTable();
        persons.createTable();
        rents.createTable();
        // Создание внешних ключей (связь между таблицами)
        rents.createForeignKeys();
    }
}
