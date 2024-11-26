package com.agecalculator.ageCalculator.services.consumer;

import com.agecalculator.ageCalculator.utils.Constants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class TopicWiseConsume {

    private final BlockingQueue<String> messages_source = new LinkedBlockingQueue<>();
    private final BlockingQueue<String> even_source = new LinkedBlockingQueue<>();
    private final BlockingQueue<String> odd_source = new LinkedBlockingQueue<>();

    @KafkaListener(topics = Constants.SOURCE_TOPIC, groupId = "mygroup")
    public void consumeSourceMessage(ConsumerRecord<String, String> record) {
        messages_source.add(record.value());
    }

    public String getSourceMessage() {
        return messages_source.poll();
    }

    @KafkaListener(topics = Constants.SOURCE_TOPIC, groupId = "mygroup")
    public void consumeEvenMessage(ConsumerRecord<String, String> record) {
        even_source.add(record.value());
    }

    public String getEvenMessage() {
        return even_source.poll();
    }

    @KafkaListener(topics = Constants.SOURCE_TOPIC, groupId = "mygroup")
    public void consumeOddMessage(ConsumerRecord<String, String> record) {
        odd_source.add(record.value());
    }

    public String getOddMessage() {
        return odd_source.poll();
    }

    private final BlockingQueue<Map<String, String>> messages = new LinkedBlockingQueue<>();

    @KafkaListener(topics = {Constants.SOURCE_TOPIC, Constants.ODD_TOPIC, Constants.EVEN_TOPIC}, groupId = "mygroup")
    public void consumeMessage(ConsumerRecord<String, String> record) throws InterruptedException {
        String topic = record.topic();
        String message = record.value();
        Map<String, String> map = new HashMap<>();
        map.put(topic, message);
        messages.put(map);

    }

    public Map<String, String> getMessage() {
        return messages.poll();
    }
}
