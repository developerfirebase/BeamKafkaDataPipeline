package com.agecalculator.ageCalculator.controller;

import com.agecalculator.ageCalculator.records.Payload;
import com.agecalculator.ageCalculator.services.TriggerProducerService;
import com.agecalculator.ageCalculator.services.consumer.ConsumerService;
import com.agecalculator.ageCalculator.services.consumer.TopicWiseConsume;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/")
public class TriggerKafkaController {

    @Autowired
    private TriggerProducerService triggerProducerService;

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private TopicWiseConsume tc;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody List<Payload> payload) {
        try {
            triggerProducerService.triggerProducerEvent(payload);
        } catch (IOException e) {
            return new ResponseEntity<>(payload, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(payload, HttpStatus.OK);
    }

    @GetMapping("/consume")
    public Map<String, String> getLatestMessage() {
        Map<String, String> message = tc.getMessage();
        return message;
    }

}
