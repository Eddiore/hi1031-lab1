package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private Connection connection;

    private DBManager() {
        String url = "jdbc:mysql://mysql:3306/Webshop";
        String user = "webshop_user";
        String password = System.getenv("WEBSHOP_PASSWORD");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not load MySQL driver");
        } catch (SQLException e) {
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
        return getInstance().connection;
    }
}
