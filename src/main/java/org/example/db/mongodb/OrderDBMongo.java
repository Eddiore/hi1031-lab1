// package org.example.db;
//
// import java.util.Map;
//
// import org.bson.Document;
// import org.bson.types.ObjectId;
// import org.example.bo.Item;
// import org.example.bo.Order;
//
// import com.mongodb.TransactionOptions;
// import com.mongodb.WriteConcern;
// import com.mongodb.client.ClientSession;
// import com.mongodb.client.MongoCollection;
// import com.mongodb.client.model.Filters;
// import com.mongodb.client.model.Updates;
// import com.mongodb.client.result.UpdateResult;
//
// public class OrderDB {
//     private static MongoCollection<Document> ordersCollection = null;
//     private static MongoCollection<Document> itemsCollection = null;
//
//     private static void cacheCollection() {
//         if (ordersCollection == null) {
//             ordersCollection = DBManager.getDatabase().getCollection("T_Orders");
//         }
//         if (itemsCollection == null) {
//             itemsCollection = DBManager.getDatabase().getCollection("T_Items");
//         }
//     }
//
//     public static void placeOrder(Order order) {
//         cacheCollection();
//
//         try (ClientSession session = DBManager.getClient().startSession()) {
//             TransactionOptions options = TransactionOptions.builder()
//                     .writeConcern(WriteConcern.MAJORITY)
//                     .build();
//
//             session.withTransaction(() -> {
//                 for (Map.Entry<Item, Integer> entry : order.getItems().entrySet()) {
//                     Item item = entry.getKey();
//                     int quantity = entry.getValue();
//
//                     UpdateResult result = itemsCollection.updateOne(
//                             session,
//                             Filters.and(
//                                     Filters.eq("_id", new ObjectId(item.getId())),
//                                     Filters.gte("stock", quantity)),
//                             Updates.inc("stock", -quantity));
//
//                     if (result.getModifiedCount() != 1) {
//                         throw new IllegalStateException("Not enough stock for item: " + item.getName());
//                     }
//                 }
//
//                 ordersCollection.insertOne(session, new Document()
//                         .append("username", order.getUsername())
//                         .append("items", order.getItems().entrySet().stream()
//                                 .map(entry -> new Document()
//                                         .append("itemId", entry.getKey().getId())
//                                         .append("nrOfItems", entry.getValue()))
//                                 .toList()));
//
//                 return null;
//             }, options);
//         }
//     }
// }
