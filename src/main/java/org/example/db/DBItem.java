package org.example.db;

import org.example.bo.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBItem  {
    public static List<Item> getAllItems() {
        Connection connection = DBManagerSQL.getDatabase();
        List<Item> out = new ArrayList<>();

        String query = "SELECT * FROM T_Items";
        try (Statement s = connection.createStatement()) {
            try (ResultSet rs = s.executeQuery(query)) {
//                while (rs.next()) {
//                    out.add(new Item(rs.getInt("itemId"),
//                            rs.getString("name"),
//                            rs.getString("description")));
//                }
            } catch (SQLException e) {
                throw new RuntimeException("Unable to query database for items!", e);
            }
        } catch (SQLException ignored) {}

        return out;
    }
}
