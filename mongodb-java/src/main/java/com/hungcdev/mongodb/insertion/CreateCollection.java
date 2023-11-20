package com.hungcdev.mongodb.insertion;

import com.hungcdev.mongodb.connection.MongoDBConnection;
import com.hungcdev.mongodb.data.Post;
import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class CreateCollection {

    private  MongoDatabase mongoDatabase;

    public CreateCollection(String ip, int port, String databaseName) {
        MongoClient mongoClient = MongoDBConnection.connectMongoDBWithPOJOs(ip, port);
        this.mongoDatabase = mongoClient.getDatabase(databaseName);
    }

    public boolean collectionExists(String collection)
    {
        if(mongoDatabase.getCollection(collection) != null)
        {
            return true;
        }
        else{return false;}
    }
    public  <T> void create(String collectionName){
        mongoDatabase.createCollection(collectionName);
    }



    public <T> void insertMany(String collectionName, Class<T> classType, T... documents) {
        MongoCollection<T> collection = mongoDatabase.getCollection(collectionName, classType);
        collection.insertMany(List.of(documents));
    }


    public static void main(String[] args) {

        String collectionName = "BusTest";
        CreateCollection create = new CreateCollection("localhost", 27017, "5DHIF" );

        //Create Collection
        if(!create.collectionExists(collectionName))
        {create.create(collectionName);}

        //Read From Pokemon

       CreateCollection pokemon = new CreateCollection("localhost", 27017, "Pokemon" );
        BasicDBObject basicDBObject_SearchCondition = new BasicDBObject();


        Bson projection = Projections.fields(Projections.include("name"),Projections.exclude("_id"));
        FindIterable<Document>  docu =  pokemon.mongoDatabase.getCollection("Pokemon").find().projection(projection);


        Iterator itr = docu.iterator();
        int i = 0;
        while (itr.hasNext()) {
            System.out.println(itr.next());
            i++;
        }
        System.out.println(i);


        Bson projectionFields = Projections.fields(
                Projections.include("name"),
                Projections.excludeId());
        // Retrieves the first matching document, applying a projection and a descending sort to the results
       Document doc = pokemon.mongoDatabase.getCollection("Pokemon").find(eq("name", "Mew"))
                .projection(projectionFields)
                .sort(Sorts.descending())
                .first();

       if(doc != null) {
           System.out.println(doc.toJson());
       }


        MongoCollection sal =  pokemon.mongoDatabase.getCollection("sales");
        FindIterable<Document> sales =  pokemon.mongoDatabase.getCollection("sales").find();
        Document multiplyDisc = new Document("$multiply", Arrays.asList("$price", "$quantity" ));
        Document groupFields = new Document();
        groupFields.append("_id", "$_id");
        AggregateIterable<Document> group = sal.aggregate(
                Arrays.asList(
                Aggregates.group(groupFields,
                        Accumulators.avg("avg_price", "$price"),
                        Accumulators.sum("sumPriceQuantity", multiplyDisc)
                ))
        );


        Iterator salesItr = sales.iterator();
        i = 0;
        while (salesItr.hasNext()) {
            System.out.println(salesItr.next());
            i++;
        }
        System.out.println(i);

        Iterator groupItr = group.iterator();
         i = 0;
        while (groupItr.hasNext()) {
            System.out.println(groupItr.next());
            i++;
        }
        System.out.println(i);








    }




}
