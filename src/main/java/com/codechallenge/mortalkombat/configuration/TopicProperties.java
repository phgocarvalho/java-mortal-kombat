package com.codechallenge.mortalkombat.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "messaging.topic")
public class TopicProperties {

    public String novicePlayers;

    public String getNovicePlayers() {
        return novicePlayers;
    }

    public void setNovicePlayers(String novicePlayers) {
        this.novicePlayers = novicePlayers;
    }
}
