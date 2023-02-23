package me.ted.database;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    private final DataCollection database;
    private static MongoClient mongoClient;

    public static final ObjectId scheduleBlocksObjectId = new ObjectId("63e66950667c9ba31d638f15");
    public static final ObjectId timeTableObjectId = new ObjectId("63e7dc294b3e3da2b67ce623");

    public static Database studentDatabase;
    public static Database adviserDatabase;
    public static Database facultyDatabase;
    public static Database scheduleDatabase;
    public static Database utilsDatabase;

    public Database(DataCollection database) {
        this.database = database;

        if (mongoClient.getDatabase(database.getCollectionName()).listCollectionNames().first() == null) {
            mongoClient.getDatabase(database.getCollectionName()).createCollection("admin");
            System.out.println("Created a new database named: " + database.getCollectionName());
        }

    }

    public MongoDatabase getDatabase() {
        return mongoClient.getDatabase(database.getCollectionName());
    }

    public int countCollections() {

        int count = 0;
        for (Document ignored : getDatabase().listCollections()) {
            count++;
        }

        return count;
    }

    public boolean exists(String collectionName) {

        boolean exists = false;

        for (String str : getDatabase().listCollectionNames()) {
            if (str.equalsIgnoreCase(collectionName)) {
                exists = true;
                break;
            }
        }

        return exists;
    }

    public static void connectDatabase() {

        // Hide unnecessary outputs from mongoDB
        Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
        Logger.getLogger("com.mongodb").setLevel(Level.SEVERE);

        System.out.println("Connecting to the mongoDB...");

        mongoClient = MongoClients.create("mongodb://ted:pu6YmPHRFFjKvgvX@ac-zkqzsck-shard-00-00.dkh7h8v.mongodb.net:27017,ac-zkqzsck-shard-00-01.dkh7h8v.mongodb.net:27017,ac-zkqzsck-shard-00-02.dkh7h8v.mongodb.net:27017/?ssl=true&replicaSet=atlas-aan9j0-shard-0&authSource=admin&retryWrites=true&w=majority");

        System.out.println("Connected to the mongoDB successfully!");
    }

    public static void setupDatabase() {
        studentDatabase = new Database(DataCollection.STUDENTS);
        adviserDatabase = new Database(DataCollection.ACADEMIC_ADVISERS);
        facultyDatabase = new Database(DataCollection.FACULTIES);
        scheduleDatabase = new Database(DataCollection.SCHEDULES);
        utilsDatabase = new Database(DataCollection.UTILITY);
    }

}
