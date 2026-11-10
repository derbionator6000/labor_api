package de.dhbw.shipwatch.controller;

import de.dhbw.shipwatch.dto.SightingRequest;
import de.dhbw.shipwatch.dto.SightingResponse;
import de.dhbw.shipwatch.mapper.SightingMapper;
import de.dhbw.shipwatch.service.SightingService;
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
    public ResponseEntity<List<SightingResponse>> getAll(@PathVariable Long shipId) {
        return service.findByShip(shipId)
            .map(list -> list.stream().map(SightingMapper::toResponse).toList())
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SightingResponse> create(@PathVariable Long shipId,
                                                   @RequestBody SightingRequest request) {
        return service.create(shipId, SightingMapper.toEntity(request))
            .map(saved -> ResponseEntity
                .created(URI.create("/api/tracked-ships/" + shipId + "/sightings/" + saved.getId()))
                .body(SightingMapper.toResponse(saved)))
            .orElse(ResponseEntity.notFound().build());
    }
}
