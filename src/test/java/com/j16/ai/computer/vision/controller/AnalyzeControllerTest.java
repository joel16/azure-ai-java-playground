package com.j16.ai.computer.vision.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
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
    private final String LANG_URL = "https://upload.wikimedia.org/wikipedia/commons/e/e8/Everystar_logowiki.png?20170213061931";

    @Test
    @DisplayName("Should produce a caption from image")
    void shouldGetCaption() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/vision/caption").param("url", ANALYZE_TEST_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.altText").isNotEmpty());
    }

    @Test
    @DisplayName("Should not produce a caption for image")
    void shouldNotGetCaption() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/vision/caption").param("url" + "1", ANALYZE_TEST_URL))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("400"));
    }

    @Test
    @DisplayName("Should get tags from an image")
    void shouldGetTags() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/vision/tags").param("url", ANALYZE_TEST_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    @DisplayName("Should not get tags from image")
    void shouldNotGetTags() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/vision/tags").param("url" + 1, ANALYZE_TEST_URL))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("400"));
    }

    @Test
    @DisplayName("Should extract and read text from an image")
    void shouldGetTextFromImage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/vision/read").param("url", OCR_TEST_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lines").value("TEST LOGO"));
    }

    @Test
    @DisplayName("Should not extract and read text from an image")
    void shouldNotGetTextFromImage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/vision/read").param("url" + 1, OCR_TEST_URL))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("400"));
    }

    @Test
    @DisplayName("Should detect the language of extracted text from an image")
    void shouldDetectLanguage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/vision/read-detect-language").param("url", LANG_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Japanese"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.iso6391Name").value("ja"));
    }

    @Test
    @DisplayName("Should not detect the language of extracted text from an image")
    void shouldNotDetectLanguage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/vision/read-detect-language").param("url" + 1, LANG_URL))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("400"));
    }

    @Test
    @DisplayName("Should detect Miku in the uploaded image with high probability")
    void shouldDetectMiku() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "images/miku.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                getClass().getClassLoader().getResourceAsStream("images/miku.jpg")
        );

        mockMvc.perform(
                    MockMvcRequestBuilders.multipart("/v1/vision/custom/miku-or-mochi")
                        .file(file)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.predictions").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.predictions[0].tagName").value("Miku"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.predictions[0].probability").value(Matchers.greaterThan(0.90)));
    }

    @Test
    @DisplayName("Should detect Mochi in the uploaded image with high probability")
    void shouldDetectMochi() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "images/mochi.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                getClass().getClassLoader().getResourceAsStream("images/mochi.jpg")
        );

        mockMvc.perform(
                    MockMvcRequestBuilders.multipart("/v1/vision/custom/miku-or-mochi")
                        .file(file)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.predictions").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.predictions[0].tagName").value("Mochi"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.predictions[0].probability").value(Matchers.greaterThan(0.90)));
    }

}
