// package org.example.db;
//
// import com.mongodb.MongoException;
// import com.mongodb.client.MongoClient;
// import com.mongodb.client.MongoClients;
// import com.mongodb.client.MongoDatabase;
// import org.bson.Document;
//
// public class DBManager {
//     private MongoClient mongoClient = null;
//     private final MongoDatabase database;
//
//     private DBManager() {
//         String databaseName = "Webshop";
//         String username = "webshop_user";
//         String password = System.getenv("WEBSHOP_PASSWORD");
//
//         String uri = "mongodb://" + username + ":" + password + "@mongodb:27017/" + databaseName;
//
//         try {
//             this.mongoClient = MongoClients.create(uri);
//             this.database = mongoClient.getDatabase(databaseName);
//
//             database.runCommand(new Document("ping", 1).append("maxTimeMS", 500));
//         } catch (MongoException e) {
//             System.out.println("Error when establishing connection to Database...");
//             if (mongoClient != null) { mongoClient.close(); }
//             throw new RuntimeException(e);
//         }
//     }
//
//     private static class InstanceHolder {
//         private static final DBManager INSTANCE = new DBManager();
//     }
//
//     public static DBManager getInstance() {
//         return InstanceHolder.INSTANCE;
//     }
//
//     public static MongoClient getClient() {
//         return getInstance().mongoClient;
//     }
//
//     public static MongoDatabase getDatabase() {
//         return getInstance().database;
//     }
// }
