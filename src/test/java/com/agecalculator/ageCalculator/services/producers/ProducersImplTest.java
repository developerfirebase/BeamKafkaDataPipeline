package com.agecalculator.ageCalculator.services.producers;


import com.agecalculator.ageCalculator.records.Payload;
import com.agecalculator.ageCalculator.services.consumer.TopicWiseConsume;
import com.agecalculator.ageCalculator.utils.Constants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProducersImplTest {

    @Mock
    private TopicWiseConsume topicWiseConsume;

    @InjectMocks
    private ProducersImpl producers;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void sendMessageForEvenTest() throws IOException {
        List<Payload> payloads = List.of(Payload.builder().name("kafka").address("Bangalore").dob("1984-10-20").build());
        producers.sendMessage(payloads, Constants.SOURCE_TOPIC);
        given(topicWiseConsume.getEvenMessage()).willReturn(objectMapper.writeValueAsString(payloads));
        List<Payload> payloadreader = objectMapper.readValue(topicWiseConsume.getEvenMessage(), new TypeReference<List<Payload>>() {
        });
        assertEquals(payloads, payloadreader );
    }

    @Test
    public void sendMessageForOddTest() throws IOException {
        List<Payload> payloads = List.of(Payload.builder().name("kafka").address("Delhi").dob("1995-09-01").build());
        producers.sendMessage(payloads, Constants.SOURCE_TOPIC);
        given(topicWiseConsume.getOddMessage()).willReturn(objectMapper.writeValueAsString(payloads));
        List<Payload> payloadreader  = objectMapper.readValue(topicWiseConsume.getOddMessage(), new TypeReference<List<Payload>>() {
        });
        assertEquals(payloads, payloadreader );
    }

}
