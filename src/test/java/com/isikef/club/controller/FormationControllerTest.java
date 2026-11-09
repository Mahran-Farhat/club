package com.isikef.club.controller;

import com.isikef.club.entities.Club;
import com.isikef.club.entities.Formation;
import com.isikef.club.entities.ThemeFormation;
import com.isikef.club.repository.ClubRepository;
import com.isikef.club.repository.FormationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import jakarta.servlet.ServletException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class FormationControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private FormationRepository formationRepository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        formationRepository.deleteAll();
        clubRepository.deleteAll();
    }

    @Test
    void createFormation_shouldReturnSuccess_whenPayloadIsValid() throws Exception {
        Club club = saveClub("Alpha Club");

        mockMvc.perform(post("/formations/create")
                        .param("clubId", club.getId().toString())
                        .param("theme", ThemeFormation.JAVA.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.content.id").exists())
                .andExpect(jsonPath("$.content.theme").value("JAVA"));
    }

    @Test
    void createFormation_shouldReturnBadRequest_whenThemeIsMissing() throws Exception {
        Club club = saveClub("Alpha Club");

        mockMvc.perform(post("/formations/create")
                        .param("clubId", club.getId().toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.theme").value("Le thème de la formation est obligatoire"));
    }

    @Test
    void createFormation_shouldThrowException_whenClubDoesNotExist() {
        ServletException exception = assertThrows(ServletException.class, () ->
                mockMvc.perform(post("/formations/create")
                        .param("clubId", "99999")
                        .param("theme", ThemeFormation.JAVA.name())));

        assertTrue(exception.getMessage().contains("Club not found with id: 99999"));
    }

    @Test
    void getFormationById_shouldReturnSuccess_whenFormationExists() throws Exception {
        Club club = saveClub("Alpha Club");
        Formation formation = saveFormation(club, ThemeFormation.JAVA);

        mockMvc.perform(get("/formations/{id}", formation.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.content.id").value(formation.getId()))
                .andExpect(jsonPath("$.content.theme").value("JAVA"));
    }

    @Test
    void getFormationById_shouldThrowException_whenFormationDoesNotExist() {
        ServletException exception = assertThrows(ServletException.class, () ->
                mockMvc.perform(get("/formations/{id}", "99999")));

        assertTrue(exception.getMessage().contains("Formation not found with id: 99999"));
    }

    @Test
    void getAllFormations_shouldReturnSuccessWithData_whenFormationsExist() throws Exception {
        Club club = saveClub("Alpha Club");
        saveFormation(club, ThemeFormation.JAVA);

        mockMvc.perform(get("/formations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.content.length()").value(1));
    }

    @Test
    void getAllFormations_shouldReturnSuccessWithEmptyArray_whenNoFormationsExist() throws Exception {
        mockMvc.perform(get("/formations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.content.length()").value(0));
    }

    @Test
    void deleteFormation_shouldReturnSuccess_whenFormationExists() throws Exception {
        Club club = saveClub("Alpha Club");
        Formation formation = saveFormation(club, ThemeFormation.JAVA);

        mockMvc.perform(delete("/formations/{id}", formation.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.content.deleted").value(true));
    }

    @Test
    void deleteFormation_shouldThrowException_whenFormationDoesNotExist() {
        ServletException exception = assertThrows(ServletException.class, () ->
                mockMvc.perform(delete("/formations/{id}", "99999")));

        assertTrue(exception.getMessage().contains("Formation not found with id: 99999"));
    }

    @Test
    void findByTheme_shouldReturnSuccessWithData_whenThemeExists() throws Exception {
        Club club = saveClub("Alpha Club");
        saveFormation(club, ThemeFormation.JAVA);

        mockMvc.perform(get("/formations/theme/{theme}", ThemeFormation.JAVA.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].theme").value("JAVA"));
    }

    @Test
    void findByTheme_shouldReturnBadRequest_whenThemeIsInvalid() throws Exception {
        mockMvc.perform(get("/formations/theme/{theme}", "NOT_A_THEME"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void searchByTheme_shouldReturnSuccessWithData_whenMatchExists() throws Exception {
        Club club = saveClub("Alpha Club");
        saveFormation(club, ThemeFormation.JAVA);

        mockMvc.perform(get("/formations/search/{theme}", "JAV"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].theme").value("JAVA"));
    }

    @Test
    void searchByTheme_shouldReturnSuccessWithEmptyArray_whenNoMatchExists() throws Exception {
        Club club = saveClub("Alpha Club");
        saveFormation(club, ThemeFormation.JAVA);

        mockMvc.perform(get("/formations/search/{theme}", "RUST"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.content.length()").value(0));
    }

    @Test
    void getFormationsWithPagination_shouldReturnSuccess_whenPageParametersAreValid() throws Exception {
        Club club = saveClub("Alpha Club");
        saveFormation(club, ThemeFormation.JAVA);
        saveFormation(club, ThemeFormation.PYTHON);
        saveFormation(club, ThemeFormation.GO);

        mockMvc.perform(get("/formations/paginated")
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.content.pageNumber").value(0))
                .andExpect(jsonPath("$.content.pageSize").value(2))
                .andExpect(jsonPath("$.content.data.length()").value(2));
    }

    @Test
    void getFormationsWithPagination_shouldThrowException_whenSizeIsInvalid() {
        ServletException exception = assertThrows(ServletException.class, () ->
                mockMvc.perform(get("/formations/paginated")
                        .param("page", "0")
                        .param("size", "0")));

        assertTrue(exception.getMessage().contains("Page size must not be less than one"));
    }

    @Test
    void getPaginatedAndSortedFormations_shouldReturnSuccess_whenQueryIsValid() throws Exception {
        Club club = saveClub("Alpha Club");
        saveFormation(club, ThemeFormation.JAVA);
        saveFormation(club, ThemeFormation.PYTHON);

        mockMvc.perform(get("/formations/sortedAndPaginated")
                        .param("page", "0")
                        .param("size", "2")
                        .param("field", "theme")
                        .param("direction", "ASC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.content.pageNumber").value(0))
                .andExpect(jsonPath("$.content.pageSize").value(2))
                .andExpect(jsonPath("$.content.data.length()").value(2));
    }

    @Test
    void getPaginatedAndSortedFormations_shouldReturnBadRequest_whenDirectionIsInvalid() throws Exception {
        mockMvc.perform(get("/formations/sortedAndPaginated")
                        .param("page", "0")
                        .param("size", "2")
                        .param("field", "theme")
                        .param("direction", "INVALID"))
                .andExpect(status().isBadRequest());
    }

    private Club saveClub(String name) {
        Club club = new Club();
        club.setName(name);
        club.setDescription("Description for " + name);
        return clubRepository.save(club);
    }

    private Formation saveFormation(Club club, ThemeFormation theme) {
        Formation formation = new Formation();
        formation.setClub(club);
        formation.setTheme(theme);
        return formationRepository.save(formation);
    }
}
