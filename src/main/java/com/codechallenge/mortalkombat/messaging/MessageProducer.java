package com.codechallenge.mortalkombat.messaging;

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

    public ListenableFuture<SendResult<String, Event>> send(Event event, String topic) {
        LOGGER.info("Start the message sending! event='{}' | topic='{}'", event, topic);

        return kafkaTemplate.send(topic, event);
    }
}
