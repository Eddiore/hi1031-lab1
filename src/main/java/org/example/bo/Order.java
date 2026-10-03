package org.example.bo;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private String username;
    private Map<Item, Integer> items;

    public Order() {
        this.items = new HashMap<>();
    }

    public Order(String username, Map<Item, Integer> items) {
        this.username = username;
        this.items = items;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public Map<Item, Integer> getItems() { return items; }

    public void setItems(Map<Item, Integer> items) { this.items = items; }

    public void addItem(Item item, int quantity) {
        items.merge(item, quantity, Integer::sum);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public int getQuantity(Item item) {
        return items.getOrDefault(item, 0);
    }
}
