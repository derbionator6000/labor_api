package de.dhbw.shipwatch.dto;

import java.time.LocalDateTime;

public record SightingRequest(
    String observerName,
    LocalDateTime observedAt,
    String location,
    String notes
) {}
