package TEBKafka;

import com.mongodb.client.MongoCollection;
import kafka.log.Log;
import kafka.utils.ShutdownableThread;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.bson.Document;

import java.sql.Time;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

import static TEBKafka.ConnectToDB.db;

/**
 * Kafka Consumer with Example Java Application
 */
public class SampleConsumer extends ShutdownableThread{
    private final KafkaConsumer<Integer, String> consumer;
    private final String topic;

    private static final String KAFKA_SERVER_URL = "zookeeper";
    private static final int KAFKA_SERVER_PORT = 9092;
    private static final String CLIENT_ID = "SampleConsumer";

    SampleConsumer(String topic) {
        super("KafkaConsumerExample", false);
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER_URL + ":" + KAFKA_SERVER_PORT);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, CLIENT_ID);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        consumer = new KafkaConsumer<>(props);
        this.topic = topic;
    }

    @Override
    public void doWork() {
        consumer.subscribe(Collections.singletonList(this.topic));
        ConsumerRecords<Integer, String> records = consumer.poll(1000);
        for (ConsumerRecord<Integer, String> record : records) {
            System.out.println("Message received: " + record.value());
            String[] Logs = record.value().split(" ");
            if(Logs.length >= 4){

                String TimeStamp = Logs[0] + " " + Logs[1];
                String LogType = Logs[2];
                String LogCity = Logs[3];
                String LogDetail = String.join(" ", Arrays.copyOfRange(Logs, 4, Logs.length));
                MongoCollection<Document> collection = db.getCollection(LogCity);
                Document doc = new Document("Time", TimeStamp)
                        .append("type", LogType)
                        .append("city", LogCity)
                        .append("Detail", LogDetail);
                collection.insertOne(doc);
            }
        }

    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public boolean isInterruptible() {
        return false;
    }
}