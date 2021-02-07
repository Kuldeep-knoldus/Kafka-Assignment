package com.kuldeep;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Consumer {
    public static void main(String[] args) {
        ConsumerListener c = new ConsumerListener();
        Thread thread = new Thread(c);
        thread.start();
    }

    public static void consumer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "com.kuldeep.UserDeserializer");
        properties.put("group.id", "test-group");

        KafkaConsumer<String, UserModel> kafkaConsumer = new KafkaConsumer(properties);
        List topics = new ArrayList();
        topics.add("User");
        kafkaConsumer.subscribe(topics);
        try {
            FileWriter fileWriter = new FileWriter("Messages.txt", true);
            ObjectMapper mapper = new ObjectMapper();

            while (true) {
                ConsumerRecords<String, UserModel> records = kafkaConsumer.poll(1000);
                for (ConsumerRecord<String, UserModel> record : records) {
                    System.out.println(String.format("Topic : %s, Partition : %d, Value: %s", record.topic(), record.partition(), record.value()));
                    fileWriter.append(mapper.writeValueAsString(record.value()) + "\n");
                }
                fileWriter.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            kafkaConsumer.close();
        }
    }
}

class ConsumerListener implements Runnable {

    @Override
    public void run() {
        Consumer.consumer();
    }
}