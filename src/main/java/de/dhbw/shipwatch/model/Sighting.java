package de.dhbw.shipwatch.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

// Eine Sichtung eines beobachteten Schiffs
@Entity
public class Sighting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String observerName;
    private LocalDateTime observedAt;
    private String location;
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tracked_ship_id")
    private TrackedShip trackedShip;

    protected Sighting() {}

    public Sighting(String observerName, LocalDateTime observedAt,
                    String location, String notes) {
        this.observerName = observerName;
        this.observedAt = observedAt;
        this.location = location;
        this.notes = notes;
    }

    public Long getId() { return id; }

    public String getObserverName() { return observerName; }
    public void setObserverName(String v) { this.observerName = v; }

    public LocalDateTime getObservedAt() { return observedAt; }
    public void setObservedAt(LocalDateTime v) { this.observedAt = v; }

    public String getLocation() { return location; }
    public void setLocation(String v) { this.location = v; }

    public String getNotes() { return notes; }
    public void setNotes(String v) { this.notes = v; }

    public TrackedShip getTrackedShip() { return trackedShip; }
    public void setTrackedShip(TrackedShip v) { this.trackedShip = v; }
}
