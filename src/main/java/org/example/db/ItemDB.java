package org.example.db;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.bo.Item;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

public class ItemDB {
    private static MongoCollection<Document> collection = null;

    private static void cacheCollection() {
        if (collection == null) {
            collection = DBManager.getDatabase().getCollection("T_Items");
        }
    }

    public static List<Item> getAllItems() {
        cacheCollection();

        List<Item> out = new ArrayList<>();

        try {
            List<Document> allItems = collection.find().into(new ArrayList<>());
            for (Document doc : allItems) {
                out.add(new Item(doc.getString("name"),
                        doc.getString("description"),
                        doc.getString("category"),
                        doc.getInteger("price"),
                        doc.getInteger("stock"),
                        doc.getObjectId("_id").toString())
                );
            }
        } catch (MongoException e) {
            System.out.println("Error getting all items: " + e.getMessage());
        }

        return out;
    }

    public static List<Item> getItemsByCategory(String category) {
        cacheCollection();

        List<Item> out = new ArrayList<>();

        Document filter = new Document("category", category);
        try {
            List<Document> allItems = collection.find(filter).into(new ArrayList<>());
            for (Document doc : allItems) {
                out.add(new Item(doc.getString("name"),
                        doc.getString("description"),
                        doc.getString("category"),
                        doc.getInteger("price"),
                        doc.getInteger("stock"),
                        doc.getObjectId("_id").toString())
                );
            }
        } catch (MongoException e) {
            System.out.println("Error getting items from category: " + e.getMessage());
        }

        return out;
    }

    public static Item getItemById(String id) {
        cacheCollection();

        Item out = null;

        Document filter = new Document("_id", new ObjectId(id));
        try {
            Document doc = collection.find(filter).first();
            if (doc != null) {
                out = new Item(
                        doc.getString("name"),
                        doc.getString("description"),
                        doc.getString("category"),
                        doc.getInteger("price"),
                        doc.getInteger("stock"),
                        doc.getObjectId("_id").toString()
                );
            }
        } catch (MongoException e) {
            System.out.println("Error getting item by id: " + e.getMessage());
        }

        return out;
    }
}
