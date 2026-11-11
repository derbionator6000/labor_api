package de.dhbw.shipwatch.service;

import de.dhbw.shipwatch.exception.SightingNotFoundException;
import de.dhbw.shipwatch.exception.TrackedShipNotFoundException;
import de.dhbw.shipwatch.model.Sighting;
import de.dhbw.shipwatch.model.TrackedShip;
import de.dhbw.shipwatch.repository.SightingRepository;
import de.dhbw.shipwatch.repository.TrackedShipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SightingService {

    private final TrackedShipRepository trackedShipRepository;
    private final SightingRepository sightingRepository;

    public SightingService(TrackedShipRepository trackedShipRepository,
                           SightingRepository sightingRepository) {
        this.trackedShipRepository = trackedShipRepository;
        this.sightingRepository = sightingRepository;
    }

    public List<Sighting> findByShip(Long shipId) {
        if (!trackedShipRepository.existsById(shipId)) {
            throw new TrackedShipNotFoundException(shipId);
        }
        return sightingRepository.findByTrackedShipIdOrderByObservedAtDesc(shipId);
    }

    public Sighting getOrThrow(Long shipId, Long sightingId) {
        if (!trackedShipRepository.existsById(shipId)) {
            throw new TrackedShipNotFoundException(shipId);
        }
        return sightingRepository.findByIdAndTrackedShipId(sightingId, shipId)
            .orElseThrow(() -> new SightingNotFoundException(sightingId));
    }

    public Sighting create(Long shipId, Sighting sighting) {
        TrackedShip ship = trackedShipRepository.findById(shipId)
            .orElseThrow(() -> new TrackedShipNotFoundException(shipId));
        ship.addSighting(sighting);
        return sightingRepository.save(sighting);
    }
}
