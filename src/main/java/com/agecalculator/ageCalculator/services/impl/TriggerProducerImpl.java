package com.agecalculator.ageCalculator.services.impl;

import com.agecalculator.ageCalculator.records.Payload;
import com.agecalculator.ageCalculator.services.TriggerProducerService;
import com.agecalculator.ageCalculator.services.producers.ProducerService;
import com.agecalculator.ageCalculator.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TriggerProducerImpl implements TriggerProducerService {

    @Autowired
    ProducerService producerService;

    public void triggerProducerEvent(List<Payload> payload) throws IOException {
        producerService.sendMessage(payload, Constants.SOURCE_TOPIC);

    }
}
