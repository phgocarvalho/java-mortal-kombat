package com.codechallenge.mortalkombat.service;

import com.codechallenge.mortalkombat.dto.PlayerDTO;
import com.codechallenge.mortalkombat.dto.PlayerRequestDTO;
import com.codechallenge.mortalkombat.dto.PlayerResponseDTO;
import com.codechallenge.mortalkombat.entity.Player;
import com.codechallenge.mortalkombat.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class PlayerServiceTest {

    @Autowired
    private PlayerService service;

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

        PlayerResponseDTO playerResponseDTO = service.process(playerRequestDTO);

        assertEquals(3, playerResponseDTO.getResult().size());
        assertEquals("player Sub zero stored in DB", playerResponseDTO.getResult().get(0));
        assertEquals("player Scorpion sent to Kafka topic", playerResponseDTO.getResult().get(1));
        assertEquals( "player Reptile did not fit", playerResponseDTO.getResult().get(2));
    }

    @Test
    void findAll() {
        Player player = new Player("Sub zero", "expert");

        repository.save(player);

        List<PlayerDTO> playerDTOList = service.findAll();

        assertEquals(1, playerDTOList.size());
    }
}