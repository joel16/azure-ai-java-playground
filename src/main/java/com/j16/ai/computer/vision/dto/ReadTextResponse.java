package com.j16.ai.computer.vision.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReadTextResponse {
    private List<String> lines;
}
