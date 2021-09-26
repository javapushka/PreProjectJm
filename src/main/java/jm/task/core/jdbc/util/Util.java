package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/test?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "mysqlqwerty";

    public static Connection getMySQLConnection() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Проблемы с загрузкой драйвера подключения");
        }
        try {
            Connection connect = DriverManager.getConnection(URL, USER, PASSWORD);
            return connect;
        } catch (SQLException e) {
            System.out.println("Проблема с соединением");
        }
        return null;
    }
}
