package de.dhbw.shipwatch.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

// Ein Schiff auf unserer Beobachtungsliste
@Entity
public class TrackedShip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long mmsi;
    private String name;
    private String shipType;
    private double lengthM;
    private boolean dangerousCargo;
    private String destination;

    @OneToMany(mappedBy = "trackedShip",
               cascade = CascadeType.ALL,
               orphanRemoval = true,
               fetch = FetchType.LAZY)
    private List<Sighting> sightings = new ArrayList<>();

    protected TrackedShip() {
        // JPA braucht einen parameterlosen Konstruktor
    }

    public TrackedShip(long mmsi, String name, String shipType,
                       double lengthM, boolean dangerousCargo, String destination) {
        this.mmsi = mmsi;
        this.name = name;
        this.shipType = shipType;
        this.lengthM = lengthM;
        this.dangerousCargo = dangerousCargo;
        this.destination = destination;
    }

    public Long getId() { return id; }

    public long getMmsi() { return mmsi; }
    public void setMmsi(long mmsi) { this.mmsi = mmsi; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getShipType() { return shipType; }
    public void setShipType(String shipType) { this.shipType = shipType; }

    public double getLengthM() { return lengthM; }
    public void setLengthM(double lengthM) { this.lengthM = lengthM; }

    public boolean isDangerousCargo() { return dangerousCargo; }
    public void setDangerousCargo(boolean dangerousCargo) { this.dangerousCargo = dangerousCargo; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public List<Sighting> getSightings() { return sightings; }

    // hält beide Seiten der Beziehung synchron
    public void addSighting(Sighting sighting) {
        sightings.add(sighting);
        sighting.setTrackedShip(this);
    }

    public void removeSighting(Sighting sighting) {
        sightings.remove(sighting);
        sighting.setTrackedShip(null);
    }
}
