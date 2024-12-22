package org.vt.conferences.config.springdoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPI30
public class ApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().openapi("3.0.1")
                .info(new Info().title("Conferences API")
                        .description("Conferences service")
                        .version("1.0.0"));
    }

}
