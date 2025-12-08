package com.j16.ai.computer.vision.service;

import com.j16.ai.computer.vision.dto.CustomVisionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CustomVisionServiceImpl implements CustomVisionService {

    RestClient restClient;

    @Value("${custom.vision.endpoint.uri}")
    String endpointUri;

    @Value("${custom.vision.endpoint.key}")
    String endpointKey;

    @Autowired
    public CustomVisionServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public ResponseEntity<CustomVisionResponse> isMikuOrMochi(MultipartFile file) throws IOException {
        return restClient.post()
                .uri(endpointUri)
                .header("Prediction-Key", endpointKey)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .body(file.getBytes())
                .retrieve()
                .toEntity(CustomVisionResponse.class);
    }

}
