package com.j16.ai.computer.vision.service;

import com.azure.ai.textanalytics.TextAnalyticsClient;
import com.azure.ai.textanalytics.TextAnalyticsClientBuilder;
import com.azure.ai.textanalytics.models.DetectedLanguage;
import com.azure.core.credential.AzureKeyCredential;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TextAnalyticsServiceImpl implements TextAnalyticsService {

    private final TextAnalyticsClient textAnalyticsClient;

    @Autowired
    public TextAnalyticsServiceImpl(
            @Value("${text.analytics.endpoint.uri}") String endpointUri,
            @Value("${text.analytics.endpoint.key}") String endpointKey

    ) {
        this.textAnalyticsClient = new TextAnalyticsClientBuilder()
                .credential(new AzureKeyCredential(endpointKey))
                .endpoint(endpointUri)
                .buildClient();
    }

    @Override
    public DetectedLanguage getDetectedLanguage(String document) {
        return textAnalyticsClient.detectLanguage(document);
    }
}
