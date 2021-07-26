package com.codechallenge.mortalkombat.service;

import com.codechallenge.mortalkombat.dto.PlayerDTO;
import com.codechallenge.mortalkombat.dto.PlayerRequestDTO;
import com.codechallenge.mortalkombat.dto.PlayerResponseDTO;
import com.codechallenge.mortalkombat.entity.Player;
import com.codechallenge.mortalkombat.messaging.Event;
import com.codechallenge.mortalkombat.messaging.PlayerMessageService;
import com.codechallenge.mortalkombat.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaFailureCallback;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class PlayerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerService.class);

    @Autowired
    private PlayerRepository repository;

    @Autowired
    private PlayerMessageService messageService;

    public PlayerResponseDTO process(PlayerRequestDTO playerRequestDTO) {
        LOGGER.info("Start the player list processing! playerRequestDTO='{}'", playerRequestDTO);

        PlayerResponseDTO playerResponseDTO = new PlayerResponseDTO();

        playerRequestDTO.getPlayers()
                .stream()
                .map(playerDTO -> process(playerDTO.toEntity()))
                .forEach(resultElement -> playerResponseDTO.addResultElement(resultElement));

        return playerResponseDTO;
    }

    private String process(Player player) {

        switch (player.getType()) {
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

    public void save(Player player) {
        LOGGER.info("Start the player saving! player='{}'", player);

        try {
            this.repository.save(player);

            LOGGER.info("Success in the player saving! player='{}'", player);
        } catch (Exception exception) {
            LOGGER.error("Error in the player saving!", exception);
        }
    }

    private void generateEvent(Player player) {
        Event<Player> event = new Event<>(player.getName(), player);

        this.messageService.produce(event)
                .addCallback(sendResult -> {
                    LOGGER.info("Success in the event generation! sendResult='{}'", sendResult);
                }, (KafkaFailureCallback<Integer, String>) kafkaProducerException -> {
                    LOGGER.error("Error in the event generation!", kafkaProducerException);
                });
    }

    public List<PlayerDTO> findAll() {
        LOGGER.info("Start the players finding!");

        Spliterator<Player> playerSpliterator = this.repository.findAll().spliterator();
        List<PlayerDTO> playerDTOList = StreamSupport.stream(playerSpliterator, false)
                .map(player -> player.toDTO())
                .collect(Collectors.toList());

        LOGGER.info("Success in the players finding! playerDTOList='{}'", playerDTOList);

        return playerDTOList;
    }
}
