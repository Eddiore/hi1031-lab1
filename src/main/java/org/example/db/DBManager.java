package org.example.db;

import java.sql.*;

public class DBManager {
    private final Connection dbConnection;

    private DBManager() {
        String url = "jdbc:mysql://mysql:3306/Webshop";
        String user = "webapp_user";
        String password = "Abcde123#";

        try {
            dbConnection = DriverManager.getConnection(url, user, password);
        }
        catch (SQLException e) {
            throw new RuntimeException("Unable to connect to MySQL Database!", e);
        }
    }

    private static class InstanceHolder {
        private static final DBManager INSTANCE = new DBManager();
    }

    public static DBManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static Connection getDatabase() {
        return getInstance().dbConnection;
    }
}
