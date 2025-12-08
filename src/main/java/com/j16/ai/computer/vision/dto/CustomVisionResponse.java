package com.j16.ai.computer.vision.dto;

import java.util.Comparator;
import java.util.List;

public record CustomVisionResponse(
        String id,
        String project,
        String iteration,
        String created,
        List<Prediction> predictions
) {

    public record Prediction(
            double probability,
            String tagId,
            String tagName
    ) {

    }

    // Get the highest prediction
    public Prediction dominantPrediction() {
        return predictions.stream()
                .max(Comparator.comparingDouble(Prediction::probability))
                .orElse(null);
    }

}
