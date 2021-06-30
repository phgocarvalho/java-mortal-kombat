package com.codechallenge.mortalkombat.dto;

import java.util.ArrayList;
import java.util.List;

public class PlayerResponseDTO {

    private List<String> result;

    public PlayerResponseDTO() {
        this.result = new ArrayList<>();
    }

    public void addResultElement(String resultElement){
        result.add(resultElement);
    }

    public List<String> getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "PlayerResponseDTO{" +
                "result=" + result +
                '}';
    }
}
