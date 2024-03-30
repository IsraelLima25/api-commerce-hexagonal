package com.api.hexagonal.commerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@ConditionalOnProperty(value = "spring.swagger-ui.enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerConfig {
	
	@Value("${info.app.name:unknown}")
	private String name;
	
	@Value("${info.app.description:unknown}")
	private String description;
	
	@Value("${info.app.version:unknown}")
	private String version;
	
	
	@Bean
	public OpenAPI springOpenAPI() {
		return new OpenAPI()
				.info(new Info().title(this.name)
						.description(description)
						.version(version))
				.externalDocs(new ExternalDocumentation()
						.description("Api commerce")
						.url("URL descriptin construction"));
	}
}
