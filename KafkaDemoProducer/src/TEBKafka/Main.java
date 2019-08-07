package TEBKafka;

public class Main {
    public static final String TOPIC = "testTopic";
    public static void main(String[] args) {
        boolean isAsync = false;
        SampleProducer producerThread = new  SampleProducer(TOPIC);
        // start the producer
        producerThread.start();
    }
}
