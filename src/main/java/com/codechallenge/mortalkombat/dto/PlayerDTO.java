package com.codechallenge.mortalkombat.dto;

import com.codechallenge.mortalkombat.entity.Player;

import java.util.Objects;

public class PlayerDTO {

    private String name;
    private String type;

    public PlayerDTO() {
    }

    public PlayerDTO(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Player toEntity() {
        return new Player(this.name, this.type);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerDTO playerDTO = (PlayerDTO) o;
        return Objects.equals(name, playerDTO.name) && Objects.equals(type, playerDTO.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    @Override
    public String toString() {
        return "PlayerDTO{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
