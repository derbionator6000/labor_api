package de.dhbw.shipwatch.model;

// Stammdaten eines Schiffs aus der Digitraffic AIS-API
public record Ship(
    long mmsi,          // eindeutige Funkkennung
    String name,
    String callSign,
    long imo,
    int shipType,
    String destination
) {
    public Ship {
        // die API füllt Texte mit Leerzeichen auf
        name = name == null ? null : name.trim();
        destination = destination == null ? null : destination.trim();
    }
}
