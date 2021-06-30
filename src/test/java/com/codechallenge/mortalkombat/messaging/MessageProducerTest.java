package com.codechallenge.mortalkombat.messaging;

import com.codechallenge.mortalkombat.configuration.TopicProperties;
import com.codechallenge.mortalkombat.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaFailureCallback;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class MessageProducerTest {

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private TopicProperties topicProperties;

    @BeforeEach
    void setUp() {
    }

    @Test
    void send() {
        Player player = new Player("Sub zero", "expert");
        Event event = new Event(Event.Type.CREATE, player.getName(), player);

        messageProducer.send(event, topicProperties.novicePlayers).addCallback(sendResult -> {
            assertTrue(true);
        }, (KafkaFailureCallback<Integer, String>) kafkaProducerException -> {
            assertTrue(false);
        });
    }
}