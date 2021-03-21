package service;

import service.exception.ExceptionJson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private static Connection connection = null;

    public static Connection getDbConnection(String driver, String url, String username, String password) {
        if (connection == null){
            try {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new ExceptionJson("error","Ошибка загрузки класса подключения к базе БД; "+e.getMessage());
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ExceptionJson("error","Ошибка подключения к БД; "+e.getMessage());
            }
        }
        return connection;
    }
}
