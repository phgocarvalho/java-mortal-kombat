package com.codechallenge.mortalkombat.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Import(TopicProperties.class)
public class TopicConfiguration {

    @Autowired
    private TopicProperties topicProperties;

    /*
    A bean alternative to create the used topic. Instead, it using the auto create alternative by
    the "KAFKA_AUTO_CREATE_TOPICS_ENABLE" environment variable in the "docker-compose.yml" file.

    @Bean
    public NewTopic topicExample() {
        return TopicBuilder.name(topicProperties.novicePlayers)
                .partitions(1)
                .replicas(1)
                .build();
    }
    */
}
