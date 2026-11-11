package de.dhbw.shipwatch.controller;

import de.dhbw.shipwatch.dto.TrackedShipRequest;
import de.dhbw.shipwatch.dto.TrackedShipResponse;
import de.dhbw.shipwatch.mapper.TrackedShipMapper;
import de.dhbw.shipwatch.model.TrackedShip;
import de.dhbw.shipwatch.service.TrackedShipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

    // GET /api/tracked-ships/search?name=viking
    @GetMapping("/search")
    public List<TrackedShipResponse> search(@RequestParam String name) {
        return service.searchByName(name).stream()
            .map(TrackedShipMapper::toResponse)
            .toList();
    }

    // Schiffe länger als 200 m (Custom Query)
    @GetMapping("/large")
    public List<TrackedShipResponse> getLarge() {
        return service.findLarge().stream()
            .map(TrackedShipMapper::toResponse)
            .toList();
    }

    @GetMapping("/{id}")
    public TrackedShipResponse getById(@PathVariable Long id) {
        return TrackedShipMapper.toResponse(service.getOrThrow(id));
    }

    @Operation(summary = "Create a new tracked ship")
    @ApiResponse(responseCode = "201", description = "Created")
    @ApiResponse(responseCode = "400", description = "Validation failed")
    @PostMapping
    public ResponseEntity<TrackedShipResponse> create(@Valid @RequestBody TrackedShipRequest request) {
        TrackedShip created = service.create(TrackedShipMapper.toEntity(request));
        return ResponseEntity
            .created(URI.create("/api/tracked-ships/" + created.getId()))
            .body(TrackedShipMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public TrackedShipResponse update(@PathVariable Long id,
                                      @Valid @RequestBody TrackedShipRequest request) {
        return TrackedShipMapper.toResponse(service.update(id, TrackedShipMapper.toEntity(request)));
    }

    // schaltet nur das Feld dangerousCargo um
    @PatchMapping("/{id}/dangerous-cargo")
    public TrackedShipResponse toggleDangerousCargo(@PathVariable Long id) {
        return TrackedShipMapper.toResponse(service.toggleDangerousCargo(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
