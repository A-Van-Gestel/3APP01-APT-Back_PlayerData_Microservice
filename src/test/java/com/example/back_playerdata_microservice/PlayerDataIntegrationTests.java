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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    // Gives back a list of all PlayerDatas
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


    // Gives 1 playerData back, searched on playerDataCode (playerData1)
    @Test
    void givenPlayerData_whenGetPlayerDataById_thenReturnJsonPlayerData1() throws Exception {
        mockMvc.perform(get("/playerData/{playerDataCode}","12345abcde")) // command
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // playerData1 is correct
                .andExpect(jsonPath("$.playerDataCode",is("12345abcde")))
                .andExpect(jsonPath("$.typeName",is("Slijmie")))
                .andExpect(jsonPath("$.name",is("Rimu Tempest")))
                .andExpect(jsonPath("$.health",is(80)))
                .andExpect(jsonPath("$.happiness",is(50)))
                .andExpect(jsonPath("$.lastFed",is("2017-02-13T15:56:42")))
                .andExpect(jsonPath("$.lastPetted",is("2017-02-13T15:56:05")))
                .andExpect(jsonPath("$.age",is(30)));
    }


    // Gives 1 playerData back, searched on playerDataCode (playerData2)
    @Test
    void givenPlayerData_whenGetPlayerDataById_thenReturnJsonPlayerData2() throws Exception {
        mockMvc.perform(get("/playerData/{playerDataCode}","abcde12345")) // command
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // playerData2 is correct
                .andExpect(jsonPath("$.playerDataCode",is("abcde12345")))
                .andExpect(jsonPath("$.typeName",is("Slakkie")))
                .andExpect(jsonPath("$.name",is("Slakkie Slak")))
                .andExpect(jsonPath("$.health",is(70)))
                .andExpect(jsonPath("$.happiness",is(60)))
                .andExpect(jsonPath("$.lastFed",is("2019-03-04T15:56:12")))
                .andExpect(jsonPath("$.lastPetted",is("2019-03-05T15:56:35")))
                .andExpect(jsonPath("$.age",is(40)));
    }


    // Gives 1 playerData back, searched on playerDataCode (playerDataToBeDeleted)
    @Test
    void givenPlayerData_whenGetPlayerDataById_thenReturnJsonPlayerDataToBeDeleted() throws Exception {
        mockMvc.perform(get("/playerData/{playerDataCode}","zzzzz99999")) // command
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // playerDataToBeDeleted is correct
                .andExpect(jsonPath("$.playerDataCode",is("zzzzz99999")))
                .andExpect(jsonPath("$.typeName",is("TheDeletedOne")))
                .andExpect(jsonPath("$.name",is("The Sacrifice")))
                .andExpect(jsonPath("$.health",is(99)))
                .andExpect(jsonPath("$.happiness",is(99)))
                .andExpect(jsonPath("$.lastFed",is("2000-01-01T01:01:01")))
                .andExpect(jsonPath("$.lastPetted",is("2000-01-02T02:02:02")))
                .andExpect(jsonPath("$.age",is(99)));
    }


    // Gives all playerData that has a typeName containing "Sl" back, searched on typeName
    @Test
    void givenPlayerData_whenGetPlayerDataByTypeName_theReturnJsonPlayerDatas() throws Exception {
        mockMvc.perform(get("/playerDatas/type/{typeName}","Sl")) // Command
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // Array length is correct
                .andExpect(jsonPath("$", hasSize(2)))
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
                .andExpect(jsonPath("$[1].age",is(40)));
    }


    // Gives all playerData that has a typeName containing "Slijmie" back, searched on typeName
    @Test
    void givenPlayerData_whenGetPlayerDataByTypeName_theReturnJsonPlayerData1() throws Exception {
        mockMvc.perform(get("/playerDatas/type/{typeName}","Slijmie")) // Command
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // Array length is correct
                .andExpect(jsonPath("$", hasSize(1)))
                // playerData1 is correct
                .andExpect(jsonPath("$[0].playerDataCode",is("12345abcde")))
                .andExpect(jsonPath("$[0].typeName",is("Slijmie")))
                .andExpect(jsonPath("$[0].name",is("Rimu Tempest")))
                .andExpect(jsonPath("$[0].health",is(80)))
                .andExpect(jsonPath("$[0].happiness",is(50)))
                .andExpect(jsonPath("$[0].lastFed",is("2017-02-13T15:56:42")))
                .andExpect(jsonPath("$[0].lastPetted",is("2017-02-13T15:56:05")))
                .andExpect(jsonPath("$[0].age",is(30)));
    }


    // Gives all playerData that has a typeName containing "Slakkie" back, searched on typeName
    @Test
    void givenPlayerData_whenGetPlayerDataByTypeName_theReturnJsonPlayerData2() throws Exception {
        mockMvc.perform(get("/playerDatas/type/{typeName}","Slakkie")) // Command
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // Array length is correct
                .andExpect(jsonPath("$", hasSize(1)))
                // playerData2 is correct
                .andExpect(jsonPath("$[0].playerDataCode",is("abcde12345")))
                .andExpect(jsonPath("$[0].typeName",is("Slakkie")))
                .andExpect(jsonPath("$[0].name",is("Slakkie Slak")))
                .andExpect(jsonPath("$[0].health",is(70)))
                .andExpect(jsonPath("$[0].happiness",is(60)))
                .andExpect(jsonPath("$[0].lastFed",is("2019-03-04T15:56:12")))
                .andExpect(jsonPath("$[0].lastPetted",is("2019-03-05T15:56:35")))
                .andExpect(jsonPath("$[0].age",is(40)));
    }


    // Gives all playerData that has a typeName containing "TheDeletedOne" back, searched on typeName
    @Test
    void givenPlayerData_whenGetPlayerDataByTypeName_theReturnJsonPlayerDataToBeDeleted() throws Exception {
        mockMvc.perform(get("/playerDatas/type/{typeName}","TheDeletedOne")) // Command
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // Array length is correct
                .andExpect(jsonPath("$", hasSize(1)))
                // playerDataToBeDeleted is correct
                .andExpect(jsonPath("$[0].playerDataCode",is("zzzzz99999")))
                .andExpect(jsonPath("$[0].typeName",is("TheDeletedOne")))
                .andExpect(jsonPath("$[0].name",is("The Sacrifice")))
                .andExpect(jsonPath("$[0].health",is(99)))
                .andExpect(jsonPath("$[0].happiness",is(99)))
                .andExpect(jsonPath("$[0].lastFed",is("2000-01-01T01:01:01")))
                .andExpect(jsonPath("$[0].lastPetted",is("2000-01-02T02:02:02")))
                .andExpect(jsonPath("$[0].age",is(99)));
    }


    // When adding a new PlayerData, also give it back
    @Test
    void whenPostPlayerData_thenReturnJsonPlayerData() throws Exception {
        PlayerData playerDataPost = new PlayerData("Fluffy12345","Een pluisbol","Fluffy Pluisbol",160,80, LocalDateTime.of(2021, 5, 12, 15, 35, 59),LocalDateTime.of(2021, 5, 12, 15, 35, 41),1);

        mockMvc.perform(post("/playerData") // Command
                        .content(mapper.writeValueAsString(playerDataPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // playerData Fluffie Pluisbol is correct
                .andExpect(jsonPath("$.playerDataCode",is("Fluffy12345")))
                .andExpect(jsonPath("$.typeName",is("Een pluisbol")))
                .andExpect(jsonPath("$.name",is("Fluffy Pluisbol")))
                .andExpect(jsonPath("$.health",is(160)))
                .andExpect(jsonPath("$.happiness",is(80)))
                .andExpect(jsonPath("$.lastFed",is("2021-05-12T15:35:59")))
                .andExpect(jsonPath("$.lastPetted",is("2021-05-12T15:35:41")))
                .andExpect(jsonPath("$.age",is(1)));
    }


    // When updating a PlayerData, get back the updated PlayerData
    @Test
    void givenPlayerData_whenPutPlayerData_thenReturnJsonPlayerData() throws Exception {
        PlayerData playerDataPut = new PlayerData("12345abcde", "Slijmie", "Rimu Tempest the second", 5000, 100, LocalDateTime.of(2017, 2, 13, 15, 56, 42), LocalDateTime.of(2017, 2, 13, 15, 56, 5), 9999);

        mockMvc.perform(put("/playerData") // Command
                        .content(mapper.writeValueAsString(playerDataPut))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // playerData1 is correct
                .andExpect(jsonPath("$.playerDataCode", is("12345abcde")))
                .andExpect(jsonPath("$.typeName", is("Slijmie")))
                .andExpect(jsonPath("$.name", is("Rimu Tempest the second")))
                .andExpect(jsonPath("$.health", is(5000)))
                .andExpect(jsonPath("$.happiness", is(100)))
                .andExpect(jsonPath("$.lastFed", is("2017-02-13T15:56:42")))
                .andExpect(jsonPath("$.lastPetted", is("2017-02-13T15:56:05")))
                .andExpect(jsonPath("$.age", is(9999)));
    }


    // When deleting a PlayerData, give back status OK
    @Test
    void givenPlayerData_whenDeletePlayerData_thenStatusOk() throws Exception {
        mockMvc.perform(delete("/playerData/{playerDataCode}","zzzzz99999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // When deleting a PlayerData that doesn't exist, give back status Not Found
    @Test
    void givenPlayerData_whenDeletePlayerData_thenStatusNotfound() throws Exception {
        mockMvc.perform(delete("/playerData/{playerDataCode}","zzzzz99999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/playerData/{playerDataCode}","zzzzz99999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
