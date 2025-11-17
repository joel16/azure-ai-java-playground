package com.j16.ai.computer.vision.service;

import com.azure.ai.vision.imageanalysis.models.ImageAnalysisResult;
import com.azure.ai.vision.imageanalysis.models.VisualFeatures;

public interface AnalyzeService {
    ImageAnalysisResult analyze(String url, VisualFeatures feature);
}
