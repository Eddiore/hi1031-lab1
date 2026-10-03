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

    public double getTotalPrice() {
        double total = 0;

        return total;
    }
}
