package org.example.ui;

public class ItemDTO {
    private String id;
    private String name;
    private String description;

    public ItemDTO(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getDescription() { return description; }

    public String getName() { return name; }
}
