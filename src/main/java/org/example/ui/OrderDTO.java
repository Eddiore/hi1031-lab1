package org.example.ui;

import org.example.bo.enums.OrderStatus;

import java.util.HashMap;
import java.util.Map;

public record OrderDTO(Map<ItemDTO, Integer> items, OrderStatus status, int id, String username) {
    public OrderDTO(String username, OrderStatus status, Map<ItemDTO, Integer> items, int id) {
        this(items, status, id, username);
    }

    @Override
    public Map<ItemDTO, Integer> items() {
        Map<ItemDTO, Integer> deepCopy = new HashMap<>();

        for (Map.Entry<ItemDTO, Integer> entry : items.entrySet()) {
            ItemDTO copiedItem = new ItemDTO(
                    entry.getKey().name(),
                    entry.getKey().description(),
                    entry.getKey().category(),
                    entry.getKey().price(),
                    entry.getKey().stock(),
                    entry.getKey().id()
            );

            deepCopy.put(copiedItem, entry.getValue());
        }

        return deepCopy;
    }
}
