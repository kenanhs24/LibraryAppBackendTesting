package com.LibraryApp.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_utils {
    private static Connection connection;
    public static void createConnection(String url, String username, String password){
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
