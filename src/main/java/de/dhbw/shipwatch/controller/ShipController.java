package de.dhbw.shipwatch.controller;

import de.dhbw.shipwatch.model.Ship;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/api/ships")
public class ShipController {

    // Offene AIS-Daten der finnischen Verkehrsbehörde (Ostsee), kein API-Key nötig
    private final RestClient digitraffic;

    public ShipController(RestClient.Builder builder) {
        this.digitraffic = builder
            .baseUrl("https://meri.digitraffic.fi/api/ais/v1")
            .defaultHeader("Digitraffic-User", "shipwatch-student-project")
            .build();
    }

    @GetMapping
    public List<Ship> getAll() {
        return digitraffic.get()
            .uri("/vessels")
            .retrieve()
            .body(new ParameterizedTypeReference<List<Ship>>() {});
    }

    @GetMapping("/count")
    public int count() {
        return getAll().size();
    }

    @GetMapping("/{mmsi}")
    public Ship getByMmsi(@PathVariable long mmsi) {
        return digitraffic.get()
            .uri("/vessels/{mmsi}", mmsi)
            .retrieve()
            .body(Ship.class);
    }
}
