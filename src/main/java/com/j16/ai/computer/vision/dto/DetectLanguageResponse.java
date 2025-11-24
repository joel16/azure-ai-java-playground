package com.j16.ai.computer.vision.dto;

import lombok.Builder;

@Builder
public record DetectLanguageResponse(
        String name,
        String iso6391Name,
        String confidence
) {

}
