package org.roque;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * A) Programa 2: conexión a MongoDB y ejecución de find.
 */
public class ConexionMongo {

    public static void main(String[] args) {
        String conexion = System.getProperty("mongo.uri", "mongodb://localhost:27017");
        String filtroTitulo = args.length > 0 ? args[0] : null;

        try (MongoClient mongoClient = MongoClients.create(conexion)) {
            MongoDatabase database = mongoClient.getDatabase("bibliotecaDB");
            MongoCollection<Document> collection = database.getCollection("biblioteca");

            Document filtro = filtroTitulo == null
                    ? new Document()
                    : new Document("titulo", filtroTitulo);

            FindIterable<Document> docs = collection.find(filtro);

            System.out.println("=== Resultado MongoDB ===");
            System.out.println("Filtro aplicado: " + filtro.toJson());
            docs.forEach(doc -> System.out.println(doc.toJson()));
        }
    }
}
