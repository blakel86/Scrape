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

    public Mongo() throws IOException {
        mongo = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
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

    public static void createIndexes(){
        col.createIndex(String.valueOf(Indexes.ascending("year")));
        col.createIndex(String.valueOf(Indexes.ascending("number")));
        col.createIndex(String.valueOf(Indexes.ascending("song")));
        col.createIndex(String.valueOf(Indexes.ascending("artist")));
        col.createIndex(String.valueOf(Indexes.ascending("length")));
        col.createIndex(String.valueOf(Indexes.ascending("country")));
        col.createIndex(String.valueOf(Indexes.descending("year")));
        col.createIndex(String.valueOf(Indexes.descending("number")));
        col.createIndex(String.valueOf(Indexes.descending("song")));
        col.createIndex(String.valueOf(Indexes.descending("artist")));
        col.createIndex(String.valueOf(Indexes.descending("length")));
        col.createIndex(String.valueOf(Indexes.descending("country")));
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

    public static void returnAllQuery(){
        BasicDBObject returnAllQuery = new BasicDBObject();

        DBCursor cursor = col.find(returnAllQuery, fields);
        while(cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

//    public static void queryGUI(){
//        BasicDBObject queryGUI = new BasicDBObject();
//
//        if(GUI.yearSearchValue != "SELECT") {
//            queryGUI.put("year", new BasicDBObject("$eq", GUI.yearSearchValue));
//        }
//
//        if(GUI.numberSearchValue != "SELECT") {
//            queryGUI.put("number", new BasicDBObject("$eq", GUI.numberSearchValue));
//        }
//
//        if(GUI.countrySearchValue != "SELECT") {
//            queryGUI.put("country", new BasicDBObject("$eq", GUI.countrySearchValue));
//        }
//
//        DBCursor cursor = col.find(queryGUI, fields);
//        while(cursor.hasNext()) {
//            System.out.println(cursor.next());
//        }
//    }

    public static void queryClose() throws InterruptedException {
        fields();
        createIndexes();
        returnAllQuery();
        top3Query();
//        mongo.dropDatabase("hottest100DB");
        TimeUnit.SECONDS.sleep(1);
        mongo.close();
    }
}
