package TEBKafka;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.apache.commons.io.input.TailerListenerAdapter;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/**
 * Producer Example in Apache Kafka
 * @author www.tutorialkart.com
 */

class LogListener extends TailerListenerAdapter {
    private final KafkaProducer producer1;
    private final String topic1;
    private int lineNo = 0;
    LogListener(KafkaProducer producer, String topic){
        producer1 = producer;
        topic1 = topic;
    }
    public void handle(String line) {
        System.out.println("Sent message: (" + lineNo + ", " + line + ")");
        try {
            producer1.send(new ProducerRecord(topic1,
                    lineNo,
                    line)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(line);
        this.lineNo++;
    }
}
public class SampleProducer extends Thread {
    private final KafkaProducer producer;
    private final String topic;

    private static final String KAFKA_SERVER_URL = "zookeeper";
    private static final int KAFKA_SERVER_PORT = 9092;
    private static final String CLIENT_ID = "SampleProducer";

    SampleProducer(String topic) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", KAFKA_SERVER_URL + ":" + KAFKA_SERVER_PORT);
        properties.put("client.id", CLIENT_ID);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer(properties);
        this.topic = topic;
    }

    public void run() {
        int messageNo = 1;
        while (messageNo < 2) {
            TailerListener listener = new LogListener(producer, topic);
            Tailer tailer = new Tailer(new File("/data/Log.txt"), listener, 1000);
            System.out.println(tailer.getFile());
            Executor executor = Runnable::run;

            executor.execute(tailer);
            ++messageNo;
        }
    }
}