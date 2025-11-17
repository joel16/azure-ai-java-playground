package com.j16.ai.computer.vision.service;

import com.azure.ai.vision.imageanalysis.*;
import com.azure.ai.vision.imageanalysis.models.ImageAnalysisOptions;
import com.azure.ai.vision.imageanalysis.models.ImageAnalysisResult;
import com.azure.ai.vision.imageanalysis.models.VisualFeatures;
import com.azure.core.credential.KeyCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AnalyzeServiceImpl implements AnalyzeService {

    private final ImageAnalysisClient client;

    @Autowired
    public AnalyzeServiceImpl(
            @Value("${vision.endpoint.uri}") String endpointUri,
            @Value("${vision.endpoint.key}") String endpointKey
    ) {
        this.client = new ImageAnalysisClientBuilder()
                .endpoint(endpointUri)
                .credential(new KeyCredential(endpointKey))
                .buildClient();
    }

    public ImageAnalysisResult analyze(String url, VisualFeatures feature) {
        return this.client.analyzeFromUrl(
                url,
                Collections.singletonList(feature),
                new ImageAnalysisOptions().setGenderNeutralCaption(true)
        );
    }

}
