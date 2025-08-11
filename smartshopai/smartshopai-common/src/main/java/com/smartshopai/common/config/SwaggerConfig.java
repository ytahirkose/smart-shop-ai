package com.smartshopai.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger/OpenAPI configuration for SmartShopAI services
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SmartShopAI API")
                        .description("Akıllı Alışveriş Asistanı - AI destekli e-ticaret platformu")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("SmartShopAI Team")
                                .email("info@smartshopai.com")
                                .url("https://smartshopai.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Development"),
                        new Server().url("https://api.smartshopai.com").description("Production")
                ));
    }
}
