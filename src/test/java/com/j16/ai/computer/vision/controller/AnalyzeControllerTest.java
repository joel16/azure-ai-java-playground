package com.j16.ai.computer.vision.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class AnalyzeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String ANALYZE_TEST_URL = "https://upload.wikimedia.org/wikipedia/commons/0/01/Bot-Test.jpg";
    private final String OCR_TEST_URL = "https://upload.wikimedia.org/wikipedia/commons/8/85/Logo-Test.png";

    @Test
    void shouldGetCaption() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/vision/get/caption").param("url", ANALYZE_TEST_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.altText").isNotEmpty());
    }

    @Test
    void shouldNotGetCaption() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/vision/get/caption").param("url" + "1", ANALYZE_TEST_URL))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldGetTags() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/vision/get/tags").param("url", ANALYZE_TEST_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void shouldNotGetTags() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/vision/get/tags").param("url" + 1, ANALYZE_TEST_URL))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldGetTextFromImage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/vision/get/read").param("url", OCR_TEST_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lines").value("TEST LOGO"));
    }

    @Test
    void shouldNotGetTextFromImage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/vision/get/read").param("url" + 1, OCR_TEST_URL))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
