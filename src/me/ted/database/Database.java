package me.ted.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.logging.Level;
import java.util.logging.Logger;

// Utility Class

/*
 * This class handles the setup connection between our project and the database.
 * This class is also considered a utility class where anything database-related can be found here
 * such as ID's of some the documents, functionally method to find whether a collection exists or not
 * or just to count the amount of collections in the database.
 *
 */

public class Database {

    // Set up our data fields to handle the connection
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    // IDs of useful documents that should not be changed.
    public static final ObjectId coursesObjectId = new ObjectId("63e66c0a667c9ba31d638f16");
    public static final ObjectId scheduleBlocksObjectId = new ObjectId("63e66950667c9ba31d638f15");
    public static final ObjectId timeTableObjectId = new ObjectId("63e7dc294b3e3da2b67ce623");

    /**
     * This method handles the setup to our data base
     */
    public static void setupDatabase() {

        // Hide unnecessary outputs from mongoDB
        Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
        Logger.getLogger("com.mongodb").setLevel(Level.SEVERE);

        System.out.println("Setting up the database...");

        // Connect our project to the database
        mongoClient = MongoClients.create("mongodb://ted:pu6YmPHRFFjKvgvX@ac-zkqzsck-shard-00-00.dkh7h8v.mongodb.net:27017,ac-zkqzsck-shard-00-01.dkh7h8v.mongodb.net:27017,ac-zkqzsck-shard-00-02.dkh7h8v.mongodb.net:27017/?ssl=true&replicaSet=atlas-aan9j0-shard-0&authSource=admin&retryWrites=true&w=majority");
        database = mongoClient.getDatabase("StudentSchedules");

        System.out.println("Connected to the database successfully!");

    }

    /**
     * This method determines whether a collection in the database exists or not.
     *
     * @param collectionName The collection name to search for
     * @return the result of finding. True means the collection exists, False means the collection does not exists.
     */

    public static boolean exists(String collectionName) {

        boolean exists = false;

        for (String str : database.listCollectionNames()) {
            if (str.equalsIgnoreCase(collectionName)) {
                exists = true;
                break;
            }
        }

        return exists;
    }

    /**
     * This method counts the amount of existing collections in the database.
     *
     * @return amount of collections in the database
     */

    public static int countCollections() {

        int count = 0;
        for (Document ignored : database.listCollections()) {
            count++;
        }

        return count;
    }

    public static MongoDatabase getDatabase() {
        return database;
    }

    public static MongoClient getMongoClient() {
        return mongoClient;
    }

}
