package org.example.bo;

import org.example.db.ItemDB;
import org.example.db.OrderDB;
import org.example.db.UserDB;
import org.example.ui.ItemDTO;
import org.example.ui.UserDTO;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Facade {
    public static List<ItemDTO> getAllItems() {
        List<ItemDTO> DTOs = new ArrayList<>();
        List<Item> items = ItemDB.getAllItems();
        if (items == null || items.isEmpty()) { return DTOs; }

        for (Item item : items) {
            DTOs.add(new ItemDTO(item.getName(),
                    item.getDescription(),
                    item.getCategory(),
                    item.getPrice(),
                    item.getStock(),
                    item.getId())
            );
        }

        return DTOs;
    }

    public static List<ItemDTO> getItemsByCategory(String category) {
        List<ItemDTO> DTOs = new ArrayList<>();
        List<Item> items = ItemDB.getItemsByCategory(category);
        if (items == null || items.isEmpty()) { return DTOs; }

        for (Item item : items) {
            DTOs.add(new ItemDTO(item.getName(),
                    item.getDescription(),
                    item.getCategory(),
                    item.getPrice(),
                    item.getStock(),
                    item.getId())
            );
        }

        return DTOs;
    }

    public static ItemDTO getItemById(String id) {
        Item item = ItemDB.getItemById(Integer.valueOf(id));

        if (item == null) { return null; }

        return new ItemDTO(item.getName(),
                item.getDescription(),
                item.getCategory(),
                item.getPrice(),
                item.getStock(),
                item.getId()
        );
    }

    public static List<UserDTO> getAllUsers() {
        List<UserDTO> DTOs = new ArrayList<>();
        List<User> users = UserDB.getAllUsers();

        if (users == null || users.isEmpty()) return DTOs;

        for (User user : users) {
            DTOs.add(new UserDTO(
                    user.getUsername(),
                    user.getPasswordHash(),
                    user.getRole(),
                    user.getId()
            ));
        }

        return DTOs;
    }

    public static UserDTO getUserById(String id) {
        User user = UserDB.getUserById(Integer.parseInt(id));

        if (user == null) { return null; }

        return new UserDTO(
                user.getUsername(),
                user.getPasswordHash(),
                user.getRole(),
                user.getId()
        );
    }

    public static boolean updateUser(UserDTO updateUser) {
        User user = UserDB.getUserById(updateUser.id());
        if (user == null) { return false; }

        user.setRole(updateUser.role());
        user.setUsername(updateUser.username());

        return UserDB.updateUser(user);
    }

    public static UserDTO loginAsUser(String username, String password) {
        User user = UserDB.getUser(username);
        if (user == null) { return null; }

        if (!BCrypt.checkpw(password, user.getPasswordHash())) { return null; }

        return new UserDTO(user.getUsername(), user.getPasswordHash(), user.getRole(), user.getId());
    }

    public static boolean placeOrder(UserDTO user, HashMap<ItemDTO, Integer> items) {

        HashMap<Item, Integer> itemMap = new HashMap<>();

        for (Map.Entry<ItemDTO, Integer> entry : items.entrySet()) {
            Item item = convertToItem(entry.getKey());
            itemMap.put(item, entry.getValue());
        }

        Order order = new Order(
                user.username(),
                itemMap
        );

        return OrderDB.placeOrder(order);
    }

    private static Item convertToItem(ItemDTO DTO) {

        return new Item(
                DTO.name(),
                DTO.description(),
                DTO.category(),
                DTO.price(),
                DTO.stock(),
                DTO.id()
        );
    }
}
