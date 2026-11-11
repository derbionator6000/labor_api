package de.dhbw.shipwatch.controller;

import de.dhbw.shipwatch.dto.SightingRequest;
import de.dhbw.shipwatch.dto.SightingResponse;
import de.dhbw.shipwatch.mapper.SightingMapper;
import de.dhbw.shipwatch.model.Sighting;
import de.dhbw.shipwatch.service.SightingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tracked-ships/{shipId}/sightings")
public class SightingController {

    private final SightingService service;

    public SightingController(SightingService service) {
        this.service = service;
    }

    @GetMapping
    public List<SightingResponse> getAll(@PathVariable Long shipId) {
        return service.findByShip(shipId).stream()
            .map(SightingMapper::toResponse)
            .toList();
    }

    @GetMapping("/{id}")
    public SightingResponse getById(@PathVariable Long shipId, @PathVariable Long id) {
        return SightingMapper.toResponse(service.getOrThrow(shipId, id));
    }

    @PostMapping
    public ResponseEntity<SightingResponse> create(@PathVariable Long shipId,
                                                   @Valid @RequestBody SightingRequest request) {
        Sighting saved = service.create(shipId, SightingMapper.toEntity(request));
        return ResponseEntity
            .created(URI.create("/api/tracked-ships/" + shipId + "/sightings/" + saved.getId()))
            .body(SightingMapper.toResponse(saved));
    }
}
