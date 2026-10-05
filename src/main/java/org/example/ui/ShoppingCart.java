package org.example.ui;

import java.util.HashMap;

public class ShoppingCart {
    private HashMap<ItemDTO, Integer> items;

    public ShoppingCart() {
        this.items = new HashMap<>();
    }

    public HashMap<ItemDTO, Integer> getItems() {
        return items;
    }

    public void addItem(ItemDTO item) {
        if (items.containsKey(item)) {
            items.put(item, items.get(item) + 1);
        } else {
            items.put(item, 1);
        }
    }

    public void removeItem(ItemDTO item) {
        if (items.containsKey(item)) {
            if (items.get(item) > 1) {
                items.put(item, items.get(item) - 1);
            } else {
                items.remove(item);
            }
        }
    }

    public double getTotalCost() {
        double total = 0;
        for (ItemDTO key : items.keySet()) {
            total += (key.price() * items.get(key));
        }

        return total;
    }

    public void clearCart() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
