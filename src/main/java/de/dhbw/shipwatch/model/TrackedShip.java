package de.dhbw.shipwatch.model;

// Ein Schiff auf unserer Beobachtungsliste
public class TrackedShip {

    private Long id;
    private long mmsi;
    private String name;
    private String shipType;
    private double lengthM;
    private boolean dangerousCargo;
    private String destination;

    public TrackedShip(Long id, long mmsi, String name, String shipType,
                       double lengthM, boolean dangerousCargo, String destination) {
        this.id = id;
        this.mmsi = mmsi;
        this.name = name;
        this.shipType = shipType;
        this.lengthM = lengthM;
        this.dangerousCargo = dangerousCargo;
        this.destination = destination;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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
}
