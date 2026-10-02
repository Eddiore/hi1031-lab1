package org.example.ui;

import java.util.HashMap;

public class ShoppingCart {
    private HashMap<String, Integer> items;

    public ShoppingCart() {
        this.items = new HashMap<>();
    }

    public HashMap<String, Integer> getItems() {
        return items;
    }

    public void addItem(String itemID) {
        if (items.containsKey(itemID)) {
            items.put(itemID, items.get(itemID) + 1);
        } else {
            items.put(itemID, 1);
        }
    }


    public void removeItem(String productID) {
        //Remove item by amount...?
    }

    public double getTotalPrice() {
        double total = 0;

        return total;
    }
}
