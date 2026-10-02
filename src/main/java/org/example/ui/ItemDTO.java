package org.example.ui;

public class ItemDTO {
    private final String id;
    private final String name;
    private final String description;

    public ItemDTO(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getDescription() { return description; }

    public String getName() { return name; }

    public String getPrice() { return "10$"; }

    public String getId() { return id; }
}
