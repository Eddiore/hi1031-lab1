package org.example.bo;

public class Item {
    private String name;
    private String description;
    private String category;
    private int price;
    private int stock;
    private int id;

    public Item(String name, String description, String category, int price, int stock) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public Item(String name, String description, String category, int price, int stock, int id) {
        this(name , description , category, price, stock);
        this.id = id;
    }

    public Item(Item other) {
        this.name = other.name;
        this.description = other.description;
        this.category = other.category;
        this.price = other.price;
        this.stock = other.stock;
        this.id = other.id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public int getStock() { return stock; }

    public void setStock(int stock) { this.stock = stock; }

    public int getId() { return id; }
}
