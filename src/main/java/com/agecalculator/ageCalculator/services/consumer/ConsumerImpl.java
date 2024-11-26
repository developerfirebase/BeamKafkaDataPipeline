package com.agecalculator.ageCalculator.services.consumer;

import com.agecalculator.ageCalculator.services.impl.ProcessMessageFn;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.kafka.KafkaIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.runners.direct.DirectRunner;
import org.apache.kafka.common.serialization.StringDeserializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerImpl implements ConsumerService, ApplicationRunner {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Value("${kafka.topics}")
    private List<String> topics;

    @Autowired
    private ProcessMessageFn processMessageFn;

    @Override
    public void run(ApplicationArguments args) {
        topics.forEach(this::consumeMessages);
    }

    public void consumeMessages(String topic) {
        PipelineOptions options = PipelineOptionsFactory.create();
        options.setRunner(DirectRunner.class);

        Pipeline pipeline = Pipeline.create(options);

        pipeline.apply(KafkaIO.<String, String>read()
                        .withBootstrapServers(kafkaServer)
                        .withTopic(topic)
                        .withKeyDeserializer(StringDeserializer.class)
                        .withValueDeserializer(StringDeserializer.class)
                        .withoutMetadata()
                )
                .apply("ProcessMessage", ParDo.of(new ProcessMessageFn()));

        pipeline.run().waitUntilFinish();
    }


}
