package org.invest.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customSwaggerDocumentation(@Value("classpath:swagger.yml") Resource openApiResource) {
        try {
            String openApiAsString = new String(openApiResource.getInputStream().readAllBytes());
            return new OpenAPIV3Parser().readContents(openApiAsString,null,null).getOpenAPI();
        }catch (IOException exception){
            throw new RuntimeException("Не удалось загрузить файл документации");
        }
    }
}
