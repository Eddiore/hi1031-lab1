package org.example.ui;

public class ItemDTO {
    private final int id;
    private final String name;
    private final String description;

    public ItemDTO(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getDescription() { return description; }

    public String getName() { return name; }
}
