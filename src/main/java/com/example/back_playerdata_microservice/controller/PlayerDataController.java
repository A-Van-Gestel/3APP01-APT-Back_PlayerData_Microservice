package com.example.back_playerdata_microservice.controller;

import com.example.back_playerdata_microservice.model.PlayerData;
import com.example.back_playerdata_microservice.model.PlayerDataDTO;
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
    public PlayerData addPlayerData(@RequestBody PlayerDataDTO playerData) {
        PlayerData tempPlayerData = new PlayerData();
        PlayerData persistentPlayerData = getPlayerDataFromPlayerDataDTO(tempPlayerData, playerData);
        playerDataRepository.save(persistentPlayerData);
        return persistentPlayerData;
    }

    @PutMapping("/playerData")
    public PlayerData modifyPlayerData(@RequestBody PlayerDataDTO updatedPlayerData) {
        PlayerData tempPlayerData = playerDataRepository.findPlayerDataByPlayerDataCode(updatedPlayerData.getPlayerDataCode());
        PlayerData retrievedPlayerData = getPlayerDataFromPlayerDataDTO(tempPlayerData, updatedPlayerData);

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

    // Make a real PlayerData from the PlayerDataDTO
    private PlayerData getPlayerDataFromPlayerDataDTO(PlayerData playerData, PlayerDataDTO playerDataDTO) {
        playerData.setPlayerDataCode(playerDataDTO.getPlayerDataCode());
        playerData.setTypeName(playerDataDTO.getTypeName());
        playerData.setName(playerDataDTO.getName());
        playerData.setHealth(playerDataDTO.getHealth());
        playerData.setHappiness(playerDataDTO.getHappiness());
        playerData.setLastFed(playerDataDTO.getLastFed());
        playerData.setLastPetted(playerDataDTO.getLastPetted());
        playerData.setAge(playerDataDTO.getAge());

        return playerData;
    }
}
