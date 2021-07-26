package com.codechallenge.mortalkombat.dto;

import java.util.List;

public class PlayerRequestDTO {

    private List<PlayerDTO> players;

    public PlayerRequestDTO() {
    }

    public PlayerRequestDTO(List<PlayerDTO> players) {
        this.players = players;
    }

    public List<PlayerDTO> getPlayers() {
        return this.players;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "PlayerRequestDTO{" +
                "players=" + this.players +
                '}';
    }
}
