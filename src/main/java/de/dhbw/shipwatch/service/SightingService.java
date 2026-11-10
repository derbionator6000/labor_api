package de.dhbw.shipwatch.service;

import de.dhbw.shipwatch.model.Sighting;
import de.dhbw.shipwatch.repository.SightingRepository;
import de.dhbw.shipwatch.repository.TrackedShipRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SightingService {

    private final TrackedShipRepository trackedShipRepository;
    private final SightingRepository sightingRepository;

    public SightingService(TrackedShipRepository trackedShipRepository,
                           SightingRepository sightingRepository) {
        this.trackedShipRepository = trackedShipRepository;
        this.sightingRepository = sightingRepository;
    }

    // leer, wenn es das Schiff nicht gibt
    public Optional<List<Sighting>> findByShip(Long shipId) {
        if (!trackedShipRepository.existsById(shipId)) {
            return Optional.empty();
        }
        return Optional.of(sightingRepository.findByTrackedShipIdOrderByObservedAtDesc(shipId));
    }

    public Optional<Sighting> create(Long shipId, Sighting sighting) {
        return trackedShipRepository.findById(shipId).map(ship -> {
            ship.addSighting(sighting);
            return sightingRepository.save(sighting);
        });
    }
}
