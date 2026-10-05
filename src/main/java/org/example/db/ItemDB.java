package org.example.db;

import org.example.bo.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDB {
    private static Item itemFromResultSet(ResultSet result) throws SQLException {
        return new Item(
                result.getString("name"),
                result.getString("description"),
                result.getString("category"),
                result.getInt("price"),
                result.getInt("stock"),
                result.getInt("id"));
    }

    public static List<Item> getAllItems() {
        String sql = """
                SELECT id, name, description, category, price, stock
                FROM T_Items
                """;

        List<Item> out = new ArrayList<>();
        Connection connection = DBManager.getDatabase();

        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                out.add(itemFromResultSet(result));
            }
        } catch (SQLException e) {
            System.out.println("Error getting all items: " + e.getMessage());
            return null;
        }

        return out;
    }

    public static List<Item> getItemsByCategory(String category) {
        String sql = """
                SELECT id, name, description, category, price, stock
                FROM T_Items
                WHERE category = ?
                """;

        List<Item> out = new ArrayList<>();
        Connection connection = DBManager.getDatabase();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, category);

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    out.add(itemFromResultSet(result));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting items from category: " + e.getMessage());
            return null;
        }

        return out;
    }

    public static Item getItemById(int id) {
        String sql = """
                SELECT id, name, description, category, price, stock
                FROM T_Items
                WHERE id = ?
                """;

        Connection connection = DBManager.getDatabase();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return itemFromResultSet(result);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting item by id: " + e.getMessage());
        }

        return null;
    }
}
