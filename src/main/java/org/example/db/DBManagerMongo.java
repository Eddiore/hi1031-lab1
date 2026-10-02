package org.example.db;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DBManagerMongo {
    private MongoClient mongoClient = null;
    private final MongoDatabase database;

    private DBManagerMongo() {
        String host = "mongodb:27017/Webshop";
        String username = "webapp_user";
        String password = "Abcde123%23";

        try {
            this.mongoClient = MongoClients.create("mongodb://webapp_user:Abcde123%23@mongodb:27017/Webshop"); //doesn't throw exceptions...
            this.database = mongoClient.getDatabase("Webshop");

            database.runCommand(new Document("ping", 1).append("maxTimeMS", 500));
        } catch (MongoException e) {
            System.out.println("Error when establishing connection to Database...");
            if (mongoClient != null) { mongoClient.close(); }
            throw new RuntimeException(e);
        }
    }

    private static class InstanceHolder {
        private static final DBManagerMongo INSTANCE = new DBManagerMongo();
    }

    public static DBManagerMongo getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static MongoClient getClient() {
        return getInstance().mongoClient;
    }

    public static MongoDatabase getDatabase() {
        return getInstance().database;
    }
}
