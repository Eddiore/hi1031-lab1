package org.example.db;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.example.bo.Item;

import java.util.ArrayList;
import java.util.List;

public class DBItem  {
    private static MongoCollection<Document> collection = null;

//    public DBItem() {
//        collection = DBManager.getDatabase().getCollection("items");
//    }

    public static List<Item> getAllItems() {
        if (collection == null) {
            collection = DBManager.getDatabase().getCollection("items");
        }

        ArrayList<Item> wArray = new ArrayList<>();
        List<Document> allItems;
        try {
            allItems = collection.find().into(new ArrayList<>());
        } catch (MongoException e) {
            System.out.println("Collection not found...");
            return null; // TEMP!!!
        }

        for (Document doc : allItems) {
            wArray.add(new Item(doc.getObjectId("_id").toString(), doc.getString("name"), doc.getString("description")));
        }

        return wArray;
    }

//    public DBItem getItemWithName(String name) {
//        Document doc = collection.find(Filters.eq("name", name)).first();
//
//        assert doc != null;
//        DBItem dbItem = new DBItem(doc.getString("_id"),
//                                 doc.getString("name"),
//                                 doc.getString("description"));
//
//        return dbItem;
//    }

}
