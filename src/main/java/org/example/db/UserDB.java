package org.example.db;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import org.bson.Document;
import org.example.bo.User;
import org.example.bo.enums.UserRole;

import java.util.ArrayList;
import java.util.List;

public class UserDB {
    private static MongoCollection<Document> collection = null;

    private static void cacheCollection() {
        if (collection == null) {
            collection = DBManager.getDatabase().getCollection("T_Users");
        }
    }

    public static User getUser(String username) {
        cacheCollection();

        Document filter = new Document("username", username);

        try {
            Document doc = collection.find(filter).first();
            if (doc != null) {
                return new User(doc.getString("username"),
                        doc.getString("passwordHash"),
                        UserRole.valueOf(doc.getString("role")),
                        doc.getObjectId("_id").toString()
                );

            }
        } catch (MongoException e) {
            System.out.println("Unable to login as user: " + username);
        }

        return null;
    }

    public static boolean createUser(User user) {
        cacheCollection();

        Document filter = new Document("username", user.getUsername());

        try {
            List<Document> result = collection.find(filter).into(new ArrayList<>());
            if (!result.isEmpty()) {
                System.out.println("User already exists: " + user.getUsername());
                return false;
            }

            Document doc = new Document()
                    .append("username", user.getUsername())
                    .append("passwordHash", user.getPasswordHash())
                    .append("role", user.getRole().toString());
            collection.insertOne(doc);
        } catch (MongoException e) {
            System.out.println("Unable to create user: " + user.getUsername());
        }

        return true;
    }

    public static boolean updateUser(User user) {
        cacheCollection();

        Document filter = new Document("username", user.getUsername());

        try {
            collection.updateOne(eq(filter),
                    combine(set("passwordHash", user.getPasswordHash()),set("role", user.getRole()))
            );
        } catch (MongoException e) {
            System.out.println("Unable to update user: " + user.getUsername());
            return false;
        }

        return true;
    }
}
