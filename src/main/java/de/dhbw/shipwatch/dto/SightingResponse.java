package de.dhbw.shipwatch.dto;

import java.time.LocalDateTime;

public record SightingResponse(
    Long id,
    Long trackedShipId,
    String observerName,
    LocalDateTime observedAt,
    String location,
    String notes
) {}
