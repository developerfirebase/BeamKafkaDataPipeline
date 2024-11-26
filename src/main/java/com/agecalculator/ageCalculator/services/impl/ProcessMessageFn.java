package com.agecalculator.ageCalculator.services.impl;

import com.agecalculator.ageCalculator.config.SpringContextHolder;
import com.agecalculator.ageCalculator.records.Payload;
import com.agecalculator.ageCalculator.services.producers.ProducerService;
import com.agecalculator.ageCalculator.utils.Constants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class ProcessMessageFn extends DoFn<KV<String, String>, Void> {

    private transient ObjectMapper objectMapper;
    private transient ProducerService produceMessage;

    @Setup
    public void setUp() {
        this.objectMapper = new ObjectMapper();
        this.produceMessage = SpringContextHolder.getBean(ProducerService.class); // Retrieve ProduceMessage bean
    }

    @ProcessElement
    public void processElement(@Element KV<String, String> element) throws IOException {

        System.out.println("Received message: Key = " + element.getKey() + ", Value = " + element.getValue());
        List<Payload> payload = objectMapper.readValue(element.getValue(), new TypeReference<List<Payload>>() {
        });
        log.info("payload {} :", payload);
        calculateAge(payload);
    }

    private void calculateAge(List<Payload> payloads) throws IOException {
        for (Payload p : payloads) {
            LocalDate dob = LocalDate.parse(p.dob(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int age = Period.between(dob, LocalDate.now()).getYears();
            produceMessage.sendMessage(List.of(p), (age % 2 == 0) ? Constants.EVEN_TOPIC : Constants.ODD_TOPIC);
        }
    }
}

