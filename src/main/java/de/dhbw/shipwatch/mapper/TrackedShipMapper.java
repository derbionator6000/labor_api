package de.dhbw.shipwatch.mapper;

import de.dhbw.shipwatch.dto.TrackedShipRequest;
import de.dhbw.shipwatch.dto.TrackedShipResponse;
import de.dhbw.shipwatch.model.TrackedShip;

public final class TrackedShipMapper {

    private TrackedShipMapper() {}

    public static TrackedShip toEntity(Long id, TrackedShipRequest request) {
        return new TrackedShip(
            id,
            request.mmsi(),
            request.name(),
            request.shipType(),
            request.lengthM(),
            request.dangerousCargo(),
            request.destination()
        );
    }

    public static TrackedShipResponse toResponse(TrackedShip entity) {
        return new TrackedShipResponse(
            entity.getId(),
            entity.getMmsi(),
            entity.getName(),
            entity.getShipType(),
            entity.getLengthM(),
            entity.isDangerousCargo(),
            entity.getDestination()
        );
    }
}
