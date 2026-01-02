package com.j16.ai.computer.vision;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Azure AI Computer Vision Playground",
				version = "1.0",
				description = "API documentation for testing various Azure AI Java SDK features"
		)
)
@SpringBootApplication
public class AiComputerVisionApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiComputerVisionApplication.class, args);
	}

}
