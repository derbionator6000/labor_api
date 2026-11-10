package de.dhbw.shipwatch.dto;

// Was der Server zurückgibt (mit id)
public record TrackedShipResponse(
    Long id,
    long mmsi,
    String name,
    String shipType,
    double lengthM,
    boolean dangerousCargo,
    String destination
) {}
