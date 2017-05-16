/**
 * Created by laroux0b on 5/05/2017.
 */

import com.mongodb.*;
import com.mongodb.client.model.Indexes;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Mongo {

    public static DB db;
    public static MongoClient mongo;
    public static BasicDBObject doc;
    public static DBCollection col;
    public static BasicDBObject fields;
    public static Boolean doesDatabaseExist;

    public Mongo() throws IOException {
        mongo = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
    }

    public static Boolean checkDatabaseStatus() throws IOException {
        db = mongo.getDB("hottest100DB");
        if(db.collectionExists("hottest100")){
            System.out.println("Database exits");
            doesDatabaseExist = true;
        }
        else{
            System.out.println("Database does not exist");
            doesDatabaseExist = false;
        }
        return doesDatabaseExist;
    }

    public void createMongoDB(){
        mongo.dropDatabase("hottest100DB");
        db = new DB(mongo, "hottest100DB");
        db.createCollection("hottest100", doc);
        col = db.getCollection("hottest100");
    }

    public static void addRecord(Hottest100 hottest100) {
        doc = new BasicDBObject("year", hottest100.getYear())
                .append("number", hottest100.getNumber())
                .append("song", hottest100.getSong())
                .append("artist", hottest100.getArtist())
                .append("length", hottest100.getLength())
                .append("country", hottest100.getCountry());
        col.insert(doc);
    }

    public static BasicDBObject fields(){
        fields = new BasicDBObject();

        fields.put("_id", 0);
        fields.put("year", 1);
        fields.put("number", 1);
        fields.put("song", 1);
        fields.put("artist", 1);
        fields.put("length", 1);
        fields.put("country", 1);

        return fields;
    }

    public static void top3Query(){
        BasicDBObject top3Query = new BasicDBObject();

        top3Query.put("number", new BasicDBObject("$lt", 4));

        DBCursor cursor = col.find(top3Query, fields);
        while(cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    public static void createIndexes(){
        col.createIndex(String.valueOf(Indexes.ascending("year")));
        System.out.println("Ascending Index for Year created");
        col.createIndex(String.valueOf(Indexes.ascending("number")));
        System.out.println("Ascending Index for Number created");
        col.createIndex(String.valueOf(Indexes.ascending("song")));
        System.out.println("Ascending Index for Song created");
        col.createIndex(String.valueOf(Indexes.ascending("artist")));
        System.out.println("Ascending Index for Artist created");
        col.createIndex(String.valueOf(Indexes.ascending("length")));
        System.out.println("Ascending Index for Length created");
        col.createIndex(String.valueOf(Indexes.ascending("country")));
        System.out.println("Ascending Index for Country created");

        col.createIndex(String.valueOf(Indexes.descending("year")));
        System.out.println("Descending Index for Year created");
        col.createIndex(String.valueOf(Indexes.descending("number")));
        System.out.println("Descending Index for Number created");
        col.createIndex(String.valueOf(Indexes.descending("song")));
        System.out.println("Descending Index for Song created");
        col.createIndex(String.valueOf(Indexes.descending("artist")));
        System.out.println("Descending Index for Artist created");
        col.createIndex(String.valueOf(Indexes.descending("length")));
        System.out.println("Descending Index for Length created");
        col.createIndex(String.valueOf(Indexes.descending("country")));
        System.out.println("Descending Index for Country created");
    }

    public static void returnAllQuery(){
        BasicDBObject returnAllQuery = new BasicDBObject();

        DBCursor cursor = col.find(returnAllQuery, fields);
        while(cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    public static void queryClose() throws InterruptedException {
        fields();
//        createIndexes();
        returnAllQuery();
        top3Query();
//        mongo.dropDatabase("hottest100DB");
        TimeUnit.SECONDS.sleep(1);
//        mongo.close();
    }

    public static void mongoClose(){
        mongo.close();
    }

    public static void main(String[] args) throws IOException {
        MongoClient mongoDrop = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        mongoDrop.dropDatabase("hottest100DB");
        mongoDrop.close();
    }
}
