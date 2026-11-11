package de.dhbw.shipwatch;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@OpenAPIDefinition(info = @Info(
    title = "ShipWatch API",
    version = "0.1.0",
    description = "Beobachtungsliste für Schiffe mit Live-AIS-Daten"
))
@EnableRetry
@SpringBootApplication
public class ShipwatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShipwatchApplication.class, args);
    }
}
