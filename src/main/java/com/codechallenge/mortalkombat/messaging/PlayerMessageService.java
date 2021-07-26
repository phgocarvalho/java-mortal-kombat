package com.codechallenge.mortalkombat.messaging;

import com.codechallenge.mortalkombat.configuration.TopicProperties;
import com.codechallenge.mortalkombat.entity.Player;
import com.codechallenge.mortalkombat.service.PlayerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CountDownLatch;

@Component
public class PlayerMessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerMessageService.class);

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private TopicProperties topicProperties;

    @Autowired
    private PlayerService playerService;

    public ListenableFuture<SendResult<String, Event>> produce(Event event) {
        return this.messageProducer.produce(event, this.topicProperties.novicePlayers);
    }

    @KafkaListener(topics = "${messaging.topic.novicePlayers}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ConsumerRecord<String, Event<Player>> consumerRecord) {
        LOGGER.info("Start the message consuming! consumerRecord='{}'", consumerRecord);

        Event<Player> event = consumerRecord.value();

        this.countDownLatch.countDown();
        this.playerService.save(event.getData());
    }

    public CountDownLatch getCountDownLatch() {
        return this.countDownLatch;
    }
}
