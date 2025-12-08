package com.j16.ai.computer.vision.service;

import com.j16.ai.computer.vision.dto.CustomVisionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CustomVisionService {
    ResponseEntity<CustomVisionResponse> isMikuOrMochi(MultipartFile file) throws IOException;
}
