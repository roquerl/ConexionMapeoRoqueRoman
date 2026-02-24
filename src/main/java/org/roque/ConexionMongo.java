package org.roque;

import com.mongodb.client.*;
import org.bson.Document;

public class ConexionMongo {
    public static void main(String[] args) {

        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        MongoDatabase database = mongoClient.getDatabase("bibliotecaDB");
        MongoCollection<Document> collection = database.getCollection("biblioteca");

        FindIterable<Document> docs = collection.find();

        for (Document doc : docs) {
            System.out.println(doc.toJson());
        }

        mongoClient.close();
    }
}