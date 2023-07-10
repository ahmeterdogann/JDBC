package com.ahmeterdogan.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private final static String URL = "jdbc:postgresql://localhost/moviedb";
    private final static String USER = "postgres";
    private final static String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        return connection;
    }
}
