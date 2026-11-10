package de.dhbw.shipwatch.mapper;

import de.dhbw.shipwatch.dto.SightingRequest;
import de.dhbw.shipwatch.dto.SightingResponse;
import de.dhbw.shipwatch.model.Sighting;

public final class SightingMapper {

    private SightingMapper() {}

    public static Sighting toEntity(SightingRequest request) {
        return new Sighting(
            request.observerName(),
            request.observedAt(),
            request.location(),
            request.notes()
        );
    }

    public static SightingResponse toResponse(Sighting entity) {
        return new SightingResponse(
            entity.getId(),
            entity.getTrackedShip().getId(),
            entity.getObserverName(),
            entity.getObservedAt(),
            entity.getLocation(),
            entity.getNotes()
        );
    }
}
