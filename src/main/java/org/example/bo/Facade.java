package org.example.bo;

import org.example.db.ItemDB;
import org.example.db.UserDB;
import org.example.ui.ItemDTO;
import org.example.ui.UserDTO;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

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

    public static UserDTO loginAsUser(String username, String password) {
        User user = UserDB.getUser(username);
        if (user == null) { return null; }

        if (!BCrypt.checkpw(password, user.getPasswordHash())) { return null; }

        return new UserDTO(user.getUsername(), user.getPasswordHash(), user.getRole(), user.getId());
    }
}
