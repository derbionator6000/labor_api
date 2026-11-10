package de.dhbw.shipwatch.dto;

// Was der Client schickt (ohne id)
public record TrackedShipRequest(
    long mmsi,
    String name,
    String shipType,
    double lengthM,
    boolean dangerousCargo,
    String destination
) {}
