package com.kuldeep;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Random;

public class Producer {
    public static void main(String[] args) {
        // For example 192.168.1.1:9092,192.168.1.2:9092
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "com.kuldeep.UserSerializer");

        KafkaProducer<String, UserModel> kafkaProducer = new KafkaProducer<>(properties);
        try {
            Random random = new Random();
            for (int i = 1; i <= 5; i++) {
                System.out.println(i * 10);
                UserModel userModel = new UserModel(i, "Kuldeep", random.nextInt(2) + 21, "B.Tech.");
                kafkaProducer.send(new ProducerRecord<String, UserModel>("User", String.valueOf(i), userModel));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            kafkaProducer.close();
        }
    }
}