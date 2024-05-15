package com.behsacorp.processmanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {

    private static String DB_URL;
    private static String DB_IP = "192.168.24.52";
    private static String DB_PORT = "1521";
    private static String DB_SERVICE_NAME = "gisdb";
    private static String USER_NAME = "zeebe";
    private static String PASSWORD = "123";

    static {
        DB_URL = "jdbc:oracle:thin:@" + DB_IP + ":" + DB_PORT + "/" + DB_SERVICE_NAME;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection connection;
        connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        return connection;
    }

    public static <T> void execute(String sql, Object... params) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.close();
        connection.close();
    }

}
