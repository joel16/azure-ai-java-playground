package com.j16.ai.computer.vision.controller;

import com.azure.ai.vision.imageanalysis.models.CaptionResult;
import com.azure.ai.vision.imageanalysis.models.DetectedTag;
import com.azure.ai.vision.imageanalysis.models.DetectedTextLine;
import com.azure.ai.vision.imageanalysis.models.ImageAnalysisResult;
import com.j16.ai.computer.vision.dto.*;
import com.j16.ai.computer.vision.service.AnalyzeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.azure.ai.vision.imageanalysis.models.VisualFeatures.*;

@RestController
@RequestMapping("/vision")
public class AnalyzeController {

    private final AnalyzeService analyzeService;

    @Autowired
    public AnalyzeController(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    @GetMapping("/get/caption")
    public ResponseEntity<CaptionResponse> getCaption(@RequestParam String url) {
        CaptionResult caption = analyzeService.analyze(url, CAPTION).getCaption();

        CaptionResponse response = CaptionResponse.builder()
                .altText(caption.getText())
                .confidence(String.format("%.2f%%", caption.getConfidence() * 100))
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/tags")
    public ResponseEntity<List<TagResponse>> getTags(@RequestParam String url) {
        ImageAnalysisResult result = analyzeService.analyze(url, TAGS);
        List<DetectedTag> detectedTags = result.getTags().getValues();

        List<TagResponse> tagResponses = detectedTags.stream()
                .map(tag -> new TagResponse(
                        tag.getName(),
                        String.format("%.2f%%", tag.getConfidence() * 100)
                ))
                .toList();

        return ResponseEntity.ok(tagResponses);
    }

    @GetMapping("/get/read")
    public ResponseEntity<ReadTextResponse> getTextFromImage(@RequestParam String url) {
        ImageAnalysisResult result = analyzeService.analyze(url, READ);

        if (result.getRead() == null || result.getRead().getBlocks().isEmpty()) {
            return ResponseEntity.ok(new ReadTextResponse(List.of()));
        }

        // Flatten all lines from all blocks
        List<String> lines = result.getRead().getBlocks().stream()
                .flatMap(block -> block.getLines().stream())
                .map(DetectedTextLine::getText)
                .toList();

        return ResponseEntity.ok(new ReadTextResponse(lines));
    }

}
