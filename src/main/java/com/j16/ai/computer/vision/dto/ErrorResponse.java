package com.j16.ai.computer.vision.dto;

import lombok.Builder;

@Builder
public record ErrorResponse(
        int status,
        String error,
        String details
) {

}