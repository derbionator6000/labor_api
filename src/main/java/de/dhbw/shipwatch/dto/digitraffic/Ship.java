package de.dhbw.shipwatch.dto.digitraffic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// Stammdaten eines Schiffs aus der Digitraffic-AIS-API (externe Struktur, keine Entity)
@JsonIgnoreProperties(ignoreUnknown = true)
public record Ship(
    long mmsi,          // eindeutige Funkkennung
    String name,
    String callSign,
    long imo,
    int shipType,       // AIS-Typcode, z. B. 60-69 Passagier, 80-89 Tanker
    String destination,
    int referencePointA, // Abstand GPS-Antenne -> Bug in m
    int referencePointB  // Abstand GPS-Antenne -> Heck in m
) {
    public Ship {
        // die API füllt Texte mit Leerzeichen auf
        name = name == null ? null : name.trim();
        destination = destination == null ? null : destination.trim();
    }

    // Schiffslänge = Bug-Abstand + Heck-Abstand (0, wenn die API nichts meldet)
    public double lengthM() {
        return referencePointA + referencePointB;
    }
}
