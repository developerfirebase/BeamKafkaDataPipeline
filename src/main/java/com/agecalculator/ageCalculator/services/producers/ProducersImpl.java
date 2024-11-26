package com.agecalculator.ageCalculator.services.producers;


import com.agecalculator.ageCalculator.records.Payload;
import com.agecalculator.ageCalculator.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.kafka.KafkaIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.beam.runners.direct.DirectRunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class ProducersImpl implements ProducerService {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    private final ObjectMapper objectMapper;

    public ProducersImpl() {
        this.objectMapper = new ObjectMapper();
    }

    public void sendMessage(List<Payload> message, String topic) throws IOException {
        PipelineOptions options = PipelineOptionsFactory.create();
        options.setRunner(DirectRunner.class);

        Pipeline pipeline = Pipeline.create(options);

        PCollection<KV<String, String>> messages = pipeline.apply(
                Create.of(KV.of("key", objectMapper.writeValueAsString(message)))
        );

        messages.apply(KafkaIO.<String, String>write()
                .withBootstrapServers(Constants.BOOTSTRAPS_KAFKA)
                .withTopic(topic)
                .withKeySerializer(StringSerializer.class)
                .withValueSerializer(StringSerializer.class)
        );

        pipeline.run().waitUntilFinish();
    }
}
