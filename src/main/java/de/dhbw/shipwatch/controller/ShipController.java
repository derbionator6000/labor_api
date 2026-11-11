package de.dhbw.shipwatch.controller;

import de.dhbw.shipwatch.dto.TrackedShipResponse;
import de.dhbw.shipwatch.dto.digitraffic.Ship;
import de.dhbw.shipwatch.mapper.ShipMapper;
import de.dhbw.shipwatch.mapper.TrackedShipMapper;
import de.dhbw.shipwatch.model.TrackedShip;
import de.dhbw.shipwatch.service.DigitrafficService;
import de.dhbw.shipwatch.service.TrackedShipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

// Live-Daten aus der Digitraffic-AIS-API (Ostsee)
@RestController
@RequestMapping("/api/ships")
public class ShipController {

    private final DigitrafficService digitrafficService;
    private final TrackedShipService trackedShipService;

    public ShipController(DigitrafficService digitrafficService, TrackedShipService trackedShipService) {
        this.digitrafficService = digitrafficService;
        this.trackedShipService = trackedShipService;
    }

    // GET /api/ships oder /api/ships?name=viking
    @GetMapping
    public List<Ship> getAll(@RequestParam(required = false) String name) {
        List<Ship> ships = digitrafficService.fetchAll();
        if (name == null || name.isBlank()) {
            return ships;
        }
        String needle = name.toLowerCase();
        return ships.stream()
            .filter(s -> s.name() != null && s.name().toLowerCase().contains(needle))
            .toList();
    }

    @GetMapping("/count")
    public int count() {
        return digitrafficService.fetchAll().size();
    }

    @GetMapping("/{mmsi}")
    public Ship getByMmsi(@PathVariable long mmsi) {
        return digitrafficService.fetchByMmsi(mmsi);
    }

    // Live-Schiff in die eigene Beobachtungsliste (H2) übernehmen
    @PostMapping("/{mmsi}/track")
    public ResponseEntity<TrackedShipResponse> track(@PathVariable long mmsi) {
        TrackedShip created = trackedShipService.create(ShipMapper.toEntity(digitrafficService.fetchByMmsi(mmsi)));
        return ResponseEntity
            .created(URI.create("/api/tracked-ships/" + created.getId()))
            .body(TrackedShipMapper.toResponse(created));
    }
}
