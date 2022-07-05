package library.db;

import library.domain.Person;
import library.domain.Rent;
import library.ports.RentStorage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RentsTableStorage extends BaseTableStorage implements RentStorage {
    DateFormat df = new SimpleDateFormat("dd/MM/yyy");
    public static final String NAME = "NAME";
    public static final String MAILPERSON = "EMAIL";
    public static final String AUTHOR = "AUTHOR";
    public static final String BEGIN = "DATEBEGIN";
    public static final String END = "DATEEND";
    public static final String BOOKVERSION = "VERSION";
    public static final String RENTVERSION = "RVERSION";
    public static final String ID = "ID";
    public RentsTableStorage() throws SQLException {
        super("rents");
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS rents(" +
                "idPerson BIGINT NOT NULL," +
                "idBook BIGINT NOT NULL," +
                "dateBegin datetime NOT NULL," +
                "dateEnd datetime NOT NULL, " +
                "rversion bigint NOT NULL)", "Создана таблица " + tableName);
    }

    @Override
    public void createForeignKeys() throws SQLException {
        super.executeSqlStatement(" ALTER TABLE rents ADD FOREIGN KEY (idPerson) REFERENCES persons(id)",
                "Cоздан внешний ключ rents.idPerson -> persons.id");
        super.executeSqlStatement(" ALTER TABLE rents ADD FOREIGN KEY (idBook) REFERENCES books(id)",
                "Cоздан внешний ключ rents.idBooks -> books.id");
    }

    @Override
    public void insert(Rent rent) throws SQLException {
        super.updateSqlStatement("INSERT INTO rents (IDPERSON, IDBOOK, DATEBEGIN, DATEEND, RVERSION)" +
                        " VALUES (" +
                        rent.getIdPerson() + ", " +
                        rent.getIdBook() + ", " + "PARSEDATETIME('" +
                        df.format(rent.getDateBegin().getTime()) + "', 'dd/MM/yyyy')" + ", " +
                        "PARSEDATETIME('" + df.format(rent.getDateEnd().getTime()) + "', 'dd/MM/yyyy')," +
                        "0)",
                "Данные добавлены в таблицу " + tableName);
    }

    @Override
    public void delete(Rent rent) throws SQLException {
        super.updateSqlStatement("DELETE FROM rents WHERE idPerson = " + rent.getIdPerson() +
                "AND idBook = " + rent.getIdBook(),
                "Данные удалены из таблицы "+ tableName);
    }

    @Override
    public void update(Rent rent, long version) throws SQLException {
        super.updateSqlStatement("UPDATE rents SET DATEEND = PARSEDATETIME('" +
                        df.format(rent.getDateEnd().getTime()) + "', 'dd/MM/yyyy')," +
                        " RVERSION = " + ++version +
                        " WHERE IDPERSON = " + rent.getIdPerson() +
                        " AND IDBOOK = " + rent.getIdBook(),
                "Данные изменены в таблице "+ tableName);
    }

    @Override
    public boolean searchRow(long idPerson, long idBook) throws SQLException {
        return super.querySqlStatement("SELECT * FROM rents WHERE IDPERSON = " + idPerson + " AND IDBOOK = " + idBook + " FOR UPDATE").isEmpty();
    }

    public long getVersionRents(long idBook, long idPerson) throws SQLException {
        List<Map<String, Object>> searchRow = super.querySqlStatement("SELECT RVERSION FROM " + tableName +
                " WHERE IDBOOK = " + idBook + " AND IDPERSON = " + idPerson);
        if (searchRow.isEmpty()) {
            return 0;
        } else {
            return Long.parseLong(searchRow.get(0).get(VERSION).toString());
        }
    }

    public ArrayList<Rent> returnAllPersonBooks(Integer id) throws SQLException, ParseException {
        String sql = "Select books.id, books.Name, books.Author, rents.datebegin, rents.dateend, rents.rversion, books.version from " +
                "books inner join rents " +
                "on books.id=rents.idBook " +
                "inner join persons " +
                "on persons.id=rents.idperson " +
                "where persons.id = " + id;
        ArrayList<Rent> foundCards = new ArrayList<Rent>();
        List<Map<String, Object>> searchRow = querySqlStatement(sql);
        for (int i = 0; i < searchRow.size(); i++) {
            Integer idBook = Integer.parseInt(searchRow.get(i).get(ID).toString());
            String name = searchRow.get(i).get(NAME).toString();
            String author = searchRow.get(i).get(AUTHOR).toString();
            String begin = searchRow.get(i).get(BEGIN).toString().substring(8, 10) +
                    "." + searchRow.get(i).get(BEGIN).toString().substring(5, 7) +
                    "." + searchRow.get(i).get(BEGIN).toString().substring(0, 4);
            String end = searchRow.get(i).get(END).toString().substring(8, 10) +
                    "." + searchRow.get(i).get(END).toString().substring(5, 7) +
                    "." + searchRow.get(i).get(END).toString().substring(0, 4);
            Integer bookVersion = Integer.parseInt(searchRow.get(i).get(BOOKVERSION).toString());
            Integer rentVersion = Integer.parseInt(searchRow.get(i).get(RENTVERSION).toString());

            foundCards.add(new Rent(id, idBook, name, author, begin, end, rentVersion, bookVersion));
        }
        System.out.println();
        return foundCards;
    }

    public String returnEndDate(Integer idPerson, Integer idBook) throws SQLException {
        String sql = "Select DATEEND from rents where idPerson = " + idPerson +
                " AND idBook = " + idBook;
        List<Map<String, Object>> searchRow = querySqlStatement(sql);
        return (searchRow.get(0).get(END).toString().substring(8, 10) +
                "." + searchRow.get(0).get(END).toString().substring(5, 7) +
                "." + searchRow.get(0).get(END).toString().substring(0, 4));
    }

    public List<String> getEndDateFiveDaysAfterToday() {
        String sql = "Select persons.email from persons inner join rents on persons.id = rents.idPerson " +
                "where DATEDIFF('DAY', CURRENT_DATE(), rents.Dateend) < 30 FOR UPDATE";
        List<Map<String, Object>> searchMailPerson = null;
        try {
            searchMailPerson = querySqlStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("Help!");
        }
        List<String> idPersonsForMail = new ArrayList<String>();
        for (int i = 0; i < searchMailPerson.size(); i++) {
            String emailPerson = searchMailPerson.get(i).get(MAILPERSON).toString();
            idPersonsForMail.add(emailPerson);
        }
        return idPersonsForMail;
    }
}
