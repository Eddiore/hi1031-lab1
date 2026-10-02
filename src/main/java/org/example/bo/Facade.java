package org.example.bo;

import org.example.db.DBItemMongo;
import org.example.ui.ItemDTO;

import java.util.ArrayList;
import java.util.List;

public class Facade {

    public static List<ItemDTO> getAllItems() {
        List<ItemDTO> DTOs = new ArrayList<>();
        List<Item> items = DBItemMongo.getAllItems();
        if (items == null) { return DTOs; }

        for (Item item : items) {
            DTOs.add(new ItemDTO(item.getId(), item.getName(), item.getDescription()));
        }

        return DTOs;
    }
}
