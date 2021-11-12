package com.example.back_playerdata_microservice;

import com.example.back_playerdata_microservice.model.PlayerData;
import com.example.back_playerdata_microservice.repository.PlayerDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class PlayerDataIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlayerDataRepository playerDataRepository;

    private final PlayerData playerData1 = new PlayerData("12345abcde","Slijmie","Rimu Tempest",80,50, LocalDateTime.of(2017, 2, 13, 15, 56, 42),LocalDateTime.of(2017, 2, 13, 15, 56, 5),30);
    private final PlayerData playerData2 = new PlayerData("abcde12345","Slakkie","Slakkie Slak",70,60,LocalDateTime.of(2019, 3, 4, 15, 56, 12),LocalDateTime.of(2019, 3, 5, 15, 56, 35),40);

    private final PlayerData playerDataToBeDeleted = new PlayerData("zzzzz99999","TheDeletedOne","The Sacrifice",99,99,LocalDateTime.of(2000, 1, 1, 1, 1, 1),LocalDateTime.of(2000, 1, 2, 2, 2, 2),99);

    @BeforeEach
    public void beforeAllTests() {
        playerDataRepository.deleteAll();
        playerDataRepository.save(playerData1);
        playerDataRepository.save(playerData2);
        playerDataRepository.save(playerDataToBeDeleted);
    }

    @AfterEach
    public void afterAllTests() {
        //Watch out with deleteAll() methods when you have other data in the test database!
        playerDataRepository.deleteAll();
    }

    private final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new ParameterNamesModule())
            .addModule(new Jdk8Module())
            .addModule(new JavaTimeModule())
            .build();

    @Test
    void givenPlayerData_whenGetAllPlayerDatas_thenReturnJsonPlayerDatas() throws Exception {

        mockMvc.perform(get("/playerDatas"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // Array length is correct
                .andExpect(jsonPath("$", hasSize(3)))
                // playerData1 is correct
                .andExpect(jsonPath("$[0].playerDataCode",is("12345abcde")))
                .andExpect(jsonPath("$[0].typeName",is("Slijmie")))
                .andExpect(jsonPath("$[0].name",is("Rimu Tempest")))
                .andExpect(jsonPath("$[0].health",is(80)))
                .andExpect(jsonPath("$[0].happiness",is(50)))
                .andExpect(jsonPath("$[0].lastFed",is("2017-02-13T15:56:42")))
                .andExpect(jsonPath("$[0].lastPetted",is("2017-02-13T15:56:05")))
                .andExpect(jsonPath("$[0].age",is(30)))
                // playerData2 is correct
                .andExpect(jsonPath("$[1].playerDataCode",is("abcde12345")))
                .andExpect(jsonPath("$[1].typeName",is("Slakkie")))
                .andExpect(jsonPath("$[1].name",is("Slakkie Slak")))
                .andExpect(jsonPath("$[1].health",is(70)))
                .andExpect(jsonPath("$[1].happiness",is(60)))
                .andExpect(jsonPath("$[1].lastFed",is("2019-03-04T15:56:12")))
                .andExpect(jsonPath("$[1].lastPetted",is("2019-03-05T15:56:35")))
                .andExpect(jsonPath("$[1].age",is(40)))
                // playerDataToBeDeleted is correct
                .andExpect(jsonPath("$[2].playerDataCode",is("zzzzz99999")))
                .andExpect(jsonPath("$[2].typeName",is("TheDeletedOne")))
                .andExpect(jsonPath("$[2].name",is("The Sacrifice")))
                .andExpect(jsonPath("$[2].health",is(99)))
                .andExpect(jsonPath("$[2].happiness",is(99)))
                .andExpect(jsonPath("$[2].lastFed",is("2000-01-01T01:01:01")))
                .andExpect(jsonPath("$[2].lastPetted",is("2000-01-02T02:02:02")))
                .andExpect(jsonPath("$[2].age",is(99)));
    }
}
