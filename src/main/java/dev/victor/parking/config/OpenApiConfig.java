package dev.victor.parking.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Parking API",
                version = "1.0.0",
                description = "API for managing customers, vehicles and contracts.",
                contact = @Contact(
                        name = "Victor Barbosa",
                        email = "dev.victorbr@gmail.com"
                )
        )
)
public class OpenApiConfig {
}
