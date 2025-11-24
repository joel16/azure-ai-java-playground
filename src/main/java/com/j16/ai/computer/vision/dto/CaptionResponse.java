package com.j16.ai.computer.vision.dto;

import lombok.Builder;

@Builder
public record CaptionResponse(
        String altText,
        String confidence
) {

}
