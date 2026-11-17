package com.tourist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourist.dto.TouristRequest;
import com.tourist.entity.Tourist;
import com.tourist.repository.TouristRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TouristControllerIntegrationTest {

    private static final String VALID_TOKEN = "test-token";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TouristRepository repository;

    private Tourist tourist;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        tourist = repository.save(new Tourist(
                "Original name", "Original destination", "original@example.com", "1111111111"));
    }

    @Test
    void updatesTouristWithValidToken() throws Exception {
        TouristRequest update = new TouristRequest(
                "Updated name", "Updated destination", "updated@example.com", "2222222222");

        mockMvc.perform(put("/api/tourists/{id}", tourist.getId())
                        .header("token_value", VALID_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Tourist updated successfully")))
                .andExpect(jsonPath("$.tourist.name", is("Updated name")))
                .andExpect(jsonPath("$.tourist.destination", is("Updated destination")))
                .andExpect(jsonPath("$.tourist.email", is("updated@example.com")))
                .andExpect(jsonPath("$.tourist.phone", is("2222222222")));
    }

    @Test
    void rejectsInvalidTokenWithInternalServerError() throws Exception {
        TouristRequest update = new TouristRequest(
                "Updated name", "Updated destination", "updated@example.com", "2222222222");

        mockMvc.perform(put("/api/tourists/{id}", tourist.getId())
                        .header("token_value", "invalid-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error", is("Invalid token")));
    }
}
