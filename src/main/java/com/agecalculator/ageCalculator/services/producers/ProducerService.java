package com.agecalculator.ageCalculator.services.producers;

import com.agecalculator.ageCalculator.records.Payload;

import java.io.IOException;
import java.util.List;

public interface ProducerService {

    void sendMessage(List<Payload> message, String topic) throws IOException;
}
