package com.example.back_playerdata_microservice.repository;

import com.example.back_playerdata_microservice.model.PlayerData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerDataRepository extends MongoRepository<PlayerData, String> {
    List<PlayerData> findPlayerDatas();
    PlayerData findPlayerDataById(String id);
    PlayerData findPlayerDataByPlayerDataCode(String playerDataCode);
    List<PlayerData> findPlayerDatasByTypeNameContaining(String typeName);
}
