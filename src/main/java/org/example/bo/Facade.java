package org.example.bo;

import org.example.bo.enums.OrderStatus;
import org.example.db.ItemDB;
import org.example.db.OrderDB;
import org.example.db.UserDB;
import org.example.ui.ItemDTO;
import org.example.ui.OrderDTO;
import org.example.ui.UserDTO;
import org.mindrot.jbcrypt.BCrypt;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Facade {
    /**
     * Retrieves all items as DTOs.
     *
     * @return a list of all item DTOs
     */
    public static List<ItemDTO> getAllItems() {
        List<ItemDTO> DTOs = new ArrayList<>();
        List<Item> items = ItemDB.getAllItems();
        if (items == null || items.isEmpty()) {
            return DTOs;
        }

        for (Item item : items) {
            DTOs.add(new ItemDTO(item.getName(),
                    item.getDescription(),
                    item.getCategory(),
                    item.getPrice(),
                    item.getStock(),
                    item.getId()));
        }

        return DTOs;
    }

    /**
     * Retrieves items belonging to a category as DTOs.
     *
     * @param category the category to filter by
     * @return a list of matching item DTOs
     */
    public static List<ItemDTO> getItemsByCategory(String category) {
        List<ItemDTO> DTOs = new ArrayList<>();
        List<Item> items = ItemDB.getItemsByCategory(category);
        if (items == null || items.isEmpty()) {
            return DTOs;
        }

        for (Item item : items) {
            DTOs.add(new ItemDTO(item.getName(),
                    item.getDescription(),
                    item.getCategory(),
                    item.getPrice(),
                    item.getStock(),
                    item.getId()));
        }

        return DTOs;
    }

    /**
     * Retrieves an item by its ID.
     *
     * @param id the ID of the item
     * @return the matching item DTO, or null if not found
     */
    public static ItemDTO getItemById(String id) {
        Item item = ItemDB.getItemById(Integer.valueOf(id));

        if (item == null) {
            return null;
        }

        return new ItemDTO(item.getName(),
                item.getDescription(),
                item.getCategory(),
                item.getPrice(),
                item.getStock(),
                item.getId());
    }

    /**
     * Retrieves all users as DTOs.
     *
     * @return a list of all user DTOs
     */
    public static List<UserDTO> getAllUsers() {
        List<UserDTO> DTOs = new ArrayList<>();
        List<User> users = UserDB.getAllUsers();

        if (users == null || users.isEmpty())
            return DTOs;

        for (User user : users) {
            DTOs.add(new UserDTO(
                    user.getUsername(),
                    user.getPasswordHash(),
                    user.getRole(),
                    user.getId()));
        }

        return DTOs;
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user
     * @return the matching user DTO, or null if not found
     */
    public static UserDTO getUserById(String id) {
        User user = UserDB.getUserById(Integer.parseInt(id));

        if (user == null) {
            return null;
        }

        return new UserDTO(
                user.getUsername(),
                user.getPasswordHash(),
                user.getRole(),
                user.getId());
    }

    /**
     * Updates a user's username and role.
     *
     * @param updateUser the user data to update
     * @return true if the user was updated, false otherwise
     */
    public static boolean updateUser(UserDTO updateUser) {
        User user = UserDB.getUserById(updateUser.id());
        if (user == null) {
            return false;
        }

        user.setRole(updateUser.role());
        user.setUsername(updateUser.username());

        return UserDB.updateUser(user);
    }

    /**
     * Authenticates a user using their username and password.
     *
     * @param username the username
     * @param password the user's password
     * @return the user DTO if authentication succeeds, otherwise null
     */
    public static UserDTO loginAsUser(String username, String password) {
        User user = UserDB.getUser(username);
        if (user == null) {
            return null;
        }

        if (!BCrypt.checkpw(password, user.getPasswordHash())) {
            return null;
        }

        return new UserDTO(user.getUsername(), user.getPasswordHash(), user.getRole(), user.getId());
    }

    /**
     * Places an order for the specified user and items.
     *
     * @param user  the user placing the order
     * @param items the items and quantities to order
     * @return true if the order was successfully placed, false otherwise
     */
    public static boolean placeOrder(UserDTO user, HashMap<ItemDTO, Integer> items) {
        HashMap<Item, Integer> itemMap = new HashMap<>();

        for (Map.Entry<ItemDTO, Integer> entry : items.entrySet()) {
            Item item = convertToItem(entry.getKey());
            itemMap.put(item, entry.getValue());
        }

        Order order = new Order(
                user.username(),
                OrderStatus.PLACED,
                itemMap);

        return OrderDB.placeOrder(order);
    }

    private static Item convertToItem(ItemDTO DTO) {
        return new Item(
                DTO.name(),
                DTO.description(),
                DTO.category(),
                DTO.price(),
                DTO.stock(),
                DTO.id());
    }

    /**
     * Returns all orders with the specified status.
     *
     * @param status the status to filter orders by
     * @return a list of matching orders as OrderDTO objects
     */
    public static List<OrderDTO> getOrdersByStatus(OrderStatus status) {
        List<OrderDTO> DTOs = new ArrayList<>();
        List<Order> orders = OrderDB.getOrdersByStatus(status);

        if(orders == null || orders.isEmpty()) return DTOs;

        for (Order order : orders) {
            Map<ItemDTO, Integer> itemDTOs = getItemDTOs(order);

            DTOs.add(new OrderDTO(
                    order.getUsername(),
                    order.getStatus(),
                    itemDTOs,
                    order.getId()
            ));

        }

        return DTOs;
    }

    /**
     * Assigns order to staff member.
     *
     * @param orderId the order's ID
     * @param staffId the staff member's ID
     * @return true if assignment was successful.
     */
    public static boolean assignOrderToStaff(int orderId, int staffId) {
        return OrderDB.assignOrderToStaff(orderId, staffId);
    }

    /**
     * Returns all orders assigned to the specified staff member.
     *
     * @param staffId the staff member's ID
     * @return a list of assigned orders as OrderDTO objects
     */
    public static List<OrderDTO> getAssignedOrders(int staffId) {
        List<OrderDTO> DTOs = new ArrayList<>();
        List<Order> orders = OrderDB.getOrdersByAssignedStaff(staffId);

        if(orders == null || orders.isEmpty()) return DTOs;

        for (Order order : orders) {
            Map<ItemDTO, Integer> itemDTOs = getItemDTOs(order);

            DTOs.add(new OrderDTO(
                    order.getUsername(),
                    order.getStatus(),
                    itemDTOs,
                    order.getId()
            ));

        }

        return DTOs;
    }

    private static Map<ItemDTO, Integer> getItemDTOs(Order order) {
        Map<Item, Integer> orderItems = order.getItems();
        Map<ItemDTO, Integer> itemDTOs = new HashMap<>();

        for (Map.Entry<Item, Integer> entry : orderItems.entrySet()) {
            Item item = entry.getKey();
            Integer quantity = entry.getValue();

            ItemDTO itemDTO = new ItemDTO(
                    item.getName(),
                    item.getDescription(),
                    item.getCategory(),
                    item.getPrice(),
                    item.getStock(),
                    item.getId()
            );

            itemDTOs.put(itemDTO, quantity);
        }
        return itemDTOs;
    }

    /**
     * Updates order to be packaged.
     *
     * @param orderId the order's ID
     * @return true if the order was updated successful, false otherwise
     */
    public static boolean packageOrder(int orderId) {
        return OrderDB.packageOrder(orderId);
    }

    /**
     * Updates an item's data.
     *
     * @param updateItem the item data to update
     * @return true if the item was updated, false otherwise
     */
    public static boolean updateItem(ItemDTO updateItem) {
        Item item = ItemDB.getItemById(updateItem.id());
        if (item == null) {
            return false;
        }

        item.setName(updateItem.name());
        item.setDescription(updateItem.description());
        item.setPrice(updateItem.price());
        item.setStock(updateItem.stock());

        return ItemDB.updateItem(item);
    }
}
