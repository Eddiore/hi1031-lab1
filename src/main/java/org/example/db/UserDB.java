package org.example.db;

import org.example.bo.User;
import org.example.bo.enums.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class UserDB {
    private static User userFromResultSet(ResultSet result) throws SQLException {
        return new User(
                result.getString("username"),
                result.getString("passwordHash"),
                UserRole.valueOf(result.getString("role")),
                result.getInt("id"));
    }

    /**
     * Retrieves a user by username.
     *
     * @param username the username to search for
     * @return the matching user, or null if not found
     */
    public static User getUser(String username) {
        String sql = """
                SELECT id, username, passwordHash, role
                FROM T_Users
                WHERE username = ?
                """;

        Connection connection = DBManager.getDatabase();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return userFromResultSet(result);
                }
            }
        } catch (SQLException e) {
            System.out.println("Unable to login as user: " + username);
        }

        return null;
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user
     * @return the matching user, or null if not found
     */
    public static User getUserById(int id) {
        String sql = """
                SELECT id, username, passwordHash, role
                FROM T_Users
                WHERE id = ?
                """;

        Connection connection = DBManager.getDatabase();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return userFromResultSet(result);
                }
            }
        } catch (SQLException e) {
            System.out.println("Unable to retrieve user with id: " + id);
        }

        return null;
    }

    /**
     * Creates a new user in the database.
     *
     * @param user the user to create
     * @return true if the user was created, false otherwise
     */
    public static boolean createUser(User user) {
        String sql = """
                INSERT INTO T_Users (username, passwordHash, role)
                VALUES (?, ?, ?)
                """;

        Connection connection = DBManager.getDatabase();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPasswordHash());
            statement.setString(3, user.getRole().toString());

            statement.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("User already exists: " + user.getUsername());
            return false;
        } catch (SQLException e) {
            System.out.println("Unable to create user: " + user.getUsername());
            return false;
        }
    }

    /**
     * Updates an existing user's username and role.
     *
     * @param user the user to update
     * @return true if the user was updated, false otherwise
     */
    public static boolean updateUser(User user) {
        String sql = """
                UPDATE T_Users
                SET role = ?, username = ? WHERE id = ?
                """;

        Connection connection = DBManager.getDatabase();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getRole().toString());
            statement.setString(2, user.getUsername());
            statement.setInt(3, user.getId());

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Unable to update user: " + user.getUsername());
            return false;
        }
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list containing all users
     */
    public static List<User> getAllUsers() {
        String sql = """
                SELECT id, username, passwordHash, role FROM T_Users
                """;

        List<User> out = new ArrayList<>();
        Connection connection = DBManager.getDatabase();

        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                out.add(userFromResultSet(result));
            }

        } catch (SQLException e) {
            System.out.println("Unable to retrieve all users: " + e.getMessage());
        }

        return out;
    }
}
