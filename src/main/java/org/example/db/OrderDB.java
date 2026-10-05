package org.example.db;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.example.bo.Item;
import org.example.bo.Order;

public class OrderDB {
    public static boolean placeOrder(Order order) {
        String updateStockSql = """
                UPDATE T_Items
                SET stock = stock - ?
                WHERE id = ? AND stock >= ?
                """;

        String insertOrderSql = """
                INSERT INTO T_Orders (userId)
                SELECT id
                FROM T_Users
                WHERE username = ?
                """;

        String insertOrderItemSql = """
                INSERT INTO T_OrderItems (orderId, itemId, nrOfItems, priceAtPurchase)
                VALUES (?, ?, ?, ?)
                """;

        Connection connection = DBManager.getDatabase();

        try {
            connection.setAutoCommit(false);

            try {
                for (Map.Entry<Item, Integer> entry : order.getItems().entrySet()) {
                    Item item = entry.getKey();
                    int quantity = entry.getValue();

                    try (PreparedStatement statement = connection.prepareStatement(updateStockSql)) {
                        statement.setInt(1, quantity);
                        statement.setInt(2, item.getId());
                        statement.setInt(3, quantity);

                        int rowsUpdated = statement.executeUpdate();

                        if (rowsUpdated != 1) {
                            throw new IllegalStateException("Not enough stock for item: " + item.getName());
                        }
                    }
                }

                int orderId;

                try (PreparedStatement statement = connection.prepareStatement(
                        insertOrderSql,
                        Statement.RETURN_GENERATED_KEYS)) {

                    statement.setString(1, order.getUsername());
                    int rowsInserted = statement.executeUpdate();

                    if (rowsInserted != 1) {
                        throw new SQLException("User does not exist: " + order.getUsername());
                    }

                    try (ResultSet keys = statement.getGeneratedKeys()) {
                        if (!keys.next()) {
                            throw new SQLException("Failed to create order");
                        }

                        orderId = keys.getInt(1);
                    }
                }

                try (PreparedStatement statement = connection.prepareStatement(insertOrderItemSql)) {
                    for (Map.Entry<Item, Integer> entry : order.getItems().entrySet()) {
                        Item item = entry.getKey();
                        int quantity = entry.getValue();

                        statement.setInt(1, orderId);
                        statement.setInt(2, item.getId());
                        statement.setInt(3, quantity);
                        statement.setDouble(4, item.getPrice());

                        statement.executeUpdate();
                    }
                }

                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.out.println("Unable to place order");
            return false;
//            throw new RuntimeException("Unable to place order", e);
        }

        return true;
    }
}
