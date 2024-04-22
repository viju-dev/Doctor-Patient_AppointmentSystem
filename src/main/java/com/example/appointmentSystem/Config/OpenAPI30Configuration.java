package com.example.appointmentSystem.Config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;

@Configuration
public class OpenAPI30Configuration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info().title("My API").version("1.0").description("My API Description"))
                .servers(Arrays.asList(
                        new io.swagger.v3.oas.models.servers.Server().url("http://localhost:8080/").description("Local server"),
                        new io.swagger.v3.oas.models.servers.Server().url("https://doctor-patientappointmentsystem-production.up.railway.app/").description("Production server")
                ));
    }
}
