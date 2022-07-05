package library.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class BaseTableStorage {
    //Connection connection;  // JDBC-соединение для работы с таблицей
    public String tableName;       // Имя таблицы
    public static final String VERSION = "VERSION";

    @Autowired
    JdbcTemplate jdbcTemplate;

    public BaseTableStorage(String tableName) throws SQLException { // Для реальной таблицы передадим в конструктор её имя
        this.tableName = tableName;
        //this.connection = StockExchangeDB.getConnection(); // Установим соединение с СУБД для дальнейшей работы
    }

    // Закрытие
//    public void close() {
//        try {
//            if (connection != null && !connection.isClosed())
//                connection.close();
//        } catch (SQLException e) {
//            System.out.println("Ошибка закрытия SQL соединения!");
//        }
//    }

    // Выполнить SQL команду без параметров в СУБД, по завершению выдать сообщение в консоль
    public void executeSqlStatement(String sql, String description) throws SQLException { //Create
        //reopenConnection(); // переоткрываем (если оно неактивно) соединение с СУБД
        //Statement statement = connection.createStatement();  // Создаем statement для выполнения sql-команд
        //boolean answer = statement.execute(sql);// Выполняем statement - sql команду
        //statement.close();      // Закрываем statement для фиксации изменений в СУБД
        jdbcTemplate.execute(sql);
        if (description != null)
            System.out.println(description);

    };

    public void updateSqlStatement(String sql, String description) throws SQLException { //Update, Insert, Delete
        jdbcTemplate.update(sql);
        if (description != null)
            System.out.println(description);
    };

    public List<Map<String, Object>> querySqlStatement(String sql) throws SQLException { //Select
        return jdbcTemplate.queryForList(sql);
    };

    public String getTableName() {
        return tableName;
    }


    //    boolean executeSqlStatement(String sql) throws SQLException {
//        return executeSqlStatement(sql, null);
//    };

    // Активизация соединения с СУБД, если оно не активно.
//    void reopenConnection() throws SQLException {
//        if (connection == null || connection.isClosed()) {
//            connection = StockExchangeDB.getConnection();
//        }
//    }
}
