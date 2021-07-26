package com.codechallenge.mortalkombat.dto;

import java.util.ArrayList;
import java.util.List;

public class PlayerResponseDTO {

    private List<String> result;

    public PlayerResponseDTO() {
        this.result = new ArrayList<>();
    }

    public void addResultElement(String resultElement) {
        this.result.add(resultElement);
    }

    public List<String> getResult() {
        return this.result;
    }

    @Override
    public String toString() {
        return "PlayerResponseDTO{" +
                "result=" + this.result +
                '}';
    }
}
