package com.codechallenge.mortalkombat.messaging;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class MessageProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);

    @Autowired
    private KafkaTemplate<String, Event> kafkaTemplate;

    public ListenableFuture<SendResult<String, Event>> produce(Event event, String topic) {
        return produce(event, topic, null, null);
    }

    public ListenableFuture<SendResult<String, Event>> produce(Event event, String topic, Integer partition) {
        return produce(event, topic, partition, null);
    }

    public ListenableFuture<SendResult<String, Event>> produce(Event event,
                                                               String topic,
                                                               Integer partition,
                                                               Iterable<Header> headerIterable) {
        LOGGER.info("Start the message producing! event='{}' | topic='{}' | partition='{}' | headerIterable='{}'",
                    event, topic, partition, headerIterable);

        ProducerRecord producerRecord = new ProducerRecord(topic,
                                                           partition,
                                                           event.getInstant().toEpochMilli(),
                                                           event.getKey(),
                                                           event,
                                                           headerIterable);

        return this.kafkaTemplate.send(producerRecord);
    }
}
