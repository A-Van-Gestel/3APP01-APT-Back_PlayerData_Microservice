package com.example.back_playerdata_microservice.controller;

import com.example.back_playerdata_microservice.repository.PlayerDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerDataController {

    @Autowired
    private PlayerDataRepository playerDataRepository;
}
