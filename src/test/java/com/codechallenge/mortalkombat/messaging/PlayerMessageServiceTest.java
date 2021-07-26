package com.codechallenge.mortalkombat.messaging;

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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class PlayerMessageServiceTest {

    @Autowired
    private PlayerMessageService playerMessageService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void produce() {
        Player player = new Player("Sub zero", "expert");
        Event<Player> event = new Event<>(player.getName(), player);

        this.playerMessageService.produce(event)
                .addCallback(sendResult -> {
                    assertTrue(true);
                }, (KafkaFailureCallback<Integer, String>) kafkaProducerException -> {
                    assertTrue(false);
                });
    }

    @Test
    void consume() throws InterruptedException {
        Player player = new Player("Sub zero", "expert");
        Event<Player> event = new Event<>(player.getName(), player);

        this.playerMessageService.produce(event)
                .addCallback(sendResult -> {
                    assertTrue(true);
                }, (KafkaFailureCallback<Integer, String>) kafkaProducerException -> {
                    assertTrue(false);
                });

        CountDownLatch countDownLatch = this.playerMessageService.getCountDownLatch();

        countDownLatch.await(10000, TimeUnit.MILLISECONDS);

        assertEquals(0L, countDownLatch.getCount());
    }
}