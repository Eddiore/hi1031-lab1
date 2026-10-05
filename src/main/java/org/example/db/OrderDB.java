package org.example.db;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.bo.Item;
import org.example.bo.Order;
import org.example.bo.enums.OrderStatus;

public class OrderDB {
    private static List<Order> getOrders(PreparedStatement statement) throws SQLException {
        ResultSet result = statement.executeQuery();
        Map<Integer, Order> orders = new HashMap<>();

        while (result.next()) {
            int orderId = result.getInt("orderId");

            Order order = orders.get(orderId);

            if (order == null) {
                order = new Order(
                        result.getString("customerUsername"),
                        OrderStatus.valueOf(result.getString("status")),
                        new HashMap<>(),
                        orderId);

                if (result.getObject("staffId") != null) {
                    order.setAssignedStaffId(result.getInt("staffId"));
                }

                orders.put(orderId, order);
            }

            Item item = new Item(
                    result.getString("name"),
                    result.getString("description"),
                    result.getString("category"),
                    result.getInt("price"),
                    result.getInt("stock"),
                    result.getInt("itemId"));

            order.addItem(item, result.getInt("nrOfItems"));
        }

        return new ArrayList<>(orders.values());
    }

    /**
     * Retrieves all orders with the specified status.
     *
     * @param status the order status to filter by
     * @return a list of matching orders, or null if an error occurs
     */
    public static List<Order> getOrdersByStatus(OrderStatus status) {
        String sql = """
                SELECT o.id AS orderId,
                       u.username AS customerUsername,
                       o.status,
                       s.id AS staffId,
                       i.id AS itemId,
                       i.name,
                       i.description,
                       i.category,
                       i.price,
                       i.stock,
                       oi.nrOfItems
                FROM T_Orders o
                JOIN T_Users u ON o.userId = u.id
                LEFT JOIN T_Users s ON o.assignedStaffId = s.id
                JOIN T_OrderItems oi ON o.id = oi.orderId
                JOIN T_Items i ON oi.itemId = i.id
                WHERE o.status = ?
                """;

        Connection connection = DBManager.getDatabase();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status.name());
            return getOrders(statement);
        } catch (SQLException e) {
            System.out.println("Error getting orders with status: " + status);
            return null;
        }
    }

    /**
     * Retrieves all orders with the specified staff id assigned to it.
     *
     * @param staffId the staff id to filter by
     * @return a list of matching orders, or null if an error occurs
     */
    public static List<Order> getOrdersByAssignedStaff(int staffId) {
        String sql = """
                SELECT o.id AS orderId,
                       u.username AS customerUsername,
                       o.status,
                       s.id AS staffId,
                       i.id AS itemId,
                       i.name,
                       i.description,
                       i.category,
                       i.price,
                       i.stock,
                       oi.nrOfItems
                FROM T_Orders o
                JOIN T_Users u ON o.userId = u.id
                LEFT JOIN T_Users s ON o.assignedStaffId = s.id
                JOIN T_OrderItems oi ON o.id = oi.orderId
                JOIN T_Items i ON oi.itemId = i.id
                WHERE o.assignedStaffId = ?
                """;

        Connection connection = DBManager.getDatabase();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, staffId);
            return getOrders(statement);
        } catch (SQLException e) {
            System.out.println("Error getting orders with staffID: " + staffId);
            return null;
        }
    }

    /**
     * Updates the status of an existing order.
     *
     * @param orderId the order to update
     * @return true if the order was updated, false otherwise
     */
    public static boolean packageOrder(int orderId) {
        String sql = """
                UPDATE T_Orders
                SET status = 'PACKED'
                WHERE id = ?
                """;
        Connection connection = DBManager.getDatabase();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);

            if (statement.executeUpdate() == 0) {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Unable to update order: " + orderId);
            return false;
        }

        return true;
    }

    /**
     * Assigns an order to a staff member.
     * <p>
     * The order can only be assigned if it has not already been assigned
     * to another staff member.
     *
     * @param orderId the ID of the order to assign
     * @param staffId the ID of the staff member to assign the order to
     * @return true if the order was successfully assigned, false otherwise
     */
    public static boolean assignOrderToStaff(int orderId, int staffId) {
        String sql = """
                UPDATE T_Orders o
                JOIN T_Users u ON u.id = ?
                SET o.assignedStaffId = u.id, o.status = 'PACKING'
                WHERE o.id = ?
                AND u.role = 'STAFF'
                """;

        Connection connection = DBManager.getDatabase();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, staffId);
            statement.setInt(2, orderId);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Unable to assign order " + orderId + " to staff " + staffId);
            return false;
        }
    }

    /**
     * Places an order and updates the corresponding item stock.
     *
     * @param order the order to place
     * @return true if the order was successfully placed, false otherwise
     */
    public static boolean placeOrder(Order order) {
        String updateStockSql = """
                UPDATE T_Items
                SET stock = stock - ?
                WHERE id = ? AND stock >= ?
                """;

        String insertOrderSql = """
                INSERT INTO T_Orders (userId, status)
                SELECT id, ?
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

                    statement.setString(1, order.getStatus().toString());
                    statement.setString(2, order.getUsername());
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
            // throw new RuntimeException("Unable to place order", e);
        }

        return true;
    }
}
