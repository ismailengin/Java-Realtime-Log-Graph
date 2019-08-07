package TEBKafka;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;


/**
 *  Kafka Consumer with Example Java Application
 */

class ConnectToDB {

    public static MongoDatabase db = null;

    public static void connect() {

        // Creating a Mongo client
        MongoClient mongoClient = MongoClients.create("mongodb://db:27017");
        db = mongoClient.getDatabase("Logs");


    }
}

public class Main {
    public static void main(String[] args) {
        Thread.UncaughtExceptionHandler h = (th, ex) -> System.out.println("Uncaught exception: " + ex);
        ConnectToDB.connect();
        SampleConsumer consumerThread =  new SampleConsumer("testTopic");
        consumerThread.setUncaughtExceptionHandler(h);
        consumerThread.start();
    }
}