package de.dhbw.shipwatch.mapper;

import de.dhbw.shipwatch.dto.digitraffic.Ship;
import de.dhbw.shipwatch.model.TrackedShip;

// externe Digitraffic-Daten -> eigene Entity
public final class ShipMapper {

    private ShipMapper() {}

    public static TrackedShip toEntity(Ship source) {
        return new TrackedShip(
            source.mmsi(),
            source.name(),
            "AIS " + source.shipType(),
            0.0,     // Länge liefert die API nicht -> unbekannt
            false,
            source.destination()
        );
    }
}
