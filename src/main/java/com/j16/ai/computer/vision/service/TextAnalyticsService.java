package com.j16.ai.computer.vision.service;

import com.azure.ai.textanalytics.models.DetectedLanguage;

public interface TextAnalyticsService {
    DetectedLanguage getDetectedLanguage(String document);
}
