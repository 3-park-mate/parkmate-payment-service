package com.parkmate.paymentservice.kafka.config.produce;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

public abstract class CommonProduceKafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    protected String bootstrapServer;

    abstract Map<String, Object> getStringObjectMap();

    protected <T> ProducerFactory<String, T> createProducerFactory() {
        return new DefaultKafkaProducerFactory<>(getStringObjectMap());
    }

    protected <T> KafkaTemplate<String, T> createKafkaTemplate(Class<T> targetClass) {
        return new KafkaTemplate<>(createProducerFactory());
    }
}
