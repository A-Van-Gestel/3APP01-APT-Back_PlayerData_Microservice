package com.example.back_playerdata_microservice.model;

import java.time.LocalDateTime;

public class PlayerDataDTO {
    private String playerDataCode;
    private String typeName;
    private String name;
    private int health;
    private int happiness;
    private LocalDateTime lastFed;
    private LocalDateTime lastPetted;
    private int age;

    public String getPlayerDataCode() {
        return playerDataCode;
    }

    public void setPlayerDataCode(String playerDataCode) {
        this.playerDataCode = playerDataCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public LocalDateTime getLastFed() {
        return lastFed;
    }

    public void setLastFed(LocalDateTime lastFed) {
        this.lastFed = lastFed;
    }

    public LocalDateTime getLastPetted() {
        return lastPetted;
    }

    public void setLastPetted(LocalDateTime lastPetted) {
        this.lastPetted = lastPetted;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
