package com.isikef.club.controller;

import com.isikef.club.entities.Club;
import com.isikef.club.forms.ClubForm;
import com.isikef.club.repository.ClubRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ClubControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        clubRepository.deleteAll();
    }

    @Test
    void createClub_shouldReturnSuccess_whenPayloadIsValid() throws Exception {
        ClubForm input = new ClubForm();
        input.setName("Alpha Club");
        input.setDescription("A valid description");
        String clubJson = objectMapper.writeValueAsString(input);

        mockMvc.perform(post("/clubs/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clubJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.content.name").value(input.getName()))
                .andExpect(jsonPath("$.content.description").value(input.getDescription()));
    }

    @Test
    void createClub_shouldReturnFailed_whenClubNameAlreadyExists() throws Exception {
        Club existingClub = new Club();
        existingClub.setName("Alpha Club");
        existingClub.setDescription("Existing description");
        clubRepository.save(existingClub);

        mockMvc.perform(post("/clubs/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Alpha Club\",\"description\":\"Another description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("FAILED"))
                .andExpect(jsonPath("$.message").value("Check provided data."));
    }

    @Test
    void createClub_shouldReturnBadRequest_whenDescriptionIsMissing() throws Exception {
        mockMvc.perform(post("/clubs/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Alpha Club\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.description").value("description can not be null"));
    }

    @Test
    void getClubs_shouldReturnSuccessWithData_whenClubsExist() throws Exception {
        Club club = new Club();
        club.setName("Alpha Club");
        club.setDescription("Existing description");
        clubRepository.save(club);

        mockMvc.perform(get("/clubs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.content[0].name").value("Alpha Club"));
    }

    @Test
    void getClubs_shouldReturnSuccessWithEmptyArray_whenNoClubsExist() throws Exception {
        mockMvc.perform(get("/clubs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.content.length()").value(0));
    }

    @Test
    void getClub_shouldReturnSuccess_whenClubExists() throws Exception {
        Club club = new Club();
        club.setName("Alpha Club");
        club.setDescription("Existing description");
        Club savedClub = clubRepository.save(club);

        mockMvc.perform(get("/clubs/get").param("id", savedClub.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.content.id").value(savedClub.getId()))
                .andExpect(jsonPath("$.content.name").value("Alpha Club"));
    }

    @Test
    void getClub_shouldReturnNotFound_whenClubDoesNotExist() throws Exception {
        mockMvc.perform(get("/clubs/get").param("id", "99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("FAILED"));
    }

    @Test
    void deleteClub_shouldReturnSuccess_whenClubExists() throws Exception {
        Club club = new Club();
        club.setName("Alpha Club");
        club.setDescription("Existing description");
        Club savedClub = clubRepository.save(club);

        mockMvc.perform(delete("/clubs").param("id", savedClub.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.content.deleted").value(true));
    }

    @Test
    void deleteClub_shouldReturnNotFound_whenClubDoesNotExist() throws Exception {
        mockMvc.perform(delete("/clubs").param("id", "99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("FAILED"));
    }
}
