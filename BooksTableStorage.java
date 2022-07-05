package library.db;

import library.domain.Book;
import library.ports.BookStorage;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BooksTableStorage extends BaseTableStorage implements BookStorage {
    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String AUTHOR = "AUTHOR";
    public static final String COPIES = "NUMBEROFCOPIES";
    public static final String ISSUE = "NUMBEROFISSUE";
    public static final String VERSION = "VERSION";

    public BooksTableStorage() throws SQLException {
        super("books");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS books(" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "author VARCHAR(255) NOT NULL,"+
                "numberOfCopies INTEGER NOT NULL,"+
                "numberOfIssue INTEGER NOT NULL)", "Создана таблица " + tableName);
    }

    @Override
    public void insert(Book book) throws SQLException {
        super.updateSqlStatement("INSERT INTO books (NAME, AUTHOR, NUMBEROFCOPIES, NUMBEROFISSUE, VERSION) " +
                "VALUES ('" +
                book.getName() + "', '" + book.getAuthor() + "', " +
                book.getNumberOfCopies() + ", " + book.getNumberOfIssue() +
                ", 0)", "Данные добавлены в таблицу " + tableName);
    }

    @Override
    public void delete(long id) throws SQLException {
        super.updateSqlStatement("DELETE FROM books WHERE ID = " + id,
                "Книга удалена из таблицы"+ tableName);
    }

    @Override
    public void update(Book book) throws SQLException {
        super.updateSqlStatement("UPDATE books SET NAME = '" + book.getName() +
                "', AUTHOR = '" + book.getAuthor() +
                "', NUMBEROFCOPIES = " + book.getNumberOfCopies() +
                ", VERSION = " + book.getVersion() +
                " WHERE ID = " + book.getId(),
                "Данные изменены в таблице "+ tableName);
    }

    @Override
    public void updateIssue(long id, int i, long version) throws SQLException {
        super.updateSqlStatement("UPDATE books SET NUMBEROFISSUE =  NUMBEROFISSUE + " + i +
                ", VERSION = " + ++version +
                "WHERE ID = " + id, null);
    }

    @Override
    public boolean searchRow(long id) throws SQLException {
        return super.querySqlStatement("SELECT * FROM books WHERE ID = " + id + " FOR UPDATE").isEmpty();
    }

    @Override
    public boolean isFreeBook(long id) throws SQLException {
        return super.querySqlStatement("SELECT * FROM books WHERE ID = " + id +
                " AND NUMBEROFCOPIES = NUMBEROFISSUE FOR UPDATE").isEmpty();
    }

    @Override
    public ArrayList<Book> searchBook(String authorOrName) throws SQLException {
        String sql = String.format("SELECT * FROM books WHERE LOWER(AUTHOR) LIKE '%%%s%%' OR LOWER(NAME) LIKE '%%%s%%' FOR UPDATE",
                authorOrName.toLowerCase(), authorOrName.toLowerCase());
        ArrayList<Book> foundBooks = new ArrayList<Book>();
        List<Map<String, Object>> searchRow = querySqlStatement(sql);
        for (int i = 0; i < searchRow.size(); i++) {
            int id = Integer.parseInt(searchRow.get(i).get(ID).toString());
            String name = searchRow.get(i).get(NAME).toString();
            String author = searchRow.get(i).get(AUTHOR).toString();
            int copies = Integer.parseInt(searchRow.get(i).get(COPIES).toString());
            int issue = Integer.parseInt(searchRow.get(i).get(ISSUE).toString());
            int version = Integer.parseInt(searchRow.get(i).get(VERSION).toString());
            foundBooks.add(new Book(id, name, author, copies, issue, version));
        }
        return foundBooks;
    }

    @Override
    public ArrayList<Book> returnAllBooks() throws SQLException {
        String sql = "SELECT * FROM books";
        ArrayList<Book> foundBooks = new ArrayList<Book>();
        List<Map<String, Object>> searchRow = querySqlStatement(sql);
        for (int i = 0; i < searchRow.size(); i++) {
            int id = Integer.parseInt(searchRow.get(i).get(ID).toString());
            String name = searchRow.get(i).get(NAME).toString();
            String author = searchRow.get(i).get(AUTHOR).toString();
            int copies = Integer.parseInt(searchRow.get(i).get(COPIES).toString());
            int issue = Integer.parseInt(searchRow.get(i).get(ISSUE).toString());
            int version = Integer.parseInt(searchRow.get(i).get(VERSION).toString());
            foundBooks.add(new Book(id, name, author, copies, issue, version));
        }
        return foundBooks;
    }

    @Override
    public long getVersion(long id) throws SQLException {
        List<Map<String, Object>> searchRow = querySqlStatement("SELECT VERSION FROM " + tableName +
                " WHERE ID = " + id);
        if (searchRow.isEmpty()) {
            return 0;
        } else {
            return Long.parseLong(searchRow.get(0).get(VERSION).toString());
        }
    }
}
