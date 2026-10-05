package org.example.bo;

import org.example.bo.enums.OrderStatus;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private String username;
    private OrderStatus status;
    private Map<Item, Integer> items;
    private int id;

    public Order() {
        this.items = new HashMap<>();
    }

    public Order(String username, OrderStatus status, Map<Item, Integer> items) {
        this.username = username;
        this.status = status;
        this.items = items;
    }

    public Order(String username, OrderStatus status, Map<Item, Integer> items, int id) {
        this(username, status, items);
        this.id = id;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public OrderStatus getStatus() { return status; }

    public void setStatus(OrderStatus status) { this.status = status; }

    public Map<Item, Integer> getItems() {
        Map<Item, Integer> deepCopy = new HashMap<>();

        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            Item copiedItem = new Item(entry.getKey());

            deepCopy.put(copiedItem, entry.getValue());
        }

        return deepCopy;
    }

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

    public int getId() { return id; }
}
