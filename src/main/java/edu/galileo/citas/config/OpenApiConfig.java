package edu.galileo.citas.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API Citas Taller",
        version = "1.0",
        description = "API REST para gestión de usuarios, vehículos y citas.",
        contact = @Contact(name = "Curso Galileo"),
        license = @License(name = "Apache 2.0")
    )
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer",
    in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {}
