package com.agecalculator.ageCalculator.services;

import com.agecalculator.ageCalculator.records.Payload;

import java.io.IOException;
import java.util.List;

public interface TriggerProducerService {

    void triggerProducerEvent(List<Payload> payload) throws IOException;
}
