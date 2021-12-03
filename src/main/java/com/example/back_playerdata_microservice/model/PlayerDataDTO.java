package com.example.back_playerdata_microservice.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PlayerDataDTO {
    private String playerDataCode;
    private String typeName;
    private String name;
    private int health;
    private int happiness;
    private LocalDateTime lastFed;
    private LocalDateTime lastPetted;
    private int age;
}
