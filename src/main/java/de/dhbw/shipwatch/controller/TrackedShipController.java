package de.dhbw.shipwatch.controller;

import de.dhbw.shipwatch.dto.TrackedShipRequest;
import de.dhbw.shipwatch.dto.TrackedShipResponse;
import de.dhbw.shipwatch.mapper.TrackedShipMapper;
import de.dhbw.shipwatch.model.TrackedShip;
import de.dhbw.shipwatch.service.TrackedShipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tracked-ships")
public class TrackedShipController {

    private final TrackedShipService service;

    public TrackedShipController(TrackedShipService service) {
        this.service = service;
    }

    // GET /api/tracked-ships oder /api/tracked-ships?dangerousCargo=true
    @GetMapping
    public List<TrackedShipResponse> getAll(@RequestParam(required = false) Boolean dangerousCargo) {
        List<TrackedShip> ships = dangerousCargo == null
            ? service.findAll()
            : service.findByDangerousCargo(dangerousCargo);
        return ships.stream()
            .map(TrackedShipMapper::toResponse)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrackedShipResponse> getById(@PathVariable Long id) {
        return service.findById(id)
            .map(TrackedShipMapper::toResponse)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TrackedShipResponse> create(@RequestBody TrackedShipRequest request) {
        TrackedShip created = service.create(TrackedShipMapper.toEntity(null, request));
        return ResponseEntity
            .created(URI.create("/api/tracked-ships/" + created.getId()))
            .body(TrackedShipMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrackedShipResponse> update(@PathVariable Long id,
                                                      @RequestBody TrackedShipRequest request) {
        return service.update(id, TrackedShipMapper.toEntity(id, request))
            .map(TrackedShipMapper::toResponse)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // schaltet nur das Feld dangerousCargo um
    @PatchMapping("/{id}/dangerous-cargo")
    public ResponseEntity<TrackedShipResponse> toggleDangerousCargo(@PathVariable Long id) {
        return service.toggleDangerousCargo(id)
            .map(TrackedShipMapper::toResponse)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
