package com.example.back_playerdata_microservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "playerDatas")
public class PlayerData {
    @Id
    private String id;
    private String playerDataCode;
    private String typeName;
    private String name;
    private int health;
    private int happiness;
    private LocalDateTime lastFed;
    private LocalDateTime lastPetted;
    private LocalDateTime timeInHealthZone;
    private int age;

    public PlayerData(String playerDataCode) {
        this.playerDataCode = playerDataCode;
    }

    public PlayerData(String playerDataCode, String typeName, String name, int health, int happiness, LocalDateTime lastFed, LocalDateTime lastPetted, LocalDateTime timeInHealthZone, int age) {
        this.playerDataCode = playerDataCode;
        this.typeName = typeName;
        this.name = name;
        this.health = health;
        this.happiness = happiness;
        this.lastFed = lastFed;
        this.lastPetted = lastPetted;
        this.timeInHealthZone = timeInHealthZone;
        this.age = age;
    }

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

    public LocalDateTime getTimeInHealthZone() {
        return timeInHealthZone;
    }

    public void setTimeInHealthZone(LocalDateTime timeInHealthZone) {
        this.timeInHealthZone = timeInHealthZone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
