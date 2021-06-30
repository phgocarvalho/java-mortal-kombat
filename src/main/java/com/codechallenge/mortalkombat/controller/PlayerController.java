package com.codechallenge.mortalkombat.controller;

import com.codechallenge.mortalkombat.dto.PlayerDTO;
import com.codechallenge.mortalkombat.dto.PlayerRequestDTO;
import com.codechallenge.mortalkombat.dto.PlayerResponseDTO;
import com.codechallenge.mortalkombat.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerController.class);

    @Autowired
    private PlayerService service;

    @PostMapping
    public ResponseEntity<?> process(@RequestBody PlayerRequestDTO playerRequestDTO){
        LOGGER.info("Start the player request processing! playerRequestDTO='{}'", playerRequestDTO);

        ResponseEntity<?> responseEntity;

        try {
            PlayerResponseDTO playerResponseDTO = service.process(playerRequestDTO);

            responseEntity = ResponseEntity.ok(playerResponseDTO);

            LOGGER.info("Success in the player request processing! playerDTO='{}'", playerResponseDTO);
        }catch (Exception exception){
            responseEntity = ResponseEntity.internalServerError().body(exception);

            LOGGER.error("Error in the player request processing!", exception);
        }

        return responseEntity;
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        LOGGER.info("Start the players finding!");

        ResponseEntity<?> responseEntity;

        try {
            List<PlayerDTO> playerDTOList = service.findAll();

            responseEntity = ResponseEntity.ok(playerDTOList);

            LOGGER.info("Success in the players finding! playerDTOList='{}'", playerDTOList);
        }catch (Exception exception){
            responseEntity = ResponseEntity.internalServerError().body(exception);

            LOGGER.error("Error in the players finding!", exception);
        }

        return responseEntity;
    }
}
