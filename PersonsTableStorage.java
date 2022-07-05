package library.db;

import library.domain.Book;
import library.domain.Person;
import library.ports.PersonStorage;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PersonsTableStorage extends BaseTableStorage implements PersonStorage {
    public static final String ID = "ID";
    public static final String FIO = "FIO";
    public static final String BEGIN = "DATEBEGIN";
    public static final String END = "DATEEND";
    public static final String EMAIL = "EMAIL";
    DateFormat df = new SimpleDateFormat("dd/MM/yyy");
    DateFormat dfOut = new SimpleDateFormat("yyy-MM-dd");
    public PersonsTableStorage() throws SQLException {
        super("persons");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS persons(" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "fio VARCHAR(255) NOT NULL, " +
                "dateBegin datetime NOT NULL, "+
                "dateEnd datetime NOT NULL, " +
                "email Varchar(255) NOT NULL, " +
                "version bigint NOT NULL)", "Создана таблица " + tableName);
    }

    @Override
    public void insert(Person person) throws SQLException {
        super.updateSqlStatement("INSERT INTO persons (FIO, DATEBEGIN, DATEEND, EMAIL, VERSION) " +
                        "VALUES ('" +
                person.getFio() + "', " + "PARSEDATETIME('" +
                df.format(person.getDateBegin().getTime()) + "', 'dd/MM/yyyy')" + ", " +
                "PARSEDATETIME('" + df.format(person.getDateEnd().getTime()) + "', 'dd/MM/yyyy'), '" +
                person.getEmail() + "' , 0)",
                "Данные добавлены в таблицу " + tableName);
    }

    @Override
    public void delete(int id) throws SQLException {
        super.updateSqlStatement("DELETE FROM persons WHERE id = " + id,
                "Данные удалены из таблицы"+ tableName);
    }

    @Override
    public void update(Person person, long version) throws SQLException {
        super.updateSqlStatement("UPDATE persons SET FIO = '" + person.getFio() +
                        "', DATEBegin = PARSEDATETIME('" +
                        df.format(person.getDateBegin().getTime()) + "', 'dd/MM/yyyy')" +
                        ", DATEEND = PARSEDATETIME('" +
                        df.format(person.getDateEnd().getTime()) + "', 'dd/MM/yyyy'), " +
                        "VERSION = " + ++version + ")" +
                        " WHERE ID = " + person.getIdPerson(),
                "Данные изменены в таблице "+ tableName);
    }

    @Override
    public boolean searchRow(long id) throws SQLException {
        return super.querySqlStatement("SELECT * FROM persons WHERE ID = " + id + " FOR UPDATE").isEmpty();
    }

    public ArrayList<Person> returnAllPersons() throws SQLException, ParseException {
        String sql = "SELECT * FROM persons";
        ArrayList<Person> foundCards = new ArrayList<Person>();
        List<Map<String, Object>> searchRow = querySqlStatement(sql);
        for (int i = 0; i < searchRow.size(); i++) {
            int id = Integer.parseInt(searchRow.get(i).get(ID).toString());
            String fio = searchRow.get(i).get(FIO).toString();
            String begin = searchRow.get(i).get(BEGIN).toString().substring(8, 10) +
                    "." + searchRow.get(i).get(BEGIN).toString().substring(5, 7) +
                    "." + searchRow.get(i).get(BEGIN).toString().substring(0, 4);
            String end = searchRow.get(i).get(END).toString().substring(8, 10) +
                    "." + searchRow.get(i).get(END).toString().substring(5, 7) +
                    "." + searchRow.get(i).get(END).toString().substring(0, 4);
            String email = searchRow.get(i).get(EMAIL).toString();

            foundCards.add(new Person(id, fio, begin, end, email));
        }
        System.out.println();
        return foundCards;
    }

}
