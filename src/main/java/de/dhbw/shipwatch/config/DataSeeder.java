package de.dhbw.shipwatch.config;

import de.dhbw.shipwatch.model.TrackedShip;
import de.dhbw.shipwatch.repository.TrackedShipRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Beispieldaten beim Start, falls die Datenbank leer ist
@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedTrackedShips(TrackedShipRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }
            repository.save(new TrackedShip(230629000L, "Viking Grace", "Passenger", 218.0, false, "Stockholm"));
            repository.save(new TrackedShip(230983000L, "Sten Ingrid", "Tanker", 144.0, true, "Porvoo"));
            repository.save(new TrackedShip(230145000L, "Finnmaid", "Ro-Ro Cargo", 219.0, false, "Travemünde"));
        };
    }
}
