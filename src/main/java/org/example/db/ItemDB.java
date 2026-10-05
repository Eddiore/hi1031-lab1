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

    /**
     * Retrieves all items from the database.
     *
     * @return a list of all items, or null if an error occurs
     */
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

    /**
     * Retrieves all items belonging to the specified category.
     *
     * @param category the category to filter by
     * @return a list of matching items, or null if an error occurs
     */
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

    /**
     * Retrieves an item by its ID.
     *
     * @param id the ID of the item
     * @return the matching item, or null if it does not exist
     */
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

    /**
     * Updates an existing item's values.
     *
     * @param item the item to update
     * @return true if the item was updated, false otherwise
     */
    public static boolean updateItem(Item item) {
        String sql = """
                UPDATE T_Items
                SET name = ?, description = ?, category = ?, price = ?, stock = ?
                WHERE id = ?
                """;

        Connection connection = DBManager.getDatabase();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            statement.setString(3, item.getCategory().toString());
            statement.setInt(4, item.getPrice());
            statement.setInt(5, item.getStock());
            statement.setInt(6, item.getId());

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Unable to update item: " + item.getName());
            return false;
        }
    }
}
