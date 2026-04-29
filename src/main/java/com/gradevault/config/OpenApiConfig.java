package com.gradevault.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI gradeVaultOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GradeVault API")
                        .description("Student Grade Management REST API")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("GradeVault Team")
                                .email("support@gradevault.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
