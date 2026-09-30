package org.example.db;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DBManager {
//    private static DBManager instance = null;
    private MongoClient mongoClient = null; //"Connection"
    private final MongoDatabase database;

//    private static DBManager getInstance() {
//        if (instance == null) {
//            instance = new DBManager();
//        }
//        return instance;
//    }

    private DBManager() {
        try {
            this.mongoClient = MongoClients.create("mongodb://mongodb:27017"); //doesn't throw exceptions...
            this.database = mongoClient.getDatabase("Distribidiy");

            database.runCommand(new Document("ping", 1).append("maxTimeMS", 500));
        } catch (MongoException e) {
            System.out.println("Error when establishing connection to Database...");
            if (mongoClient != null) { mongoClient.close(); }
            throw new RuntimeException(e);
        }
//        System.out.println("DATABASER_____");
//        for (String dbName : mongoClient.listDatabaseNames()) {
//            System.out.println("Databas: " + dbName);
//        }
    }

    private static class InstanceHolder {
        private static final DBManager INSTANCE = new DBManager();
    }

    public static DBManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static MongoClient getClient() {
        return getInstance().mongoClient;
    }

    public static MongoDatabase getDatabase() {
        return getInstance().database;
    }

//    private DBManager() {
//        try {
//            mongoClient = MongoClients.create("mongodb://localhost:27017");
//            database =  mongoClient.getDatabase("Distribidy");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
