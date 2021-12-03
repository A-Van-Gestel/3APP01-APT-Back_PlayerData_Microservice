package com.example.back_playerdata_microservice.controller;

import com.example.back_playerdata_microservice.model.PlayerData;
import com.example.back_playerdata_microservice.repository.PlayerDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayerDataController {

    @Autowired
    private PlayerDataRepository playerDataRepository;

    
    @GetMapping("/playerDatas")
    public List<PlayerData> getAll() {
        return playerDataRepository.findAll();
    }

    @GetMapping(value="/playerData/{playerDataCode}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public PlayerData getPlayerDataByPlayerDataCode(@PathVariable String playerDataCode){
        return playerDataRepository.findPlayerDataByPlayerDataCode(playerDataCode);
    }

    @GetMapping("/playerDatas/type/{typeName}")
    public List<PlayerData> getPlayerDatasByTypeName(@PathVariable String typeName){
        return playerDataRepository.findPlayerDataByTypeNameContaining(typeName);
    }

    @PostMapping("/playerData")
    public PlayerData addPlayerData(@RequestBody PlayerData playerData) {
        playerDataRepository.save(playerData);
        return playerData;
    }

    @PutMapping("/playerData")
    public PlayerData modifyPlayerData(@RequestBody PlayerData updatedPlayerData) {
        PlayerData retrievedPlayerData = playerDataRepository.findPlayerDataByPlayerDataCode(updatedPlayerData.getPlayerDataCode());

        retrievedPlayerData.setPlayerDataCode(updatedPlayerData.getPlayerDataCode());
        retrievedPlayerData.setTypeName(updatedPlayerData.getTypeName());
        retrievedPlayerData.setName(updatedPlayerData.getName());
        retrievedPlayerData.setHealth(updatedPlayerData.getHealth());
        retrievedPlayerData.setHappiness(updatedPlayerData.getHappiness());
        retrievedPlayerData.setLastFed(updatedPlayerData.getLastFed());
        retrievedPlayerData.setLastPetted(updatedPlayerData.getLastPetted());
        retrievedPlayerData.setAge(updatedPlayerData.getAge());

        playerDataRepository.save(retrievedPlayerData);

        return retrievedPlayerData;
    }

    @DeleteMapping("/playerData/{playerDataCode}")
    public ResponseEntity<Object> deletePlayerDataCode(@PathVariable String playerDataCode) {
        PlayerData playerData = playerDataRepository.findPlayerDataByPlayerDataCode(playerDataCode);
        if (playerData!=null) {
            playerDataRepository.delete(playerData);
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
