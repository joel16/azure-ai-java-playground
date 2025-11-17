package com.j16.ai.computer.vision.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagResponse {
    private String name;
    private String confidence;
}
