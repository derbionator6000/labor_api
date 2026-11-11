package de.dhbw.shipwatch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

// Was der Client schickt (ohne id)
public record TrackedShipRequest(

    @Positive(message = "mmsi must be positive")
    long mmsi,

    @NotBlank(message = "name must not be blank")
    @Size(max = 100, message = "name must be at most 100 characters")
    String name,

    @Size(max = 50, message = "shipType must be at most 50 characters")
    String shipType,

    // 0 = Länge unbekannt
    @PositiveOrZero(message = "lengthM must be zero or positive")
    double lengthM,

    boolean dangerousCargo,

    @Size(max = 100, message = "destination must be at most 100 characters")
    String destination
) {}
