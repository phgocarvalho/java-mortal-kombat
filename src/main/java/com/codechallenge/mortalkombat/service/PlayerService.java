package com.codechallenge.mortalkombat.service;

import com.codechallenge.mortalkombat.configuration.TopicProperties;
import com.codechallenge.mortalkombat.dto.PlayerDTO;
import com.codechallenge.mortalkombat.dto.PlayerRequestDTO;
import com.codechallenge.mortalkombat.dto.PlayerResponseDTO;
import com.codechallenge.mortalkombat.entity.Player;
import com.codechallenge.mortalkombat.messaging.Event;
import com.codechallenge.mortalkombat.messaging.MessageProducer;
import com.codechallenge.mortalkombat.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaFailureCallback;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class PlayerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerService.class);

    @Autowired
    private PlayerRepository repository;

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private TopicProperties topicProperties;

    public PlayerResponseDTO process(PlayerRequestDTO playerRequestDTO){
        LOGGER.info("Start the player list processing! playerRequestDTO='{}'", playerRequestDTO);

        PlayerResponseDTO playerResponseDTO = new PlayerResponseDTO();

        playerRequestDTO.getPlayers()
                .stream()
                .map(playerDTO -> process(playerDTO))
                .forEach(resultElement -> playerResponseDTO.addResultElement(resultElement));

        return playerResponseDTO;
    }

    private String process(PlayerDTO playerDTO) {
        Player player = playerDTO.toEntity();

        switch (player.getType()){
            case "expert":
                save(player);

                return String.format("player %s stored in DB", player.getName());
            case "novice":
                generateEvent(player);

                return String.format("player %s sent to Kafka topic", player.getName());
            default:
                return String.format("player %s did not fit", player.getName());
        }
    }

    private void save(Player player) {
        LOGGER.info("Start the player saving! player='{}'", player);

        try {
            repository.save(player);

            LOGGER.info("Success in the player saving! player='{}'", player);
        }catch (Exception exception) {
            LOGGER.error("Error in the player saving!", exception);
        }
    }

    private void generateEvent(Player player) {
        Event event = new Event(Event.Type.CREATE, player.getName(), player);

        messageProducer.send(event, topicProperties.novicePlayers).addCallback(sendResult -> {
            LOGGER.info("Success in the event generation! sendResult='{}'", sendResult);
        }, (KafkaFailureCallback<Integer, String>) kafkaProducerException -> {
            LOGGER.error("Error in the event generation!", kafkaProducerException);
        });
    }

    public List<PlayerDTO> findAll() {
        LOGGER.info("Start the players finding!");

        List<PlayerDTO> playerDTOList = StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(player -> player.toDTO())
                .collect(Collectors.toList());

        LOGGER.info("Success in the players finding! playerDTOList='{}'", playerDTOList);

        return playerDTOList;
    }
}
