package com.codechallenge.mortalkombat.controller;

import com.codechallenge.mortalkombat.dto.PlayerDTO;
import com.codechallenge.mortalkombat.dto.PlayerRequestDTO;
import com.codechallenge.mortalkombat.entity.Player;
import com.codechallenge.mortalkombat.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class PlayerControllerTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    private PlayerRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void process() {
        List<PlayerDTO> playerDTOList = Arrays.asList(
                new PlayerDTO("Sub zero", "expert"),
                new PlayerDTO("Scorpion", "novice"),
                new PlayerDTO("Reptile", "meh"));
        PlayerRequestDTO playerRequestDTO = new PlayerRequestDTO(playerDTOList);

        client.post()
                .uri("/players")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(playerRequestDTO), PlayerRequestDTO.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .json("{\"result\":[" +
                        "\"player Sub zero stored in DB\"," +
                        "\"player Scorpion sent to Kafka topic\"," +
                        "\"player Reptile did not fit\"" +
                        "]}");
    }

    @Test
    void findAll() {
        Player player = new Player("Sub zero", "expert");

        repository.save(player);
        client.get()
                .uri("/players")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(PlayerDTO.class)
                .contains(player.toDTO())
                .hasSize(1);
    }
}